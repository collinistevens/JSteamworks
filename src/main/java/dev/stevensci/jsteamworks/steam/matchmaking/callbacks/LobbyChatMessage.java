package dev.stevensci.jsteamworks.steam.matchmaking.callbacks;

import dev.stevensci.jsteamworks.callback.CallbackIdOffset;
import dev.stevensci.jsteamworks.callback.SteamCallback;
import dev.stevensci.jsteamworks.steam.matchmaking.SteamMatchmaking;
import dev.stevensci.jsteamworks.steam.matchmaking.enums.ChatEntryType;
import dev.stevensci.jsteamworks.steam.matchmaking.types.ChatEntry;
import dev.stevensci.jsteamworks.util.Struct;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.StructLayout;
import java.lang.invoke.VarHandle;

public record LobbyChatMessage(long lobbyId, long userId, ChatEntryType entryType, int chatId) {

    public static final int CALLBACK_ID = CallbackIdOffset.STEAM_MATCHMAKING + 7;
    public static final SteamCallback<LobbyChatMessage> CALLBACK = SteamCallback.of(CALLBACK_ID, LobbyChatMessage::decode);

    private static final StructLayout LAYOUT = Struct.builder()
            .longField("m_ulSteamIDLobby")
            .longField("m_ulSteamIDUser")
            .byteField("m_eChatEntryType")
            .intField("m_iChatID")
            .build("LobbyChatMsg_t");

    private static final VarHandle VH_LOBBY_ID = Struct.varHandle(LAYOUT, "m_ulSteamIDLobby");
    private static final VarHandle VH_USER_ID = Struct.varHandle(LAYOUT, "m_ulSteamIDUser");
    private static final VarHandle VH_ENTRY_TYPE = Struct.varHandle(LAYOUT, "m_eChatEntryType");
    private static final VarHandle VH_CHAT_ID = Struct.varHandle(LAYOUT, "m_iChatID");

    private static LobbyChatMessage decode(MemorySegment seg) {
        long lobbyId = (long) VH_LOBBY_ID.get(seg, 0);
        long userId = (long) VH_USER_ID.get(seg, 0);
        byte entryType = (byte) VH_ENTRY_TYPE.get(seg, 0);
        int chatId = (int) VH_CHAT_ID.get(seg, 0);
        return new LobbyChatMessage(lobbyId, userId, ChatEntryType.fromCode(entryType), chatId);
    }

    public ChatEntry chatEntry() {
        return SteamMatchmaking.getLobbyChatEntry(this.lobbyId, this.chatId);
    }

}
