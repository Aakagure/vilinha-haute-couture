package net.aakagure.blocks.util;


import net.minecraft.block.Block;
import net.minecraft.util.shape.VoxelShape;

public class ShapeUtil {
    // createCuboidShape aceita doubles (x1, y1, z1, x2, y2, z2)
    // Valores vão de 0.0 a 16.0

    public static final VoxelShape SHAPE_SMALL = Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 8.0D, 13.0D);
    public static final VoxelShape SHAPE_SLIM = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
    public static final VoxelShape SHAPE_CARPET = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
    public static final VoxelShape SHAPE_HALFTOP = Block.createCuboidShape(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    public static final VoxelShape SHAPE_HALFBOTTOM = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);

    // Slabs Verticais (Vertical Slabs)
    public static final VoxelShape SHAPE_VSLAB_NORTH = Block.createCuboidShape(0.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D);
    public static final VoxelShape SHAPE_VSLAB_SOUTH = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D);
    public static final VoxelShape SHAPE_VSLAB_WEST = Block.createCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    public static final VoxelShape SHAPE_VSLAB_EAST = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D);
}