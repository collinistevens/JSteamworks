package dev.stevensci.jsteamworks.steam.steamapi;

import dev.stevensci.jsteamworks.util.StringUtil;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

import static dev.stevensci.jsteamworks.steam.steamapi.SteamAPINative.*;

public final class SteamAPI {

    private SteamAPI() {
        throw new UnsupportedOperationException();
    }

    public static void init() {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment errMsg = arena.allocate(MAX_STEAM_ERR_MSG);

            int code;
            try {
                code = (int) SteamInternal_SteamAPI_Init.invokeExact(StringUtil.allocateString(arena, SteamAPINative.INTERFACE_VERSIONS), errMsg);
            } catch (Throwable t) {
                throw new IllegalStateException("SteamInternal_SteamAPI_Init call failed", t);
            }

            if (code != 0) {
                throw new IllegalStateException("SteamAPI failed to initialize: " + errMsg.getString(0));
            }
        }
    }

    public static boolean isSteamRunning() {
        try {
            return (boolean) SteamAPI_IsSteamRunning.invokeExact();
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_IsSteamRunning call failed", t);
        }
    }

    public static void shutdown() {
        try {
            SteamAPI_Shutdown.invokeExact();
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_Shutdown call failed", t);
        }
    }

}
