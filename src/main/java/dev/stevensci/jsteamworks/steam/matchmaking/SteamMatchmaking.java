package dev.stevensci.jsteamworks.steam.matchmaking;

import dev.stevensci.jsteamworks.callback.CallbackManager;
import dev.stevensci.jsteamworks.steam.matchmaking.callbacks.LobbyCreated;
import dev.stevensci.jsteamworks.steam.matchmaking.callbacks.LobbyEnter;
import dev.stevensci.jsteamworks.steam.matchmaking.callbacks.LobbyMatchList;
import dev.stevensci.jsteamworks.steam.matchmaking.enums.ChatEntryType;
import dev.stevensci.jsteamworks.steam.matchmaking.enums.LobbyComparison;
import dev.stevensci.jsteamworks.steam.matchmaking.enums.LobbyDistanceFilter;
import dev.stevensci.jsteamworks.steam.matchmaking.enums.LobbyType;
import dev.stevensci.jsteamworks.steam.matchmaking.types.ChatEntry;
import dev.stevensci.jsteamworks.steam.matchmaking.types.FavoriteGame;
import dev.stevensci.jsteamworks.steam.matchmaking.types.LobbyDataEntry;
import dev.stevensci.jsteamworks.steam.matchmaking.types.LobbyGameServer;
import dev.stevensci.jsteamworks.util.StringUtil;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static dev.stevensci.jsteamworks.steam.matchmaking.SteamMatchmakingNative.*;

public final class SteamMatchmaking {

    private static final int CHAT_BUFFER_SIZE = 4096;
    private static final int LOBBY_KEY_BUFFER_SIZE = 256;
    private static final int LOBBY_VALUE_BUFFER_SIZE = 8192;

    private static MemorySegment self;

    private SteamMatchmaking() {
        throw new UnsupportedOperationException();
    }

    private static MemorySegment self() {
        if (self == null) {
            try {
                self = (MemorySegment) SteamAPI_SteamMatchmaking_v009.invokeExact();
            } catch (Throwable t) {
                throw new RuntimeException("Failed to resolve ISteamMatchmaking interface", t);
            }
            if (self.equals(MemorySegment.NULL)) {
                throw new IllegalStateException("ISteamMatchmaking pointer is null - was SteamAPI.init() called and did it succeed?");
            }
        }
        return self;
    }

    public static int addFavoriteGame(int appId, int ip, short connPort, short queryPort, int flags, int lastPlayedOnServer) {
        try {
            return (int) SteamAPI_ISteamMatchmaking_AddFavoriteGame.invokeExact(
                    self(), appId, ip, connPort, queryPort, flags, lastPlayedOnServer);
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ISteamMatchmaking_AddFavoriteGame call failed", t);
        }
    }

    public static int getFavoriteGameCount() {
        try {
            return (int) SteamAPI_ISteamMatchmaking_GetFavoriteGameCount.invokeExact(self());
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ISteamMatchmaking_GetFavoriteGameCount call failed", t);
        }
    }

