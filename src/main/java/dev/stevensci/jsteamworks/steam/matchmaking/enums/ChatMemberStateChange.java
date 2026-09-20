package dev.stevensci.jsteamworks.steam.matchmaking.enums;

import java.util.EnumSet;

public enum ChatMemberStateChange {
    ENTERED(0x0001),
    LEFT(0x0002),
    DISCONNECTED(0x0004),
    KICKED(0x0008),
    BANNED(0x0010);

    private final int bits;

    ChatMemberStateChange(int bits) {
        this.bits = bits;
    }

    public boolean isSet(int bitMask) {
        return (this.bits & bitMask) == this.bits;
    }

    public static EnumSet<ChatMemberStateChange> fromBits(int bitMask) {
        EnumSet<ChatMemberStateChange> set = EnumSet.noneOf(ChatMemberStateChange.class);
        for (ChatMemberStateChange value : values()) {
            if (value.isSet(bitMask)) set.add(value);
        }
        return set;
    }

}
