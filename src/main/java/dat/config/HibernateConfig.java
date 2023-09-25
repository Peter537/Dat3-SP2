package dat.config;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManagerFactory;
import lombok.NoArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class HibernateConfig {

    private static EntityManagerFactory entityManagerFactory;
    private static String databaseName = "postgres";

    private static EntityManagerFactory buildEntityFactoryConfig() {
        try {
            Configuration configuration = new Configuration();
            Properties props = new Properties();
            props.put("hibernate.connection.url", "jdbc:postgresql://localhost:5432/" + databaseName + "?currentSchema=public");
            props.put("hibernate.connection.username", "postgres");
            props.put("hibernate.connection.password", "postgres");
            props.put("hibernate.show_sql", "true"); // show sql in console
            props.put("hibernate.format_sql", "true"); // format sql in console
            props.put("hibernate.use_sql_comments", "true"); // show sql comments in console

            props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect"); // dialect for postgresql
            props.put("hibernate.connection.driver_class", "org.postgresql.Driver"); // driver class for postgresql
            props.put("hibernate.archive.autodetection", "class"); // hibernate scans for annotated classes
            props.put("hibernate.current_session_context_class", "thread"); // hibernate current session context
            props.put("hibernate.hbm2ddl.auto", "create"); // hibernate creates tables based on entities
            return getEntityManagerFactory(configuration, props);
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static EntityManagerFactory getEntityManagerFactory(Configuration configuration, Properties props) {
        configuration.setProperties(props);
        // TODO: addAnnotatedClasses(configuration, X.class, Y.class, Z.class);
        getAnnotatedClasses("dat.model").forEach(configuration::addAnnotatedClass);
//        addAnnotatedClasses(
//                configuration,
//                App_Type.class,
//                Developer.class,
//                Game.class,
//                Game_Developer.class,
//                Game_Publishers.class,
//                Game_System.class,
//                News.class,
//                Publisher.class,
//                Scrape.class,
//                dat.peter.model.System.class
//        );

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();

        System.out.println("Hibernate Java Config serviceRegistry created");
        SessionFactory sf = configuration.buildSessionFactory(serviceRegistry);
        return sf.unwrap(EntityManagerFactory.class);
    }

    private static void addAnnotatedClasses(Configuration configuration, Class<?>... annotatedClasses) {
        for (Class<?> annotatedClass : annotatedClasses) {
            configuration.addAnnotatedClass(annotatedClass);
        }
    }

    public static EntityManagerFactory getEntityManagerFactoryConfig(String databaseName) {
        if (entityManagerFactory == null || !HibernateConfig.databaseName.equals(databaseName)) {
            HibernateConfig.databaseName = databaseName;
            entityManagerFactory = buildEntityFactoryConfig();
        }

        return entityManagerFactory;
    }

    public static List<Class<?>> getAnnotatedClasses(String packageName) {
        String packagePath = packageName.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        try {
            return Collections.list(classLoader.getResources(packagePath))
                    .stream()
                    .map(URL::getFile)
                    .map(File::new)
                    .filter(File::isDirectory)
                    .flatMap(packageDir -> Arrays.stream(Objects.requireNonNull(packageDir.list())))
                    .filter(classFile -> classFile.endsWith(".class"))
                    .map(classFile -> packageName + '.' + classFile.substring(0, classFile.length() - 6))
                    .map(className -> {
                        try {
                            return Class.forName(className);
                        } catch (ClassNotFoundException e) {
                            return null;
                        }
                    })
                    .filter(clazz -> clazz != null && clazz.isAnnotationPresent(Entity.class))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }
}