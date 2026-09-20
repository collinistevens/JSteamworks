package dev.stevensci.jsteamworks.steam.matchmaking.enums;

public enum LobbyComparison {
    EqualToOrLessThan(-2),
    LessThan(-1),
    Equal(0),
    GreaterThan(1),
    EqualToOrGreaterThan(2),
    NotEqual(3);

    private final int value;

    LobbyComparison(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

}
