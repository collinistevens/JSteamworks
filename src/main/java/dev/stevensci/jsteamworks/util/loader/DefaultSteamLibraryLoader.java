package dev.stevensci.jsteamworks.util.loader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

public final class DefaultSteamLibraryLoader implements SteamLibraryLoader {

    @Override
    public Path locate(String libraryName) {
        String fileName = resolveFileName(libraryName);
        Path path = Path.of(System.getProperty("user.dir"), "lib", fileName);

        if (Files.notExists(path)) {
            throw new IllegalStateException("Native Steam library not found at " + path.toAbsolutePath());
        }
        return path;
    }

    private static String resolveFileName(String libraryName) {
        String os = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        if (os.contains("windows")) return libraryName + "64.dll";
        if (os.contains("mac") || os.contains("darwin")) return "lib" + libraryName + ".dylib";
        if (os.contains("nux") || os.contains("nix") || os.contains("aix")) return "lib" + libraryName + ".so";
        throw new IllegalStateException("Unsupported OS for native Steam library: " + os);
    }

}
