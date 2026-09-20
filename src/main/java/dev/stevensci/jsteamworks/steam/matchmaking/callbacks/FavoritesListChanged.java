package dev.stevensci.jsteamworks.steam.matchmaking.callbacks;

import dev.stevensci.jsteamworks.callback.CallbackIdOffset;
import dev.stevensci.jsteamworks.callback.SteamCallback;
import dev.stevensci.jsteamworks.util.Struct;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.StructLayout;
import java.lang.invoke.VarHandle;

public record FavoritesListChanged(int ip, int queryPort, int connPort, int appId, int flags, boolean add, int accountId) {

    public static final int CALLBACK_ID = CallbackIdOffset.STEAM_MATCHMAKING + 2;
    public static final SteamCallback<FavoritesListChanged> CALLBACK = SteamCallback.of(CALLBACK_ID, FavoritesListChanged::decode);

    private static final StructLayout LAYOUT = Struct.builder()
            .intField("m_nIP")
            .intField("m_nQueryPort")
            .intField("m_nConnPort")
            .intField("m_nAppID")
            .intField("m_nFlags")
            .booleanField("m_bAdd")
            .intField("m_unAccountId")
            .build("FavoritesListChanged_t");

    private static final VarHandle VH_IP = Struct.varHandle(LAYOUT, "m_nIP");
    private static final VarHandle VH_QUERY_PORT = Struct.varHandle(LAYOUT, "m_nQueryPort");
    private static final VarHandle VH_CONN_PORT = Struct.varHandle(LAYOUT, "m_nConnPort");
    private static final VarHandle VH_APP_ID = Struct.varHandle(LAYOUT, "m_nAppID");
    private static final VarHandle VH_FLAGS = Struct.varHandle(LAYOUT, "m_nFlags");
    private static final VarHandle VH_ADD = Struct.varHandle(LAYOUT, "m_bAdd");
    private static final VarHandle VH_ACCOUNT_ID = Struct.varHandle(LAYOUT, "m_unAccountId");

    private static FavoritesListChanged decode(MemorySegment seg) {
        int ip = (int) VH_IP.get(seg, 0);
        int queryPort = (int) VH_QUERY_PORT.get(seg, 0);
        int connPort = (int) VH_CONN_PORT.get(seg, 0);
        int appId = (int) VH_APP_ID.get(seg, 0);
        int flags = (int) VH_FLAGS.get(seg, 0);
        boolean add = (boolean) VH_ADD.get(seg, 0);
        int accountId = (int) VH_ACCOUNT_ID.get(seg, 0);
        return new FavoritesListChanged(ip, queryPort, connPort, appId, flags, add, accountId);
    }

}
