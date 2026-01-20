package net.aakagure.blocks.enums;

import net.minecraft.util.StringIdentifiable;

public enum DDirectionEnum implements StringIdentifiable {
    NORTH("north"),
    EAST("east"),
    SOUTH("south"),
    WEST("west"),
    UP_NORTH("up_north"),
    UP_EAST("up_east"),
    UP_SOUTH("up_south"),
    UP_WEST("up_west");

    private final String name;

    DDirectionEnum(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }

    public DDirectionEnum getOpposite() {
        return switch (this) {
            case NORTH -> SOUTH;
            case EAST -> WEST;
            case SOUTH -> NORTH;
            case WEST -> EAST;
            case UP_NORTH -> UP_SOUTH;
            case UP_EAST -> UP_WEST;
            case UP_SOUTH -> UP_NORTH;
            case UP_WEST -> UP_EAST;
        };
    }
}