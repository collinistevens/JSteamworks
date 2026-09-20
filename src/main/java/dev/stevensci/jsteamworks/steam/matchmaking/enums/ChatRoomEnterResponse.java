package dev.stevensci.jsteamworks.steam.matchmaking.enums;

import dev.stevensci.jsteamworks.util.EnumLookup;

import java.util.Map;

public enum ChatRoomEnterResponse {
    UNKNOWN(0),
    SUCCESS(1),
    DOESNT_EXIST(2),
    NOT_ALLOWED(3),
    FULL(4),
    ERROR(5),
    BANNED(6),
    LIMITED(7),
    CLAN_DISABLED(8),
    COMMUNITY_BANNED(9),
    MEMBER_BLOCKED_YOU(10),
    YOU_BLOCKED_MEMBER(11),
    RATELIMIT_EXCEEDED(15);

    private static final Map<Integer, ChatRoomEnterResponse> LOOKUP =
            EnumLookup.mapByKey(ChatRoomEnterResponse.class, ChatRoomEnterResponse::getCode);

    private final int code;

    ChatRoomEnterResponse(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public static ChatRoomEnterResponse fromCode(int code) {
        return LOOKUP.getOrDefault(code, UNKNOWN);
    }

}
