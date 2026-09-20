package dev.stevensci.jsteamworks.steam.matchmaking.callbacks;

import dev.stevensci.jsteamworks.callback.CallbackIdOffset;
import dev.stevensci.jsteamworks.callback.SteamCallback;
import dev.stevensci.jsteamworks.util.Struct;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.StructLayout;
import java.lang.invoke.VarHandle;

public record LobbyInvite(long userSteamId, long lobbySteamId, long gameId) {

    public static final int CALLBACK_ID = CallbackIdOffset.STEAM_MATCHMAKING + 3;
    public static final SteamCallback<LobbyInvite> CALLBACK = SteamCallback.of(CALLBACK_ID, LobbyInvite::decode);

    private static final StructLayout LAYOUT = Struct.builder()
            .longField("m_ulSteamIDUser")
            .longField("m_ulSteamIDLobby")
            .longField("m_ulGameID")
            .build("LobbyInvite_t");

    private static final VarHandle VH_USER_ID = Struct.varHandle(LAYOUT, "m_ulSteamIDUser");
    private static final VarHandle VH_LOBBY_ID = Struct.varHandle(LAYOUT, "m_ulSteamIDLobby");
    private static final VarHandle VH_GAME_ID = Struct.varHandle(LAYOUT, "m_ulGameID");

    private static LobbyInvite decode(MemorySegment seg) {
        long userId = (long) VH_USER_ID.get(seg, 0);
        long lobbyId = (long) VH_LOBBY_ID.get(seg, 0);
        long gameId = (long) VH_GAME_ID.get(seg, 0);
        return new LobbyInvite(userId, lobbyId, gameId);
    }

}
