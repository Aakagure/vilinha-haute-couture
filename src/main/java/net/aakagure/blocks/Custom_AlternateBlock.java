package net.aakagure.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;

public class Custom_AlternateBlock extends Block {
    public static final BooleanProperty UP = BooleanProperty.of("up");

    public Custom_AlternateBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(UP, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(UP);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        // Lógica de alternância (Checkerboard 3D ou apenas altura)
        // O original parece usar pos.getX() + pos.getY() + pos.getZ()
        var pos = ctx.getBlockPos();
        boolean isUp = (pos.getX() + pos.getY() + pos.getZ()) % 2 != 0;
        return this.getDefaultState().with(UP, isUp);
    }
}