package net.aakagure.blocks.enums;

import net.minecraft.util.StringIdentifiable;

public enum PositionEnum implements StringIdentifiable {
    TOP("top"),
    BOTTOM("bottom");

    private final String name;

    PositionEnum(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}