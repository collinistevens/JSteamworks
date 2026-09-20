package dev.stevensci.jsteamworks.callback;

import java.lang.foreign.MemorySegment;
import java.util.function.Function;

public record SteamCallback<T>(int callbackId, Function<MemorySegment, T> decoder) {

    public static <T> SteamCallback<T> of(int callbackId, Function<MemorySegment, T> decoder) {
        return new SteamCallback<>(callbackId, decoder);
    }

}
