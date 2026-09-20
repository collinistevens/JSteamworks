package dev.stevensci.jsteamworks.steam.matchmaking.callbacks;

import dev.stevensci.jsteamworks.callback.CallbackIdOffset;
import dev.stevensci.jsteamworks.callback.SteamCallback;
import dev.stevensci.jsteamworks.steam.matchmaking.enums.ChatRoomEnterResponse;
import dev.stevensci.jsteamworks.util.Struct;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.StructLayout;
import java.lang.invoke.VarHandle;

public record LobbyEnter(long lobbySteamId, int chatPermissions, boolean locked, ChatRoomEnterResponse enterResponse) {

    public static final int CALLBACK_ID = CallbackIdOffset.STEAM_MATCHMAKING + 4;
    public static final SteamCallback<LobbyEnter> CALLBACK = SteamCallback.of(CALLBACK_ID, LobbyEnter::decode);

    private static final StructLayout LAYOUT = Struct.builder()
            .longField("m_ulSteamIDLobby")
            .intField("m_rgfChatPermissions")
            .booleanField("m_bLocked")
            .intField("m_EChatRoomEnterResponse")
            .build("LobbyEnter_t");

    private static final VarHandle VH_LOBBY_ID = Struct.varHandle(LAYOUT, "m_ulSteamIDLobby");
    private static final VarHandle VH_PERMISSIONS = Struct.varHandle(LAYOUT, "m_rgfChatPermissions");
    private static final VarHandle VH_LOCKED = Struct.varHandle(LAYOUT, "m_bLocked");
    private static final VarHandle VH_ENTER_RESPONSE = Struct.varHandle(LAYOUT, "m_EChatRoomEnterResponse");

    private static LobbyEnter decode(MemorySegment seg) {
        long lobbyId = (long) VH_LOBBY_ID.get(seg, 0L);
        int permissions = (int) VH_PERMISSIONS.get(seg, 0L);
        boolean locked = (boolean) VH_LOCKED.get(seg, 0L);
        int enterResponse = (int) VH_ENTER_RESPONSE.get(seg, 0L);
        return new LobbyEnter(lobbyId, permissions, locked, ChatRoomEnterResponse.fromCode(enterResponse));
    }

}
