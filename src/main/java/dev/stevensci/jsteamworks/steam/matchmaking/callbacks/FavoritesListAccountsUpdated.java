package dev.stevensci.jsteamworks.steam.matchmaking.callbacks;

import dev.stevensci.jsteamworks.SteamResult;
import dev.stevensci.jsteamworks.callback.CallbackIdOffset;
import dev.stevensci.jsteamworks.callback.SteamCallback;
import dev.stevensci.jsteamworks.util.Struct;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.StructLayout;
import java.lang.invoke.VarHandle;

public record FavoritesListAccountsUpdated(SteamResult result) {

    public static final int CALLBACK_ID = CallbackIdOffset.STEAM_MATCHMAKING + 16;
    public static final SteamCallback<FavoritesListAccountsUpdated> CALLBACK = SteamCallback.of(CALLBACK_ID, FavoritesListAccountsUpdated::decode);

    private static final StructLayout LAYOUT = Struct.builder()
            .intField("m_eResult")
            .build("FavoritesListAccountsUpdated_t");

    private static final VarHandle VH_RESULT = Struct.varHandle(LAYOUT, "m_eResult");

    private static FavoritesListAccountsUpdated decode(MemorySegment seg) {
        int result = (int) VH_RESULT.get(seg, 0);
        return new FavoritesListAccountsUpdated(SteamResult.fromCode(result));
    }

}
