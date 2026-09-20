package dev.stevensci.jsteamworks.steam.manualdispatch;

import dev.stevensci.jsteamworks.util.SteamNative;

import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;

final class SteamManualDispatchNative {

    private SteamManualDispatchNative() {
        throw new UnsupportedOperationException();
    }

    static final MethodHandle SteamAPI_ManualDispatch_Init = SteamNative.bindVoid(
            "SteamAPI_ManualDispatch_Init");

    static final MethodHandle SteamAPI_ManualDispatch_RunFrame = SteamNative.bindVoid(
            "SteamAPI_ManualDispatch_RunFrame",
            ValueLayout.JAVA_INT);

    static final MethodHandle SteamAPI_ManualDispatch_GetNextCallback = SteamNative.bind(
            "SteamAPI_ManualDispatch_GetNextCallback",
            ValueLayout.JAVA_BOOLEAN,
            ValueLayout.JAVA_INT,
            ValueLayout.ADDRESS);

    static final MethodHandle SteamAPI_ManualDispatch_FreeLastCallback = SteamNative.bindVoid(
            "SteamAPI_ManualDispatch_FreeLastCallback",
            ValueLayout.JAVA_INT);

    static final MethodHandle SteamAPI_ManualDispatch_GetAPICallResult = SteamNative.bind(
            "SteamAPI_ManualDispatch_GetAPICallResult",
            ValueLayout.JAVA_BOOLEAN,
            ValueLayout.JAVA_INT,
            ValueLayout.JAVA_LONG,
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_INT,
            ValueLayout.JAVA_INT,
            ValueLayout.ADDRESS);

}
