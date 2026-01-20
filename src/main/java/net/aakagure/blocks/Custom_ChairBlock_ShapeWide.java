package net.aakagure.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class Custom_ChairBlock_ShapeWide extends Custom_ChairBlock {
    // Cadeira mais larga (ocupa quase todo o bloco)
    private static final VoxelShape SEAT_WIDE = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D);

    public Custom_ChairBlock_ShapeWide(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        // Simplificação: usa apenas a base larga para colisão, visualmente o modelo json definirá o encosto
        return SEAT_WIDE;
    }
}