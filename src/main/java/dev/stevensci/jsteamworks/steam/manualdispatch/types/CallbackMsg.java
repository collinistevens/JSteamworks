package dev.stevensci.jsteamworks.steam.manualdispatch.types;

import dev.stevensci.jsteamworks.util.Struct;

import java.lang.foreign.StructLayout;
import java.lang.invoke.VarHandle;

public final class CallbackMsg {

    public static final StructLayout LAYOUT = Struct.builder()
            .intField("m_hSteamUser")
            .intField("m_iCallback")
            .addressField("m_pubParam")
            .intField("m_cubParam")
            .build("CallbackMsg_t");

    private CallbackMsg() {
        throw new UnsupportedOperationException();
    }

    public static final VarHandle VH_STEAM_USER = Struct.varHandle(LAYOUT, "m_hSteamUser");
    public static final VarHandle VH_CALLBACK = Struct.varHandle(LAYOUT, "m_iCallback");
    public static final VarHandle VH_PUB_PARAM = Struct.varHandle(LAYOUT, "m_pubParam");
    public static final VarHandle VH_CUB_PARAM = Struct.varHandle(LAYOUT, "m_cubParam");

}
