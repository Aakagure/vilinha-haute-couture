package net.aakagure.blocks;


import net.aakagure.blocks.util.ShapeUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class Block_ActionLit_ShapeSlim extends Block_ActionLit {
    public Block_ActionLit_ShapeSlim(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return ShapeUtil.SHAPE_SLIM;
    }
}
