package net.aakagure.blocks.enums;

import net.minecraft.util.StringIdentifiable;

public enum UDDirectionEnum implements StringIdentifiable {
    NORTH("north"),
    EAST("east"),
    SOUTH("south"),
    WEST("west"),
    UP_NORTH("up_north"),
    UP_EAST("up_east"),
    UP_SOUTH("up_south"),
    UP_WEST("up_west"),
    DOWN_NORTH("down_north"),
    DOWN_EAST("down_east"),
    DOWN_SOUTH("down_south"),
    DOWN_WEST("down_west");

    private final String name;

    UDDirectionEnum(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }

    public UDDirectionEnum getOpposite() {
        return switch (this) {
            case NORTH -> SOUTH;
            case EAST -> WEST;
            case SOUTH -> NORTH;
            case WEST -> EAST;
            case UP_NORTH -> UP_SOUTH;
            case UP_EAST -> UP_WEST;
            case UP_SOUTH -> UP_NORTH;
            case UP_WEST -> UP_EAST;
            case DOWN_NORTH -> DOWN_SOUTH;
            case DOWN_EAST -> DOWN_WEST;
            case DOWN_SOUTH -> DOWN_NORTH;
            case DOWN_WEST -> DOWN_EAST;
        };
    }
}
