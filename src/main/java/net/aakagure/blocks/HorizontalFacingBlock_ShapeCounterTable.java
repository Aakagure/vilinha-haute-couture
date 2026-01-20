package net.aakagure.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class HorizontalFacingBlock_ShapeCounterTable extends HorizontalFacingBlock {
    // Balcões geralmente ocupam o bloco todo menos uma pequena folga, ou são como lajes superiores grossas.
    // Usando uma forma de topo cheia para exemplo.
    private static final VoxelShape SHAPE = Block.createCuboidShape(0, 0, 0, 16, 16, 16);

    public HorizontalFacingBlock_ShapeCounterTable(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE; // Pode refinar se quiser que não encoste nas paredes
    }
}