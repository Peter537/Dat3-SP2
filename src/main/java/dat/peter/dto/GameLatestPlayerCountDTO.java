package dat.peter.dto;

public record GameLatestPlayerCountDTO(long appId, String name, long playerCount, long peakPlayerCount) { }