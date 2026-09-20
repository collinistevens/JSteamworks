package dev.stevensci.jsteamworks.steam.matchmaking.types;

public record FavoriteGame(int appId, int ip, short connPort, short queryPort, int flags, int lastPlayedOnServer) {
}
