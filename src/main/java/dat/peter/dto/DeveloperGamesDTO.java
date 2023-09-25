package dat.peter.dto;

import dat.peter.model.Developer;
import dat.peter.model.Game;

import java.util.Set;

public record DeveloperGamesDTO(Developer developer, Set<Game> games) { }