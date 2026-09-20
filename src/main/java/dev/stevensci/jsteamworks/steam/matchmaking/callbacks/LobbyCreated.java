package dev.stevensci.jsteamworks.steam.matchmaking.callbacks;

import dev.stevensci.jsteamworks.SteamResult;
import dev.stevensci.jsteamworks.callback.CallbackIdOffset;
import dev.stevensci.jsteamworks.callback.SteamCallback;
import dev.stevensci.jsteamworks.util.Struct;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.StructLayout;
import java.lang.invoke.VarHandle;

public record LobbyCreated(SteamResult result, long lobbySteamId) {

    public static final int CALLBACK_ID = CallbackIdOffset.STEAM_MATCHMAKING + 13;
    public static final SteamCallback<LobbyCreated> CALLBACK = SteamCallback.of(CALLBACK_ID, LobbyCreated::decode);

    private static final StructLayout LAYOUT = Struct.builder()
            .intField("m_eResult")
            .longField("m_ulSteamIDLobby")
            .build("LobbyCreated_t");

    private static final VarHandle VH_RESULT = Struct.varHandle(LAYOUT, "m_eResult");
    private static final VarHandle VH_LOBBY_ID = Struct.varHandle(LAYOUT, "m_ulSteamIDLobby");

    private static LobbyCreated decode(MemorySegment seg) {
        int result = (int) VH_RESULT.get(seg, 0);
        long lobbyId = (long) VH_LOBBY_ID.get(seg, 0);
        return new LobbyCreated(SteamResult.fromCode(result), lobbyId);
    }

}
