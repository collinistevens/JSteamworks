package dev.stevensci.jsteamworks.steam.matchmaking.callbacks;

import dev.stevensci.jsteamworks.callback.CallbackIdOffset;
import dev.stevensci.jsteamworks.callback.SteamCallback;
import dev.stevensci.jsteamworks.util.Struct;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.StructLayout;
import java.lang.invoke.VarHandle;

public record LobbyKicked(long lobbySteamId, long adminSteamId, boolean kickedDueToDisconnect) {

    public static final int CALLBACK_ID = CallbackIdOffset.STEAM_MATCHMAKING + 12;
    public static final SteamCallback<LobbyKicked> CALLBACK = SteamCallback.of(CALLBACK_ID, LobbyKicked::decode);

    private static final StructLayout LAYOUT = Struct.builder()
            .longField("m_ulSteamIDLobby")
            .longField("m_ulSteamIDAdmin")
            .booleanField("m_bKickedDueToDisconnect")
            .build("LobbyKicked_t");

    private static final VarHandle VH_LOBBY_ID = Struct.varHandle(LAYOUT, "m_ulSteamIDLobby");
    private static final VarHandle VH_ADMIN_ID = Struct.varHandle(LAYOUT, "m_ulSteamIDAdmin");
    private static final VarHandle VH_KICKED = Struct.varHandle(LAYOUT, "m_bKickedDueToDisconnect");

    private static LobbyKicked decode(MemorySegment seg) {
        long lobbyId = (long) VH_LOBBY_ID.get(seg, 0);
        long adminId = (long) VH_ADMIN_ID.get(seg, 0);
        boolean kicked = (boolean) VH_KICKED.get(seg, 0);
        return new LobbyKicked(lobbyId, adminId, kicked);
    }

}
