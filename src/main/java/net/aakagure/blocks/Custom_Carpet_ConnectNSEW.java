package net.aakagure.blocks;

import net.aakagure.blocks.util.ShapeUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

// Reutiliza a lógica do ConnectNSEWBlock que criamos antes
public class Custom_Carpet_ConnectNSEW extends ConnectNSEWBlock {

    public Custom_Carpet_ConnectNSEW(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return ShapeUtil.SHAPE_CARPET;
    }
}