package net.aakagure.blocks.enums;

import net.minecraft.util.StringIdentifiable;

public enum VerticalEnum implements StringIdentifiable {
    SINGLE("single"),
    UPPER("upper"),
    MIDDLE("middle"),
    LOWER("lower");

    private final String name;

    VerticalEnum(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}