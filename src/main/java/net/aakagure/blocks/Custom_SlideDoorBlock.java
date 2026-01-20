package net.aakagure.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.enums.DoorHinge;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class Custom_SlideDoorBlock extends DoorBlock {
    // Shapes originais exatos (portas deslizantes finas)
    protected static final VoxelShape SOUTH_SLIDE_SHAPE = Block.createCuboidShape(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
    protected static final VoxelShape NORTH_SLIDE_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 13.0D, 3.0D, 16.0D, 16.0D);
    protected static final VoxelShape WEST_SLIDE_SHAPE = Block.createCuboidShape(13.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape EAST_SLIDE_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 3.0D);

    // CONSTRUTOR CORRIGIDO: Aceita BlockSetType + Settings para bater com o ModBlocks
    public Custom_SlideDoorBlock(BlockSetType type, Settings settings) {
        super(type, settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(FACING);

        // flag = se está FECHADO (!OPEN)
        boolean closed = !state.get(OPEN);
        // flag1 = se a dobradiça é DIREITA
        boolean rightHinge = state.get(HINGE) == DoorHinge.RIGHT;

        switch (direction) {
            case SOUTH:
                // Se fechado: usa forma vanilla (SOUTH_SHAPE). Se aberto: desliza.
                return closed ? SOUTH_SHAPE : (rightHinge ? SOUTH_SLIDE_SHAPE : EAST_SLIDE_SHAPE);
            case WEST:
                return closed ? WEST_SHAPE : (rightHinge ? WEST_SLIDE_SHAPE : SOUTH_SLIDE_SHAPE);
            case NORTH:
                return closed ? NORTH_SHAPE : (rightHinge ? NORTH_SLIDE_SHAPE : WEST_SLIDE_SHAPE);
            default: // EAST
                return closed ? EAST_SHAPE : (rightHinge ? EAST_SLIDE_SHAPE : NORTH_SLIDE_SHAPE);
        }
    }
}