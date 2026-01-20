package net.aakagure.blocks;

import net.aakagure.blocks.util.ShapeUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class SetDoubleBlock_HorizontalFacing_ShapeVSlab extends SetDoubleBlock_HorizontalFacing {

    public SetDoubleBlock_HorizontalFacing_ShapeVSlab(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        // Se for a metade de cima (UPPER), usa cubo cheio (ou forma definida para topo se houver).
        // No original, geralmente blocos VSlab altos (como portas finas) usam a forma fina nas duas metades.
        // O código decompilado sugere o uso de SHAPE_VSLAB baseado no FACING.
        // Se a lógica original for VSlab em ambas as metades:
        switch (state.get(FACING)) {
            case SOUTH: return ShapeUtil.SHAPE_VSLAB_SOUTH;
            case WEST: return ShapeUtil.SHAPE_VSLAB_WEST;
            case EAST: return ShapeUtil.SHAPE_VSLAB_EAST;
            default: return ShapeUtil.SHAPE_VSLAB_NORTH;
        }
    }
}