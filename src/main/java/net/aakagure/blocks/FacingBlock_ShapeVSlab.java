package net.aakagure.blocks;

import net.aakagure.blocks.util.ShapeUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class FacingBlock_ShapeVSlab extends FacingBlock {
    public FacingBlock_ShapeVSlab(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        // Mapeia a direção para o ShapeUtil
        switch (state.get(FACING)) {
            case NORTH: return ShapeUtil.SHAPE_VSLAB_NORTH;
            case SOUTH: return ShapeUtil.SHAPE_VSLAB_SOUTH;
            case WEST: return ShapeUtil.SHAPE_VSLAB_WEST;
            case EAST: return ShapeUtil.SHAPE_VSLAB_EAST;
            case UP: return ShapeUtil.SHAPE_HALFTOP; // Adaptação: Se virado para cima, usa metade superior
            case DOWN: return ShapeUtil.SHAPE_HALFBOTTOM; // Se virado para baixo, usa metade inferior
            default: return ShapeUtil.SHAPE_VSLAB_NORTH;
        }
    }
}