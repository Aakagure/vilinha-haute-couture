package net.aakagure.blocks.enums;

import net.minecraft.util.StringIdentifiable;

public enum Position3Enum implements StringIdentifiable {
    LEFT("left"),
    SINGLE("single"),
    RIGHT("right");

    private final String name;

    Position3Enum(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}