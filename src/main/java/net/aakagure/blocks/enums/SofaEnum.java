package net.aakagure.blocks.enums;

import net.minecraft.util.StringIdentifiable;

public enum SofaEnum implements StringIdentifiable {
    NONE("none"),
    SLIPPER("slipper"),
    SINGLE_A("single_a"),
    LEFT_A("left_a"),
    BOTH_A("both_a"),
    RIGHT_A("right_a"),
    SINGLE_B("single_b"),
    LEFT_B("left_b"),
    BOTH_B("both_b"),
    RIGHT_B("right_b");

    private final String name;

    SofaEnum(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}