package dev.stevensci.jsteamworks.steam.matchmaking.callbacks;

import dev.stevensci.jsteamworks.callback.CallbackIdOffset;
import dev.stevensci.jsteamworks.callback.SteamCallback;
import dev.stevensci.jsteamworks.util.Struct;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.StructLayout;
import java.lang.invoke.VarHandle;

public record LobbyDataUpdate(long lobbySteamId, long memberSteamId, boolean success) {

    public static final int CALLBACK_ID = CallbackIdOffset.STEAM_MATCHMAKING + 5;
    public static final SteamCallback<LobbyDataUpdate> CALLBACK = SteamCallback.of(CALLBACK_ID, LobbyDataUpdate::decode);

    private static final StructLayout LAYOUT = Struct.builder()
            .longField("m_ulSteamIDLobby")
            .longField("m_ulSteamIDMember")
            .booleanField("m_bSuccess")
            .build("LobbyDataUpdate_t");

    private static final VarHandle VH_LOBBY_ID = Struct.varHandle(LAYOUT, "m_ulSteamIDLobby");
    private static final VarHandle VH_MEMBER_ID = Struct.varHandle(LAYOUT, "m_ulSteamIDMember");
    private static final VarHandle VH_SUCCESS = Struct.varHandle(LAYOUT, "m_bSuccess");

    private static LobbyDataUpdate decode(MemorySegment seg) {
        long lobbyId = (long) VH_LOBBY_ID.get(seg, 0L);
        long memberId = (long) VH_MEMBER_ID.get(seg, 0L);
        boolean success = (boolean) VH_SUCCESS.get(seg, 0L);
        return new LobbyDataUpdate(lobbyId, memberId, success);
    }

}
