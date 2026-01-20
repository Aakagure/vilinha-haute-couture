package net.aakagure.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SetDoubleBlock_HorizontalFacing extends SetDoubleBlock {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    public SetDoubleBlock_HorizontalFacing(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING);
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        // Verifica se há espaço em cima (igual ao SetDoubleBlock base)
        if (blockPos.getY() < ctx.getWorld().getTopY() - 1 && ctx.getWorld().getBlockState(blockPos.up()).canReplace(ctx)) {
            // Retorna o estado com a direção oposta ao jogador (padrão de facing)
            return super.getPlacementState(ctx).with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
        }
        return null;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        // Define a parte de cima com a mesma rotação da parte de baixo
        // O super.onPlaced do SetDoubleBlock coloca a parte de cima, mas sem rotação se não sobrescrevermos
        // Aqui garantimos a rotação correta:
        if (state.get(HALF) == DoubleBlockHalf.LOWER) {
            world.setBlockState(pos.up(), state.with(HALF, DoubleBlockHalf.UPPER).with(FACING, state.get(FACING)), 3);
        }
    }
}