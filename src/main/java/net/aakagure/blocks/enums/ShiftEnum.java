package net.aakagure.blocks.enums;

import net.minecraft.util.StringIdentifiable;

public enum ShiftEnum implements StringIdentifiable {
    NONE("none"),
    NORTH("north"),
    SOUTH("south"),
    EAST("east"),
    WEST("west"),
    UP("up"),
    DOWN("down");

    private final String name;

    ShiftEnum(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}