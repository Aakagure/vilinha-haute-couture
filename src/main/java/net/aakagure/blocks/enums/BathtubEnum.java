package net.aakagure.blocks.enums;

import net.minecraft.util.StringIdentifiable;

public enum BathtubEnum implements StringIdentifiable {
    EMPTY("empty"),
    WATER("water"),
    ROSE("rose");

    private final String name;

    BathtubEnum(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}