package dev.stevensci.jsteamworks.util.loader;

import java.nio.file.Path;

public interface SteamLibraryLoader {
    Path locate(String libraryName);
}
