package net.aakagure.blocks;

import net.aakagure.blocks.enums.BathtubEnum;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Custom_Bathtub extends Block {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final EnumProperty<BathtubEnum> TYPE = EnumProperty.of("type", BathtubEnum.class);

    public Custom_Bathtub(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, net.minecraft.util.math.Direction.NORTH).with(TYPE, BathtubEnum.EMPTY));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, TYPE);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient) return ActionResult.SUCCESS;

        // Cicla entre os estados: EMPTY -> WATER -> ROSE -> EMPTY
        BathtubEnum current = state.get(TYPE);
        BathtubEnum next;
        switch (current) {
            case EMPTY: next = BathtubEnum.WATER; break;
            case WATER: next = BathtubEnum.ROSE; break;
            default: next = BathtubEnum.EMPTY; break;
        }

        world.setBlockState(pos, state.with(TYPE, next), 3);
        // Opcional: Tocar som de água aqui
        return ActionResult.SUCCESS;
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }
}