package dat.peter.dto;

import dat.peter.model.Game;
import dat.peter.model.System;

import java.util.Set;

public record SystemGamesDTO(System system, Set<Game> games) { }