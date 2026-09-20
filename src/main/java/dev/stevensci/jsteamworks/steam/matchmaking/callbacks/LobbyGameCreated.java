package dev.stevensci.jsteamworks.steam.matchmaking.callbacks;

import dev.stevensci.jsteamworks.callback.CallbackIdOffset;
import dev.stevensci.jsteamworks.callback.SteamCallback;
import dev.stevensci.jsteamworks.util.Struct;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.StructLayout;
import java.lang.invoke.VarHandle;

public record LobbyGameCreated(long lobbySteamId, long gameServerSteamId, int ip, short port) {

    public static final int CALLBACK_ID = CallbackIdOffset.STEAM_MATCHMAKING + 9;
    public static final SteamCallback<LobbyGameCreated> CALLBACK = SteamCallback.of(CALLBACK_ID, LobbyGameCreated::decode);

    private static final StructLayout LAYOUT = Struct.builder()
            .longField("m_ulSteamIDLobby")
            .longField("m_ulSteamIDGameServer")
            .intField("m_unIP")
            .shortField("m_usPort")
            .build("LobbyGameCreated_t");

    private static final VarHandle VH_LOBBY_ID = Struct.varHandle(LAYOUT, "m_ulSteamIDLobby");
    private static final VarHandle VH_GAME_SERVER_ID = Struct.varHandle(LAYOUT, "m_ulSteamIDGameServer");
    private static final VarHandle VH_IP = Struct.varHandle(LAYOUT, "m_unIP");
    private static final VarHandle VH_PORT = Struct.varHandle(LAYOUT, "m_usPort");

    private static LobbyGameCreated decode(MemorySegment seg) {
        long lobbyId = (long) VH_LOBBY_ID.get(seg, 0);
        long gameServerId = (long) VH_GAME_SERVER_ID.get(seg, 0);
        int ip = (int) VH_IP.get(seg, 0);
        short port = (short) VH_PORT.get(seg, 0);
        return new LobbyGameCreated(lobbyId, gameServerId, ip, port);
    }

}
