# Dat3-SP2

## Group

- Magnus
- Peter
- Sidsel
- Yusuf

## EER Diagram

## Links

### WebScraping

### API

## Considerations

### Edge cases

### Error handling

### Potential improvements

- Lige nu er vores WebScraper klasse ret "lukket" ift. at vi opretter Game objekterne inde fra klassen, man kunne evt. lave en DTO el.lign. som blev returneret fra WebScraper klassen, og så oprette Game objekterne et andet sted. Dvs. man vil kunne lave et relativt Generisk interface til WebScraper klassen som har en return-type DTO hvis man I fremtiden ville gøre det muligt at kunne scrape flere hjemmesider ud fra et URL.