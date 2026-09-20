package dev.stevensci.jsteamworks.steam.matchmaking;

import dev.stevensci.jsteamworks.util.SteamNative;

import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;

final class SteamMatchmakingNative {

    private SteamMatchmakingNative() {
        throw new UnsupportedOperationException();
    }

    static final MethodHandle SteamAPI_SteamMatchmaking_v009 = SteamNative.bind(
            "SteamAPI_SteamMatchmaking_v009",
            ValueLayout.ADDRESS);

    static final MethodHandle SteamAPI_ISteamMatchmaking_AddFavoriteGame = SteamNative.bind(
            "SteamAPI_ISteamMatchmaking_AddFavoriteGame",
            ValueLayout.JAVA_INT,
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_INT,
            ValueLayout.JAVA_INT,
            ValueLayout.JAVA_SHORT,
            ValueLayout.JAVA_SHORT,
            ValueLayout.JAVA_INT,
            ValueLayout.JAVA_INT);

    static final MethodHandle SteamAPI_ISteamMatchmaking_GetFavoriteGameCount = SteamNative.bind(
            "SteamAPI_ISteamMatchmaking_GetFavoriteGameCount",
            ValueLayout.JAVA_INT,
            ValueLayout.ADDRESS);

    static final MethodHandle SteamAPI_ISteamMatchmaking_GetFavoriteGame = SteamNative.bind(
            "SteamAPI_ISteamMatchmaking_GetFavoriteGame",
            ValueLayout.JAVA_BOOLEAN,
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_INT,
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS);

    static final MethodHandle SteamAPI_ISteamMatchmaking_RemoveFavoriteGame = SteamNative.bind(
            "SteamAPI_ISteamMatchmaking_RemoveFavoriteGame",
            ValueLayout.JAVA_BOOLEAN,
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_INT,
            ValueLayout.JAVA_INT,
            ValueLayout.JAVA_SHORT,
            ValueLayout.JAVA_SHORT,
            ValueLayout.JAVA_INT);

    static final MethodHandle SteamAPI_ISteamMatchmaking_RequestLobbyList = SteamNative.bind(
            "SteamAPI_ISteamMatchmaking_RequestLobbyList",
            ValueLayout.JAVA_LONG,
            ValueLayout.ADDRESS);

    static final MethodHandle SteamAPI_ISteamMatchmaking_AddRequestLobbyListStringFilter = SteamNative.bindVoid(
            "SteamAPI_ISteamMatchmaking_AddRequestLobbyListStringFilter",
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_INT);

    static final MethodHandle SteamAPI_ISteamMatchmaking_AddRequestLobbyListNumericalFilter = SteamNative.bindVoid(
            "SteamAPI_ISteamMatchmaking_AddRequestLobbyListNumericalFilter",
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_INT,
            ValueLayout.JAVA_INT);

    static final MethodHandle SteamAPI_ISteamMatchmaking_AddRequestLobbyListNearValueFilter = SteamNative.bindVoid(
            "SteamAPI_ISteamMatchmaking_AddRequestLobbyListNearValueFilter",
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_INT);

    static final MethodHandle SteamAPI_ISteamMatchmaking_AddRequestLobbyListFilterSlotsAvailable = SteamNative.bindVoid(
            "SteamAPI_ISteamMatchmaking_AddRequestLobbyListFilterSlotsAvailable",
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_INT);

    static final MethodHandle SteamAPI_ISteamMatchmaking_AddRequestLobbyListDistanceFilter = SteamNative.bindVoid(
            "SteamAPI_ISteamMatchmaking_AddRequestLobbyListDistanceFilter",
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_INT);

    static final MethodHandle SteamAPI_ISteamMatchmaking_AddRequestLobbyListResultCountFilter = SteamNative.bindVoid(
            "SteamAPI_ISteamMatchmaking_AddRequestLobbyListResultCountFilter",
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_INT);

    static final MethodHandle SteamAPI_ISteamMatchmaking_GetLobbyByIndex = SteamNative.bind(
            "SteamAPI_ISteamMatchmaking_GetLobbyByIndex",
            ValueLayout.JAVA_LONG,
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_INT);

    static final MethodHandle SteamAPI_ISteamMatchmaking_CreateLobby = SteamNative.bind(
            "SteamAPI_ISteamMatchmaking_CreateLobby",
            ValueLayout.JAVA_LONG,
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_INT,
            ValueLayout.JAVA_INT);

    static final MethodHandle SteamAPI_ISteamMatchmaking_JoinLobby = SteamNative.bind(
            "SteamAPI_ISteamMatchmaking_JoinLobby",
            ValueLayout.JAVA_LONG,
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_LONG);
    
    static final MethodHandle SteamAPI_ISteamMatchmaking_LeaveLobby = SteamNative.bindVoid(
            "SteamAPI_ISteamMatchmaking_LeaveLobby",
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_LONG);
    
    static final MethodHandle SteamAPI_ISteamMatchmaking_InviteUserToLobby = SteamNative.bind(
            "SteamAPI_ISteamMatchmaking_InviteUserToLobby",
            ValueLayout.JAVA_BOOLEAN,
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_LONG,
            ValueLayout.JAVA_LONG);

    static final MethodHandle SteamAPI_ISteamMatchmaking_GetNumLobbyMembers = SteamNative.bind(
            "SteamAPI_ISteamMatchmaking_GetNumLobbyMembers",
            ValueLayout.JAVA_INT,
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_LONG);
    