    public static FavoriteGame getFavoriteGame(int game) {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment appIdOut = arena.allocate(ValueLayout.JAVA_INT);
            MemorySegment ipOut = arena.allocate(ValueLayout.JAVA_INT);
            MemorySegment connPortOut = arena.allocate(ValueLayout.JAVA_SHORT);
            MemorySegment queryPortOut = arena.allocate(ValueLayout.JAVA_SHORT);
            MemorySegment flagsOut = arena.allocate(ValueLayout.JAVA_INT);
            MemorySegment lastPlayedOut = arena.allocate(ValueLayout.JAVA_INT);

            boolean found = (boolean) SteamAPI_ISteamMatchmaking_GetFavoriteGame.invokeExact(
                    self(), game, appIdOut, ipOut, connPortOut, queryPortOut, flagsOut, lastPlayedOut);

            if (!found) return null;

            return new FavoriteGame(
                    appIdOut.get(ValueLayout.JAVA_INT, 0),
                    ipOut.get(ValueLayout.JAVA_INT, 0),
                    connPortOut.get(ValueLayout.JAVA_SHORT, 0),
                    queryPortOut.get(ValueLayout.JAVA_SHORT, 0),
                    flagsOut.get(ValueLayout.JAVA_INT, 0),
                    lastPlayedOut.get(ValueLayout.JAVA_INT, 0));
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ISteamMatchmaking_GetFavoriteGame call failed", t);
        }
    }

    public static boolean removeFavoriteGame(int appId, int ip, short connPort, short queryPort, int flags) {
        try {
            return (boolean) SteamAPI_ISteamMatchmaking_RemoveFavoriteGame.invokeExact(
                    self(), appId, ip, connPort, queryPort, flags);
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ISteamMatchmaking_RemoveFavoriteGame call failed", t);
        }
    }

    public static void requestLobbyList(Consumer<LobbyMatchList> callback) {
        long apiCall = requestLobbyList();
        CallbackManager.subscribe(apiCall, LobbyMatchList.CALLBACK, callback);
    }

    private static long requestLobbyList() {
        try {
            return (long) SteamAPI_ISteamMatchmaking_RequestLobbyList.invokeExact(self());
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ISteamMatchmaking_RequestLobbyList call failed", t);
        }
    }

    public static void addRequestLobbyListStringFilter(String keyToMatch, String valueToMatch, LobbyComparison comparisonType) {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment key = StringUtil.allocateString(arena, keyToMatch);
            MemorySegment value = StringUtil.allocateString(arena, valueToMatch);

            SteamAPI_ISteamMatchmaking_AddRequestLobbyListStringFilter.invokeExact(
                    self(), key, value, comparisonType.getValue());
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ISteamMatchmaking_AddRequestLobbyListStringFilter call failed", t);
        }
    }

    public static void addRequestLobbyListNumericalFilter(String keyToMatch, int valueToMatch, LobbyComparison comparisonType) {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment key = StringUtil.allocateString(arena, keyToMatch);

            SteamAPI_ISteamMatchmaking_AddRequestLobbyListNumericalFilter.invokeExact(
                    self(), key, valueToMatch, comparisonType.getValue());
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ISteamMatchmaking_AddRequestLobbyListNumericalFilter call failed", t);
        }
    }

    public static void addRequestLobbyListNearValueFilter(String keyToMatch, int valueToBeCloseTo) {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment key = StringUtil.allocateString(arena, keyToMatch);
            SteamAPI_ISteamMatchmaking_AddRequestLobbyListNearValueFilter.invokeExact(self(), key, valueToBeCloseTo);
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ISteamMatchmaking_AddRequestLobbyListNearValueFilter call failed", t);
        }
    }

    public static void addRequestLobbyListFilterSlotsAvailable(int slotsAvailable) {
        try {
            SteamAPI_ISteamMatchmaking_AddRequestLobbyListFilterSlotsAvailable.invokeExact(self(), slotsAvailable);
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ISteamMatchmaking_AddRequestLobbyListFilterSlotsAvailable call failed", t);
        }
    }

    public static void addRequestLobbyListDistanceFilter(LobbyDistanceFilter distanceFilter) {
        try {
            SteamAPI_ISteamMatchmaking_AddRequestLobbyListDistanceFilter.invokeExact(self(), distanceFilter.ordinal());
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ISteamMatchmaking_AddRequestLobbyListDistanceFilter call failed", t);
        }
    }

    public static void addRequestLobbyListResultCountFilter(int maxResults) {
        try {
            SteamAPI_ISteamMatchmaking_AddRequestLobbyListResultCountFilter.invokeExact(self(), maxResults);
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ISteamMatchmaking_AddRequestLobbyListResultCountFilter call failed", t);
        }
    }

    public static void addRequestLobbyListCompatibleMembersFilter(long steamIdLobby) {
        try {
            SteamAPI_ISteamMatchmaking_AddRequestLobbyListCompatibleMembersFilter.invokeExact(self(), steamIdLobby);
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ISteamMatchmaking_AddRequestLobbyListCompatibleMembersFilter call failed", t);
        }
    }

    public static long getLobbyByIndex(int index) {
        try {
            return (long) SteamAPI_ISteamMatchmaking_GetLobbyByIndex.invokeExact(self(), index);
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ISteamMatchmaking_GetLobbyByIndex call failed", t);
        }
    }

    public static void createLobby(LobbyType type, int maxMembers, Consumer<LobbyCreated> callback) {
        long apiCall = createLobby(type, maxMembers);
        CallbackManager.subscribe(apiCall, LobbyCreated.CALLBACK, callback);
    }

    public static long createLobby(LobbyType type, int maxMembers) {
        try {
            return (long) SteamAPI_ISteamMatchmaking_CreateLobby.invokeExact(self(), type.ordinal(), maxMembers);
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ISteamMatchmaking_CreateLobby call failed", t);
        }
    }

    public static void joinLobby(long steamIdLobby, Consumer<LobbyEnter> callback) {
        long apiCall = joinLobby(steamIdLobby);
        CallbackManager.subscribe(apiCall, LobbyEnter.CALLBACK, callback);
    }

    public static long joinLobby(long steamIdLobby) {
        try {
            return (long) SteamAPI_ISteamMatchmaking_JoinLobby.invokeExact(self(), steamIdLobby);
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ISteamMatchmaking_JoinLobby call failed", t);
        }
    }

    public static void leaveLobby(long steamIdLobby) {
        try {
            SteamAPI_ISteamMatchmaking_LeaveLobby.invokeExact(self(), steamIdLobby);
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ISteamMatchmaking_LeaveLobby call failed", t);
        }
    }

    public static boolean inviteUserToLobby(long steamIdLobby, long steamIdInvitee) {
        try {
            return (boolean) SteamAPI_ISteamMatchmaking_InviteUserToLobby.invokeExact(self(), steamIdLobby, steamIdInvitee);
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ISteamMatchmaking_InviteUserToLobby call failed", t);
        }
    }

    public static int getNumLobbyMembers(long steamIdLobby) {
        try {
            return (int) SteamAPI_ISteamMatchmaking_GetNumLobbyMembers.invokeExact(self(), steamIdLobby);
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ISteamMatchmaking_GetNumLobbyMembers call failed", t);
        }
    }

    public static long getLobbyMemberByIndex(long steamIdLobby, int member) {
        try {
            return (long) SteamAPI_ISteamMatchmaking_GetLobbyMemberByIndex.invokeExact(self(), steamIdLobby, member);
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ISteamMatchmaking_GetLobbyMemberByIndex call failed", t);
        }
    }

    public static int getLobbyMemberLimit(long steamIdLobby) {
        try {
            return (int) SteamAPI_ISteamMatchmaking_GetLobbyMemberLimit.invokeExact(self(), steamIdLobby);
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ISteamMatchmaking_GetLobbyMemberLimit call failed", t);
        }
    }

    public static boolean setLobbyMemberLimit(long steamIdLobby, int maxMembers) {
        try {
            return (boolean) SteamAPI_ISteamMatchmaking_SetLobbyMemberLimit.invokeExact(self(), steamIdLobby, maxMembers);
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ISteamMatchmaking_SetLobbyMemberLimit call failed", t);
        }
    }

    public static long getLobbyOwner(long steamIdLobby) {
        try {
            return (long) SteamAPI_ISteamMatchmaking_GetLobbyOwner.invokeExact(self(), steamIdLobby);
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ISteamMatchmaking_GetLobbyOwner call failed", t);
        }
    }

    public static boolean setLobbyOwner(long steamIdLobby, long steamIdNewOwner) {
        try {
            return (boolean) SteamAPI_ISteamMatchmaking_SetLobbyOwner.invokeExact(self(), steamIdLobby, steamIdNewOwner);
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ISteamMatchmaking_SetLobbyOwner call failed", t);
        }
    }

    public static boolean setLinkedLobby(long steamIdLobby, long steamIdLobbyDependent) {
        try {
            return (boolean) SteamAPI_ISteamMatchmaking_SetLinkedLobby.invokeExact(self(), steamIdLobby, steamIdLobbyDependent);
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ISteamMatchmaking_SetLinkedLobby call failed", t);
        }
    }

    public static String getLobbyData(long steamIdLobby, String key) {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment k = StringUtil.allocateString(arena, key);
            MemorySegment ptr = (MemorySegment) SteamAPI_ISteamMatchmaking_GetLobbyData.invokeExact(self(), steamIdLobby, k);
            return StringUtil.readString(ptr);
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ISteamMatchmaking_GetLobbyData call failed", t);
        }
    }

    public static boolean setLobbyData(long steamIdLobby, String key, String value) {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment k = StringUtil.allocateString(arena, key);
            MemorySegment v = StringUtil.allocateString(arena, value);
            return (boolean) SteamAPI_ISteamMatchmaking_SetLobbyData.invokeExact(self(), steamIdLobby, k, v);
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ISteamMatchmaking_SetLobbyData call failed", t);
        }
    }

    public static int getLobbyDataCount(long steamIdLobby) {
        try {
            return (int) SteamAPI_ISteamMatchmaking_GetLobbyDataCount.invokeExact(self(), steamIdLobby);
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ISteamMatchmaking_GetLobbyDataCount call failed", t);
        }
    }

    public static LobbyDataEntry getLobbyDataByIndex(long steamIdLobby, int index) {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment keyOut = arena.allocate(LOBBY_KEY_BUFFER_SIZE);
            MemorySegment valueOut = arena.allocate(LOBBY_VALUE_BUFFER_SIZE);

            boolean success = (boolean) SteamAPI_ISteamMatchmaking_GetLobbyDataByIndex.invokeExact(
                    self(), steamIdLobby, index, keyOut, LOBBY_KEY_BUFFER_SIZE, valueOut, LOBBY_VALUE_BUFFER_SIZE);

            if (!success) {
                return null;
            }

            return new LobbyDataEntry(StringUtil.readString(keyOut), StringUtil.readString(valueOut));
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ISteamMatchmaking_GetLobbyDataByIndex call failed", t);
        }
    }

    public static List<LobbyDataEntry> getAllLobbyData(long steamIdLobby) {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment keyOut = arena.allocate(LOBBY_KEY_BUFFER_SIZE);
            MemorySegment valueOut = arena.allocate(LOBBY_VALUE_BUFFER_SIZE);

            int count = getLobbyDataCount(steamIdLobby);
            List<LobbyDataEntry> entries = new ArrayList<>(count);

            for (int i = 0; i < count; i++) {
                boolean success = (boolean) SteamAPI_ISteamMatchmaking_GetLobbyDataByIndex.invokeExact(
                        self(), steamIdLobby, i, keyOut, LOBBY_KEY_BUFFER_SIZE, valueOut, LOBBY_VALUE_BUFFER_SIZE);

                if (success) {
                    entries.add(new LobbyDataEntry(StringUtil.readString(keyOut), StringUtil.readString(valueOut)));
                }
            }
            return entries;
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ISteamMatchmaking_GetLobbyDataByIndex call failed", t);
        }
    }

    public static boolean deleteLobbyData(long steamIdLobby, String key) {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment k = StringUtil.allocateString(arena, key);
            return (boolean) SteamAPI_ISteamMatchmaking_DeleteLobbyData.invokeExact(self(), steamIdLobby, k);
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ISteamMatchmaking_DeleteLobbyData call failed", t);
        }
    }

    public static String getLobbyMemberData(long steamIdLobby, long steamIdUser, String key) {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment k = StringUtil.allocateString(arena, key);
            MemorySegment ptr = (MemorySegment) SteamAPI_ISteamMatchmaking_GetLobbyMemberData.invokeExact(self(), steamIdLobby, steamIdUser, k);
            return StringUtil.readString(ptr);
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ISteamMatchmaking_GetLobbyMemberData call failed", t);
        }
    }

    public static void setLobbyMemberData(long steamIdLobby, String key, String value) {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment k = StringUtil.allocateString(arena, key);
            MemorySegment v = StringUtil.allocateString(arena, value);
            SteamAPI_ISteamMatchmaking_SetLobbyMemberData.invokeExact(self(), steamIdLobby, k, v);
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ISteamMatchmaking_SetLobbyMemberData call failed", t);
        }
    }

    public static boolean requestLobbyData(long steamIdLobby) {
        try {
            return (boolean) SteamAPI_ISteamMatchmaking_RequestLobbyData.invokeExact(self(), steamIdLobby);
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ISteamMatchmaking_RequestLobbyData call failed", t);
        }
    }

    public static boolean sendLobbyChatMsg(long steamIdLobby, byte[] message) {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment body = arena.allocate(message.length);
            MemorySegment.copy(message, 0, body, ValueLayout.JAVA_BYTE, 0, message.length);
            return (boolean) SteamAPI_ISteamMatchmaking_SendLobbyChatMsg.invokeExact(self(), steamIdLobby, body, message.length);
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ISteamMatchmaking_SendLobbyChatMsg call failed", t);
        }
    }

    public static boolean sendLobbyChatMsg(long steamIdLobby, String message) {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment body = StringUtil.allocateString(arena, message);
            int length = message.getBytes(StandardCharsets.UTF_8).length + 1;
            return (boolean) SteamAPI_ISteamMatchmaking_SendLobbyChatMsg.invokeExact(self(), steamIdLobby, body, length);
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ISteamMatchmaking_SendLobbyChatMsg call failed", t);
        }
    }

    public static ChatEntry getLobbyChatEntry(long steamIdLobby, int chatId) {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment userIdOut = arena.allocate(ValueLayout.JAVA_LONG);
            MemorySegment dataOut = arena.allocate(CHAT_BUFFER_SIZE);
            MemorySegment entryTypeOut = arena.allocate(ValueLayout.JAVA_INT);

            int written = (int) SteamAPI_ISteamMatchmaking_GetLobbyChatEntry.invokeExact(
                    self(), steamIdLobby, chatId, userIdOut, dataOut, CHAT_BUFFER_SIZE, entryTypeOut);

            long userId = userIdOut.get(ValueLayout.JAVA_LONG, 0);
            int entryType = entryTypeOut.get(ValueLayout.JAVA_INT, 0);

            byte[] data = new byte[Math.max(written, 0)];
            MemorySegment.copy(dataOut, ValueLayout.JAVA_BYTE, 0, data, 0, data.length);

            return new ChatEntry(userId, ChatEntryType.fromCode(entryType), data);
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ISteamMatchmaking_GetLobbyChatEntry call failed", t);
        }
    }

    public static void setLobbyGameServer(long steamIdLobby, int gameServerIp, short gameServerPort, long steamIdGameServer) {
        try {
            SteamAPI_ISteamMatchmaking_SetLobbyGameServer.invokeExact(self(), steamIdLobby, gameServerIp, gameServerPort, steamIdGameServer);
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ISteamMatchmaking_SetLobbyGameServer call failed", t);
        }
    }

    public static LobbyGameServer getLobbyGameServer(long steamIdLobby) {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment ipOut = arena.allocate(ValueLayout.JAVA_INT);
            MemorySegment portOut = arena.allocate(ValueLayout.JAVA_SHORT);
            MemorySegment steamIdOut = arena.allocate(ValueLayout.JAVA_LONG);

            boolean hasGameServer = (boolean) SteamAPI_ISteamMatchmaking_GetLobbyGameServer.invokeExact(self(), steamIdLobby, ipOut, portOut, steamIdOut);

            if (!hasGameServer) {
                return null;
            }

            return new LobbyGameServer(
                    ipOut.get(ValueLayout.JAVA_INT, 0),
                    portOut.get(ValueLayout.JAVA_SHORT, 0),
                    steamIdOut.get(ValueLayout.JAVA_LONG, 0)
            );
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ISteamMatchmaking_GetLobbyGameServer call failed", t);
        }
    }

    public static boolean setLobbyType(long steamIdLobby, LobbyType type) {
        try {
            return (boolean) SteamAPI_ISteamMatchmaking_SetLobbyType.invokeExact(self(), steamIdLobby, type.ordinal());
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ISteamMatchmaking_SetLobbyType call failed", t);
        }
    }

    public static boolean setLobbyJoinable(long steamIdLobby, boolean joinable) {
        try {
            return (boolean) SteamAPI_ISteamMatchmaking_SetLobbyJoinable.invokeExact(self(), steamIdLobby, joinable);
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ISteamMatchmaking_SetLobbyJoinable call failed", t);
        }
    }

}