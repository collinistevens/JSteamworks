package dev.stevensci.jsteamworks.util;

import dev.stevensci.jsteamworks.util.loader.DefaultSteamLibraryLoader;
import dev.stevensci.jsteamworks.util.loader.SteamLibraryLoader;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import java.nio.file.Path;
import java.util.Objects;

public final class SteamNative {

    public static final Arena ARENA = Arena.ofShared();
    public static final Linker LINKER = Linker.nativeLinker();

    private static final Object LOCK = new Object();
    private static volatile SteamLibraryLoader libraryLoader = new DefaultSteamLibraryLoader();
    private static volatile SymbolLookup steamLib;

    private SteamNative() {
        throw new UnsupportedOperationException();
    }

    public static void setLibraryLoader(SteamLibraryLoader loader) {
        synchronized (LOCK) {
            if (steamLib != null) {
                throw new IllegalStateException(
                        "Steam library is already loaded. Call setLibraryLoader() before any native class is used.");
            }
            libraryLoader = Objects.requireNonNull(loader);
        }
    }

    private static SymbolLookup steamLib() {
        SymbolLookup lookup = steamLib;
        if (lookup == null) {
            synchronized (LOCK) {
                lookup = steamLib;
                if (lookup == null) {
                    Path path = libraryLoader.locate("steam_api");
                    lookup = SymbolLookup.libraryLookup(path, ARENA);
                    steamLib = lookup;
                }
            }
        }
        return lookup;
    }

    public static MethodHandle bind(String symbol, MemoryLayout returnLayout, MemoryLayout... argLayouts) {
        return LINKER.downcallHandle(
                steamLib().find(symbol).orElseThrow(() -> new IllegalStateException("Missing Steam symbol: " + symbol)),
                FunctionDescriptor.of(returnLayout, argLayouts)
        );
    }

    public static MethodHandle bindVoid(String symbol, MemoryLayout... argLayouts) {
        return LINKER.downcallHandle(
                steamLib().find(symbol).orElseThrow(() -> new IllegalStateException("Missing Steam symbol: " + symbol)),
                FunctionDescriptor.ofVoid(argLayouts)
        );
    }

}
