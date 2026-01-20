package net.aakagure.blocks.enums;

import net.minecraft.util.StringIdentifiable;

public enum FirePlaceEnum implements StringIdentifiable {
    CENTER("center"),
    LEFT("left"),
    LEFTUP("leftup"),
    UP("up"),
    RIGHTUP("rightup"),
    RIGHT("right");

    private final String name;

    FirePlaceEnum(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