    static final MethodHandle SteamAPI_ISteamMatchmaking_GetLobbyMemberByIndex = SteamNative.bind(
            "SteamAPI_ISteamMatchmaking_GetLobbyMemberByIndex",
            ValueLayout.JAVA_LONG,
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_LONG,
            ValueLayout.JAVA_INT);
    
    static final MethodHandle SteamAPI_ISteamMatchmaking_GetLobbyMemberLimit = SteamNative.bind(
            "SteamAPI_ISteamMatchmaking_GetLobbyMemberLimit",
            ValueLayout.JAVA_INT,
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_LONG);
    
    static final MethodHandle SteamAPI_ISteamMatchmaking_SetLobbyMemberLimit = SteamNative.bind(
            "SteamAPI_ISteamMatchmaking_SetLobbyMemberLimit",
            ValueLayout.JAVA_BOOLEAN,
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_LONG,
            ValueLayout.JAVA_INT);
    
    static final MethodHandle SteamAPI_ISteamMatchmaking_GetLobbyOwner = SteamNative.bind(
            "SteamAPI_ISteamMatchmaking_GetLobbyOwner",
            ValueLayout.JAVA_LONG,
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_LONG);
    
    static final MethodHandle SteamAPI_ISteamMatchmaking_SetLobbyOwner = SteamNative.bind(
            "SteamAPI_ISteamMatchmaking_SetLobbyOwner",
            ValueLayout.JAVA_BOOLEAN,
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_LONG,
            ValueLayout.JAVA_LONG);

    static final MethodHandle SteamAPI_ISteamMatchmaking_GetLobbyData = SteamNative.bind(
            "SteamAPI_ISteamMatchmaking_GetLobbyData",
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_LONG,
            ValueLayout.ADDRESS);
    
    static final MethodHandle SteamAPI_ISteamMatchmaking_SetLobbyData = SteamNative.bind(
            "SteamAPI_ISteamMatchmaking_SetLobbyData",
            ValueLayout.JAVA_BOOLEAN,
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_LONG,
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS);
    
    static final MethodHandle SteamAPI_ISteamMatchmaking_GetLobbyDataCount = SteamNative.bind(
            "SteamAPI_ISteamMatchmaking_GetLobbyDataCount",
            ValueLayout.JAVA_INT,
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_LONG);
    
    static final MethodHandle SteamAPI_ISteamMatchmaking_GetLobbyDataByIndex = SteamNative.bind(
            "SteamAPI_ISteamMatchmaking_GetLobbyDataByIndex",
            ValueLayout.JAVA_BOOLEAN,
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_LONG,
            ValueLayout.JAVA_INT,
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_INT,
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_INT);
    
    static final MethodHandle SteamAPI_ISteamMatchmaking_DeleteLobbyData = SteamNative.bind(
            "SteamAPI_ISteamMatchmaking_DeleteLobbyData",
            ValueLayout.JAVA_BOOLEAN,
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_LONG,
            ValueLayout.ADDRESS);
    
    static final MethodHandle SteamAPI_ISteamMatchmaking_GetLobbyMemberData = SteamNative.bind(
            "SteamAPI_ISteamMatchmaking_GetLobbyMemberData",
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_LONG,
            ValueLayout.JAVA_LONG,
            ValueLayout.ADDRESS);
    
    static final MethodHandle SteamAPI_ISteamMatchmaking_SetLobbyMemberData = SteamNative.bindVoid(
            "SteamAPI_ISteamMatchmaking_SetLobbyMemberData",
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_LONG,
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS);
    
    static final MethodHandle SteamAPI_ISteamMatchmaking_RequestLobbyData = SteamNative.bind(
            "SteamAPI_ISteamMatchmaking_RequestLobbyData",
            ValueLayout.JAVA_BOOLEAN,
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_LONG);

    static final MethodHandle SteamAPI_ISteamMatchmaking_SendLobbyChatMsg = SteamNative.bind(
            "SteamAPI_ISteamMatchmaking_SendLobbyChatMsg",
            ValueLayout.JAVA_BOOLEAN,
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_LONG,
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_INT);
    
    static final MethodHandle SteamAPI_ISteamMatchmaking_GetLobbyChatEntry = SteamNative.bind(
            "SteamAPI_ISteamMatchmaking_GetLobbyChatEntry",
            ValueLayout.JAVA_INT,
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_LONG,
            ValueLayout.JAVA_INT,
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_INT,
            ValueLayout.ADDRESS);

    static final MethodHandle SteamAPI_ISteamMatchmaking_SetLobbyGameServer = SteamNative.bindVoid(
            "SteamAPI_ISteamMatchmaking_SetLobbyGameServer",
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_LONG,
            ValueLayout.JAVA_INT,
            ValueLayout.JAVA_SHORT,
            ValueLayout.JAVA_LONG);
    
    static final MethodHandle SteamAPI_ISteamMatchmaking_GetLobbyGameServer = SteamNative.bind(
            "SteamAPI_ISteamMatchmaking_GetLobbyGameServer",
            ValueLayout.JAVA_BOOLEAN,
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_LONG,
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS);
    
    static final MethodHandle SteamAPI_ISteamMatchmaking_SetLobbyType = SteamNative.bind(
            "SteamAPI_ISteamMatchmaking_SetLobbyType",
            ValueLayout.JAVA_BOOLEAN,
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_LONG,
            ValueLayout.JAVA_INT);
    
    static final MethodHandle SteamAPI_ISteamMatchmaking_SetLobbyJoinable = SteamNative.bind(
            "SteamAPI_ISteamMatchmaking_SetLobbyJoinable",
            ValueLayout.JAVA_BOOLEAN,
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_LONG,
            ValueLayout.JAVA_BOOLEAN);

}
