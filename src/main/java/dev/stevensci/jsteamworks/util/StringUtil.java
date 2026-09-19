package dev.stevensci.jsteamworks.util;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

public final class StringUtil {

    private StringUtil() {
        throw new UnsupportedOperationException();
    }

    public static MemorySegment allocateString(Arena arena, String value) {
        if (value == null) return MemorySegment.NULL;
        return arena.allocateFrom(value);
    }

    public static String readString(MemorySegment ptr) {
        if (ptr == null || ptr.equals(MemorySegment.NULL)) return null;
        return ptr.reinterpret(Long.MAX_VALUE).getString(0);
    }

    public static String readFixedString(MemorySegment seg, long offset, int maxLen) {
        return seg.asSlice(offset, maxLen).getString(0);
    }

}
