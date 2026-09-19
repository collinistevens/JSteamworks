package dev.stevensci.jsteamworks.steam.steamapi;

import dev.stevensci.jsteamworks.util.SteamNative;

import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;

final class SteamAPINative {

    private SteamAPINative() {
        throw new UnsupportedOperationException();
    }

    static final int MAX_STEAM_ERR_MSG = 1024;

    static final String[] INTERFACE_NAMES = {
            "SteamUtils011",
            "SteamNetworkingUtils004",
            "STEAMAPPS_INTERFACE_VERSION009",
            "SteamController008",
            "SteamFriends018",
            "STEAMHTMLSURFACE_INTERFACE_VERSION_005",
            "STEAMHTTP_INTERFACE_VERSION003",
            "SteamInput007",
            "STEAMINVENTORY_INTERFACE_V003",
            "SteamMatchMakingServers003",
            "SteamMatchMaking009",
            "STEAMMUSIC_INTERFACE_VERSION001",
            "SteamNetworkingMessages002",
            "SteamNetworkingSockets013",
            "SteamNetworking006",
            "STEAMPARENTALSETTINGS_INTERFACE_VERSION001",
            "SteamParties002",
            "STEAMREMOTEPLAY_INTERFACE_VERSION004",
            "STEAMREMOTESTORAGE_INTERFACE_VERSION016",
            "STEAMSCREENSHOTS_INTERFACE_VERSION003",
            "STEAMUGC_INTERFACE_VERSION021",
            "STEAMUSERSTATS_INTERFACE_VERSION013",
            "SteamUser023",
            "STEAMVIDEO_INTERFACE_V007"
    };

    static final String INTERFACE_VERSIONS = String.join("\0", INTERFACE_NAMES) + '\0';

    static final MethodHandle SteamInternal_SteamAPI_Init = SteamNative.bind(
            "SteamInternal_SteamAPI_Init",
            ValueLayout.JAVA_INT,
            ValueLayout.ADDRESS,
            ValueLayout.ADDRESS);

    static final MethodHandle SteamAPI_Shutdown = SteamNative.bindVoid(
            "SteamAPI_Shutdown");

    static final MethodHandle SteamAPI_IsSteamRunning = SteamNative.bind(
            "SteamAPI_IsSteamRunning",
            ValueLayout.JAVA_BOOLEAN);

}
