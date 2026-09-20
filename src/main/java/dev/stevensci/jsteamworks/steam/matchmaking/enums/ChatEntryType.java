package dev.stevensci.jsteamworks.steam.matchmaking.enums;

import dev.stevensci.jsteamworks.util.EnumLookup;

import java.util.Map;

public enum ChatEntryType {
    INVALID(0),
    CHAT_MESSAGE(1),
    TYPING(2),
    INVITE_GAME(3),
    EMOTE(4),
    LEFT_CONVERSATION(6),
    ENTERED(7),
    WAS_KICKED(8),
    WAS_BANNED(9),
    DISCONNECTED(10),
    HISTORICAL_CHAT(11),
    RESERVED_1(12),
    RESERVED_2(13),
    LINK_BLOCKED(14);

    private static final Map<Integer, ChatEntryType> LOOKUP =
            EnumLookup.mapByKey(ChatEntryType.class, ChatEntryType::getCode);

    private final int code;

    ChatEntryType(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public static ChatEntryType fromCode(int code) {
        return LOOKUP.getOrDefault(code, INVALID);
    }

}
