package net.aakagure.blocks;

import net.aakagure.blocks.util.HelperUtil;
import net.aakagure.blocks.util.ShapeUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class Custom_Pergola extends ConnectHorizontalBlock {
    public static final BooleanProperty ON = BooleanProperty.of("on");

    public Custom_Pergola(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(ON, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(ON);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        // Usa a forma HalfTop (metade superior do bloco)
        return ShapeUtil.SHAPE_HALFTOP;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        // Chama a lógica de conexão horizontal do pai e define ON como false
        return super.getPlacementState(ctx).with(ON, false);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        // Alterna o estado ON com clique
        return HelperUtil.toggleProperty(state, world, pos, player, ON);
    }
}