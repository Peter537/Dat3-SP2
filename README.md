# Dat3-SP2

## Group

- Magnus
- Peter
- Sidsel
- Yusuf

## EER Diagram

![EER Diagram](documentation/EER.png)

## Links

Vi har brugt disse links:
- SteamDB: https://steamdb.info/
- SteamAPI:
  - Docs: https://partner.steamgames.com/doc/webapi
  - GetNewsForApp: https://api.steampowered.com/ISteamNews/GetNewsForApp/v2/

## Business model (which data did you decide to fetch - and for what?)
Data from SteamDB and the Steam API. Vores primær scraping er om players og player counts, vi vil gerne lave en DB hvor man nemt kan slå current player count, info om spillet samt recent nyheder.

## Project management (how did you organize the team and work?)

We work with Scrum and use Github Issues to create tasks. In a bigger project, we would have created a GitHub project instead.

## Webscraping (strategy and what you have implemented)
We tried using jsoup to scrape the website, but we ran into problems since the website we tried to scrape was under Cloudflare protection.
To circumvent this, we did a manual extraction of html files instead.

### API

## Considerations
We has a lot of discussion about whether the persistences should be directly in the webscraper, since the webscraper is the only one using the data. We decided to make a separate persistence layer, since it would be easier to test and maintain and to make our program more futureproof if we in the future want to hande more data from different sources.


### Edge cases

### Error handling

### Potential improvements

- Lige nu er vores WebScraper klasse ret "lukket" ift. at vi opretter Game objekterne inde fra klassen, man kunne evt. lave en DTO el.lign. som blev returneret fra WebScraper klassen, og så oprette Game objekterne et andet sted. Dvs. man vil kunne lave et relativt Generisk interface til WebScraper klassen som har en return-type DTO hvis man I fremtiden ville gøre det muligt at kunne scrape flere hjemmesider ud fra et URL.