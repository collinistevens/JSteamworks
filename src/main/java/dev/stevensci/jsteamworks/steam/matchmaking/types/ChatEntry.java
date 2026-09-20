package dev.stevensci.jsteamworks.steam.matchmaking.types;

import dev.stevensci.jsteamworks.steam.matchmaking.enums.ChatEntryType;

import java.nio.charset.StandardCharsets;

public record ChatEntry(long userSteamId, ChatEntryType type, byte[] data) {

    public String asUtf8() {
        int len = Math.max(0, this.data.length - 1);
        return new String(this.data, 0, len, StandardCharsets.UTF_8);
    }

}
