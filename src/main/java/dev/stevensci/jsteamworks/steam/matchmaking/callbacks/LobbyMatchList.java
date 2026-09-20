package dev.stevensci.jsteamworks.steam.matchmaking.callbacks;

import dev.stevensci.jsteamworks.callback.CallbackIdOffset;
import dev.stevensci.jsteamworks.callback.SteamCallback;
import dev.stevensci.jsteamworks.util.Struct;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.StructLayout;
import java.lang.invoke.VarHandle;

public record LobbyMatchList(int lobbiesMatching) {

    public static final int CALLBACK_ID = CallbackIdOffset.STEAM_MATCHMAKING + 10;
    public static final SteamCallback<LobbyMatchList> CALLBACK = SteamCallback.of(CALLBACK_ID, LobbyMatchList::decode);

    private static final StructLayout LAYOUT = Struct.builder()
            .intField("m_nLobbiesMatching")
            .build("LobbyMatchList_t");

    private static final VarHandle VH_LOBBIES_MATCHING = Struct.varHandle(LAYOUT, "m_nLobbiesMatching");

    private static LobbyMatchList decode(MemorySegment seg) {
        int lobbiesMatching = (int) VH_LOBBIES_MATCHING.get(seg, 0);
        return new LobbyMatchList(lobbiesMatching);
    }

}
