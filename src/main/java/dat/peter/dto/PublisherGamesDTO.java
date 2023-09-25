package dat.peter.dto;

import dat.peter.model.Game;
import dat.peter.model.Publisher;

import java.util.Set;

public record PublisherGamesDTO(Publisher publisher, Set<Game> games) { }