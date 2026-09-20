package dev.stevensci.jsteamworks.steam.manualdispatch.types;

import dev.stevensci.jsteamworks.callback.CallbackIdOffset;
import dev.stevensci.jsteamworks.util.Struct;

import java.lang.foreign.StructLayout;
import java.lang.invoke.VarHandle;

public final class SteamAPICallCompleted {

    public static final int CALLBACK_ID = CallbackIdOffset.STEAM_UTILS + 3;

    public static final StructLayout LAYOUT = Struct.builder()
            .longField("m_hAsyncCall")
            .intField("m_iCallback")
            .intField("m_cubParam")
            .build("SteamAPICallCompleted_t");

    public static final VarHandle VH_ASYNC_CALL = Struct.varHandle(LAYOUT, "m_hAsyncCall");
    public static final VarHandle VH_CALLBACK = Struct.varHandle(LAYOUT, "m_iCallback");
    public static final VarHandle VH_CUB_PARAM = Struct.varHandle(LAYOUT, "m_cubParam");

}
