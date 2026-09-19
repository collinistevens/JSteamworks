package dev.stevensci.jsteamworks.util;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class EnumLookup {

    private EnumLookup() {
        throw new UnsupportedOperationException();
    }

    public static <K, E extends Enum<E>> Map<K, E> mapByKey(Class<E> enumClass, Function<E, K> keyFn) {
        return Arrays.stream(enumClass.getEnumConstants()).collect(Collectors.toMap(keyFn, Function.identity()));
    }

}
