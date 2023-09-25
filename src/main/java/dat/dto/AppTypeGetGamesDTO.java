package dat.dto;

import java.util.List;

public record AppTypeGetGamesDTO(String appType, List<String> names) { }