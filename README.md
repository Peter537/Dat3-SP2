# Dat3-SP2

## Group

- Magnus
- Peter
- Sidsel
- Yusuf

## EER Diagram

![EER Diagram](documentation/EER.png)

## Links

We have utilizsed following links:
- SteamDB: https://steamdb.info/
- SteamAPI:
  - Docs: https://partner.steamgames.com/doc/webapi
  - GetNewsForApp: https://api.steampowered.com/ISteamNews/GetNewsForApp/v2/

## Business model (which data did you decide to fetch - and for what?)
Data from SteamDB and the SteamAPI. Our primary scraping regards players and playercounts. We want to make a database where its easy to find current-player-count's, info about games, and also news about said games.

## Project management (how did you organize the team and work?)

We work with Scrum and use Github Issues to create tasks. In a bigger project, we would have created a GitHub project instead.

## Webscraping (strategy and what you have implemented)
We tried using jsoup to scrape the website, but we ran into problems since the website we tried to scrape was under Cloudflare protection.
To circumvent this, we did a manual extraction of html files instead.

### API

## Considerations
We had a lot of discussion about whether the persistences should be directly in the webscraper, since the webscraper is the only one using the data. We decided to make a separate persistence layer, since it would be easier to test and maintain and to make our program more futureproof if we in the future want to hande more data from different sources.


### Edge cases

### Error handling

### Potential improvements

- Right now our WebScaper class is somewhat "closed" since we instantiate the Game objects from within the class. We could have potentially made a DTO or something alike that would return from the WebScaper class and then create the Game objects somewhere else. So in other words we would have been able to make a fairly generic interface for the WebScraper class which would return the type DTO if at any point we needed to scrape other websites.