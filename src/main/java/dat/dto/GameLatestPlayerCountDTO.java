package dat.dto;

public record GameLatestPlayerCountDTO(long appId, String name, long playerCount, long peakPlayerCount) { }