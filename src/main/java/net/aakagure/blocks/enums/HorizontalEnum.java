package net.aakagure.blocks.enums;

import net.minecraft.util.StringIdentifiable;

public enum HorizontalEnum implements StringIdentifiable {
    SINGLE("single"),
    LEFT("left"),
    MIDDLE("middle"),
    RIGHT("right");

    private final String name;

    HorizontalEnum(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}