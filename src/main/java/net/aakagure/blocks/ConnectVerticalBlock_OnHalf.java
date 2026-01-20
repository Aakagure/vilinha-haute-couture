package net.aakagure.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.WorldAccess;

public class ConnectVerticalBlock_OnHalf extends ConnectVerticalBlock {
    public static final BooleanProperty ON = BooleanProperty.of("on");

    public ConnectVerticalBlock_OnHalf(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(ON, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(ON);
    }

    @Override
    protected BlockState calculateState(BlockState state, WorldAccess world, BlockPos pos) {
        BlockState verticalState = super.calculateState(state, world, pos);

        BlockState below = world.getBlockState(pos.down());
        VoxelShape shape = below.getOutlineShape(world, pos.down());
        boolean onHalf = shape.getMax(Direction.Axis.Y) == 0.5D;

        return verticalState.with(ON, onHalf);
    }
}