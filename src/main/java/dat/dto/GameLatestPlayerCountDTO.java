package dat.dto;

public record GameLatestPlayerCountDTO(long appId, String gameTitle, long playerCount, long peakPlayerCount) { }