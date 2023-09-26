package dat.config;

import dat.model.*;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManagerFactory;
import lombok.NoArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.File;
import java.io.IOException;
import java.lang.System;
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
        List<Class<?>> annotatedClasses = getAnnotatedClasses("dat.model");
        if (!annotatedClasses.isEmpty()) {
            annotatedClasses.forEach(configuration::addAnnotatedClass);
        }
        else {
            addAnnotatedClasses(
                    configuration,
                    App_Type.class,
                    Developer.class,
                    Game.class,
                    Game_Developer.class,
                    Game_Publishers.class,
                    Game_System.class,
                    News.class,
                    Publisher.class,
                    Scrape.class,
                    dat.model.System.class
            );
        }

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

    private static List<Class<?>> getAnnotatedClasses(String packageName) {
        try {
            return findClassesInPackage(packageName);
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    private static List<Class<?>> findClassesInPackage(String packageName) throws IOException, ClassNotFoundException {
        String packagePath = packageName.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        return getResources(packagePath, classLoader)
                .stream()
                .filter(File::isDirectory)
                .flatMap(directory -> {
                    try {
                        return findClassesInDirectory(directory, packageName).stream();
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

    private static List<File> getResources(String path, ClassLoader classLoader) throws IOException {
        Enumeration<URL> resources = classLoader.getResources(path);
        return Collections.list(resources)
                .stream()
                .map(url -> new File(url.getFile()))
                .collect(Collectors.toList());
    }

    private static List<Class<?>> findClassesInDirectory(File directory, String packageName) throws ClassNotFoundException {
        File[] files = directory.listFiles();
        if (files == null) {
            return new ArrayList<>();
        }

        List<Class<?>> annotatedClasses = new ArrayList<>();
        for (File file : files) {
            if (file.isDirectory()) {
                annotatedClasses.addAll(findClassesInDirectory(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + "." + file.getName().substring(0, file.getName().length() - 6);
                Class<?> clazz = Class.forName(className);
                if (clazz.isAnnotationPresent(Entity.class)) {
                    annotatedClasses.add(clazz);
                }
            }
        }

        return annotatedClasses;
    }
}