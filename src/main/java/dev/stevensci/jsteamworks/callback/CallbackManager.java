package dev.stevensci.jsteamworks.callback;

import dev.stevensci.jsteamworks.steam.manualdispatch.SteamManualDispatch;
import dev.stevensci.jsteamworks.steam.manualdispatch.types.CallbackMsg;
import dev.stevensci.jsteamworks.steam.manualdispatch.types.SteamAPICallCompleted;
import dev.stevensci.jsteamworks.steam.steamapi.SteamAPI;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public final class CallbackManager {

    private static final Map<Integer, Consumer<MemorySegment>> GLOBAL_HANDLERS = new ConcurrentHashMap<>();
    private static final Map<Long, Consumer<MemorySegment>> CALL_RESULT_HANDLERS = new ConcurrentHashMap<>();
    private static final MemorySegment CALLBACK_MSG = Arena.global().allocate(CallbackMsg.LAYOUT);

    private static int hSteamPipe = -1;

    private CallbackManager() {
        throw new UnsupportedOperationException();
    }

    public static void init() {
        hSteamPipe = SteamAPI.getHSteamPipe();
        SteamManualDispatch.init();
    }

    public static void subscribe(long apiCall, Consumer<MemorySegment> handler) {
        CALL_RESULT_HANDLERS.put(apiCall, handler);
    }

    public static <T> void subscribe(long apiCall, SteamCallback<T> callback, Consumer<T> handler) {
        CALL_RESULT_HANDLERS.put(apiCall, decoding(callback, handler));
    }

    public static void subscribe(int callbackId, Consumer<MemorySegment> handler) {
        GLOBAL_HANDLERS.put(callbackId, handler);
    }

    public static <T> void subscribe(SteamCallback<T> callback, Consumer<T> handler) {
        GLOBAL_HANDLERS.put(callback.callbackId(), decoding(callback, handler));
    }

    private static <T> Consumer<MemorySegment> decoding(SteamCallback<T> callback, Consumer<T> handler) {
        return seg -> {
            T decoded;
            try {
                decoded = callback.decoder().apply(seg);
            } catch (Throwable t) {
                throw new RuntimeException("Failed to decode callback " + callback.callbackId(), t);
            }
            handler.accept(decoded);
        };
    }

    public static void runCallbacks() {
        try {
            SteamManualDispatch.runFrame(hSteamPipe);

            while (SteamManualDispatch.getNextCallback(hSteamPipe, CALLBACK_MSG)) {
                try {
                    int callbackId = (int) CallbackMsg.VH_CALLBACK.get(CALLBACK_MSG, 0);
                    int cubParam = (int) CallbackMsg.VH_CUB_PARAM.get(CALLBACK_MSG, 0);
                    MemorySegment param = ((MemorySegment) CallbackMsg.VH_PUB_PARAM.get(CALLBACK_MSG, 0)).reinterpret(cubParam);

                    System.out.println("Callback: " + callbackId);

                    if (callbackId == SteamAPICallCompleted.CALLBACK_ID) {
                        dispatchCallResult(param);
                    } else {
                        Consumer<MemorySegment> handler = GLOBAL_HANDLERS.get(callbackId);
                        if (handler != null) {
                            handler.accept(param);
                        }
                    }
                } finally {
                    SteamManualDispatch.freeLastCallback(hSteamPipe);
                }
            }
        } catch (Throwable t) {
            throw new RuntimeException("Callback dispatch failed", t);
        }
    }

    private static void dispatchCallResult(MemorySegment envelope) {
        long asyncCall = (long) SteamAPICallCompleted.VH_ASYNC_CALL.get(envelope, 0);
        int callbackId = (int) SteamAPICallCompleted.VH_CALLBACK.get(envelope, 0);
        int cubParam = (int) SteamAPICallCompleted.VH_CUB_PARAM.get(envelope, 0);

        Consumer<MemorySegment> handler = CALL_RESULT_HANDLERS.remove(asyncCall);
        if (handler == null) return;

        try (Arena scratch = Arena.ofConfined()) {
            MemorySegment result = SteamManualDispatch.getAPICallResult(scratch, hSteamPipe, asyncCall, callbackId, cubParam);
            if (result != null) handler.accept(result);
        }
    }

}
