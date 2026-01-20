package net.aakagure.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;


public class AxisBlock_ShapeSlim extends AxisBlock {
    // Usando as formas definidas no ShapeUtil ou recriando se forem específicas deste bloco
    // O original usa 4.0F a 12.0F (que é o mesmo que ShapeUtil.SHAPE_SLIM ajustado?)
    // Vamos definir conforme o original:
    protected static final VoxelShape Y_AXIS_AABB = createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    protected static final VoxelShape Z_AXIS_AABB = createCuboidShape(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 16.0D);
    protected static final VoxelShape X_AXIS_AABB = createCuboidShape(0.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D);

    public AxisBlock_ShapeSlim(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(AXIS)) {
            case X: return X_AXIS_AABB;
            case Z: return Z_AXIS_AABB;
            default: return Y_AXIS_AABB;
        }
    }
}