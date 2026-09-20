package dev.stevensci.jsteamworks.steam.manualdispatch;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

import static dev.stevensci.jsteamworks.steam.manualdispatch.SteamManualDispatchNative.*;

public final class SteamManualDispatch {

    private SteamManualDispatch() {
        throw new UnsupportedOperationException();
    }

    public static void init() {
        try {
            SteamAPI_ManualDispatch_Init.invokeExact();
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ManualDispatch_Init call failed", t);
        }
    }

    public static void runFrame(int hSteamPipe) {
        try {
            SteamAPI_ManualDispatch_RunFrame.invokeExact(hSteamPipe);
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ManualDispatch_RunFrame call failed", t);
        }
    }

    public static boolean getNextCallback(int hSteamPipe, MemorySegment callbackMsg) {
        try {
            return (boolean) SteamAPI_ManualDispatch_GetNextCallback.invokeExact(hSteamPipe, callbackMsg);
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ManualDispatch_GetNextCallback call failed", t);
        }
    }

    public static void freeLastCallback(int hSteamPipe) {
        try {
            SteamAPI_ManualDispatch_FreeLastCallback.invokeExact(hSteamPipe);
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ManualDispatch_FreeLastCallback call failed", t);
        }
    }

    public static MemorySegment getAPICallResult(Arena arena, int hSteamPipe, long asyncCall, int callbackId, int cubParam) {
        MemorySegment resultBuf = arena.allocate(cubParam);
        MemorySegment failedFlag = arena.allocate(ValueLayout.JAVA_BOOLEAN);

        boolean ok;

        try {
            ok = (boolean) SteamManualDispatchNative.SteamAPI_ManualDispatch_GetAPICallResult.invokeExact(
                    hSteamPipe,
                    asyncCall,
                    resultBuf,
                    cubParam,
                    callbackId,
                    failedFlag);
        } catch (Throwable t) {
            throw new RuntimeException("SteamAPI_ManualDispatch_GetAPICallResult call failed", t);
        }

        if (!ok || failedFlag.get(ValueLayout.JAVA_BOOLEAN, 0)) {
            return null;
        }

        return resultBuf;
    }

}
