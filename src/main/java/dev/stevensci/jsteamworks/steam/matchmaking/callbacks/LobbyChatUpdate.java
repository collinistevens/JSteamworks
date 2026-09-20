package dev.stevensci.jsteamworks.steam.matchmaking.callbacks;

import dev.stevensci.jsteamworks.callback.CallbackIdOffset;
import dev.stevensci.jsteamworks.callback.SteamCallback;
import dev.stevensci.jsteamworks.steam.matchmaking.enums.ChatMemberStateChange;
import dev.stevensci.jsteamworks.util.Struct;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.StructLayout;
import java.lang.invoke.VarHandle;
import java.util.EnumSet;

public record LobbyChatUpdate(long lobbyId, long userChanged, long makingChange, EnumSet<ChatMemberStateChange> stateChange) {

    public static final int CALLBACK_ID = CallbackIdOffset.STEAM_MATCHMAKING + 6;
    public static final SteamCallback<LobbyChatUpdate> CALLBACK = SteamCallback.of(CALLBACK_ID, LobbyChatUpdate::decode);

    private static final StructLayout LAYOUT = Struct.builder()
            .longField("m_ulSteamIDLobby")
            .longField("m_ulSteamIDUserChanged")
            .longField("m_ulSteamIDMakingChange")
            .intField("m_rgfChatMemberStateChange")
            .build("LobbyChatUpdate_t");

    private static final VarHandle VH_LOBBY_ID = Struct.varHandle(LAYOUT, "m_ulSteamIDLobby");
    private static final VarHandle VH_USER_CHANGED = Struct.varHandle(LAYOUT, "m_ulSteamIDUserChanged");
    private static final VarHandle VH_MAKING_CHANGE = Struct.varHandle(LAYOUT, "m_ulSteamIDMakingChange");
    private static final VarHandle VH_STATE_CHANGE = Struct.varHandle(LAYOUT, "m_rgfChatMemberStateChange");

    private static LobbyChatUpdate decode(MemorySegment seg) {
        long lobbyId = (long) VH_LOBBY_ID.get(seg, 0);
        long userChanged = (long) VH_USER_CHANGED.get(seg, 0);
        long makingChange = (long) VH_MAKING_CHANGE.get(seg, 0);
        int stateChange = (int) VH_STATE_CHANGE.get(seg, 0);
        return new LobbyChatUpdate(lobbyId, userChanged, makingChange, ChatMemberStateChange.fromBits(stateChange));
    }

}
