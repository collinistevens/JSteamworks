package dev.stevensci.jsteamworks.util;

import java.lang.foreign.MemoryLayout;
import java.lang.foreign.SequenceLayout;
import java.lang.foreign.StructLayout;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.List;

public class Struct {

    private final List<MemoryLayout> members = new ArrayList<>();
    private long offset = 0;
    private long maxAlign = 1;
    private boolean automaticPadding = true;

    private Struct() {}

    public static Struct builder() {
        return new Struct();
    }

    public Struct automaticPadding(boolean enabled) {
        this.automaticPadding = enabled;
        return this;
    }

    public Struct field(ValueLayout layout, String name) {
        return addMember(layout, name, layout.byteSize());
    }

    public Struct nested(StructLayout nestedLayout, String name) {
        return addMember(nestedLayout, name, nestedLayout.byteSize());
    }

    public Struct arrayField(ValueLayout elementLayout, String name, long count) {
        SequenceLayout seq = MemoryLayout.sequenceLayout(count, elementLayout);
        return addMember(seq, name, elementLayout.byteSize() * count);
    }

    private Struct addMember(MemoryLayout layout, String name, long size) {
        if (this.automaticPadding) {
            alignTo(layout.byteAlignment());
            this.maxAlign = Math.max(this.maxAlign, layout.byteAlignment());
        }
        this.members.add(layout.withName(name));
        this.offset += size;
        return this;
    }

    public Struct padding(long bytes) {
        this.members.add(MemoryLayout.paddingLayout(bytes));
        this.offset += bytes;
        return this;
    }

    public Struct byteField(String name) {
        return field(ValueLayout.JAVA_BYTE, name);
    }

    public Struct shortField(String name) {
        return field(ValueLayout.JAVA_SHORT, name);
    }

    public Struct intField(String name) {
        return field(ValueLayout.JAVA_INT, name);
    }

    public Struct longField(String name) {
        return field(ValueLayout.JAVA_LONG, name);
    }

    public Struct floatField(String name) {
        return field(ValueLayout.JAVA_FLOAT, name);
    }

    public Struct doubleField(String name) {
        return field(ValueLayout.JAVA_DOUBLE, name);
    }

    public Struct booleanField(String name) {
        return field(ValueLayout.JAVA_BOOLEAN, name);
    }

    public Struct addressField(String name) {
        return field(ValueLayout.ADDRESS, name);
    }

    public StructLayout build(String structName) {
        if (this.automaticPadding) {
            long misalignment = this.offset % this.maxAlign;
            if (misalignment != 0) {
                this.members.add(MemoryLayout.paddingLayout(this.maxAlign - misalignment));
            }
        }
        return MemoryLayout.structLayout(this.members.toArray(MemoryLayout[]::new)).withName(structName);
    }

    private void alignTo(long align) {
        long misalignment = this.offset % align;
        if (misalignment != 0) {
            long pad = align - misalignment;
            this.members.add(MemoryLayout.paddingLayout(pad));
            this.offset += pad;
        }
    }

    public static VarHandle varHandle(StructLayout layout, String fieldName) {
        return layout.varHandle(MemoryLayout.PathElement.groupElement(fieldName));
    }

    public static long byteOffset(StructLayout layout, String fieldName) {
        return layout.byteOffset(MemoryLayout.PathElement.groupElement(fieldName));
    }

}
