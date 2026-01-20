package net.aakagure.blocks.enums;

import net.minecraft.util.StringIdentifiable;

public enum BedFrameEnum implements StringIdentifiable {
    CENTER("center"),
    FRONT("front"),
    BACK("back"),
    FRONTR("frontr"),
    BACKR("backr");

    private final String name;

    BedFrameEnum(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
