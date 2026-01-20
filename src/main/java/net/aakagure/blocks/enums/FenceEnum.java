package net.aakagure.blocks.enums;

import net.minecraft.util.StringIdentifiable;

public enum FenceEnum implements StringIdentifiable {
    NONE("none"),
    SLANT_N("slant_n"),
    SLANT_S("slant_s"),
    SLANT_W("slant_w"),
    SLANT_E("slant_e");

    private final String name;

    FenceEnum(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}