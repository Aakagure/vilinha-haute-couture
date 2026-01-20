package net.aakagure.blocks;

import net.aakagure.blocks.enums.FirePlaceEnum;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class Custom_FirePlace extends Block {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final EnumProperty<FirePlaceEnum> PART = EnumProperty.of("part", FirePlaceEnum.class);

    public Custom_FirePlace(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(FACING, Direction.NORTH)
                .with(PART, FirePlaceEnum.CENTER));
    }

    // Calcula a dureza do bloco (original method_9575)
    @Override
    public float getHardness() {
        return 1.0F;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PART, FACING);
    }

    // Verifica se pode colocar (checa o espaço ao redor)
    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getHorizontalPlayerFacing().getOpposite();
        BlockPos pos = ctx.getBlockPos();
        World world = ctx.getWorld();

        // Passamos o 'ctx' inteiro para o canPlaceAt
        return this.canPlaceAt(ctx, world, pos, direction)
                ? this.getDefaultState().with(FACING, direction)
                : null;
    }

    // Método atualizado para receber o Contexto
    private boolean canPlaceAt(ItemPlacementContext ctx, WorldView world, BlockPos pos, Direction facing) {
        Direction left = facing.rotateYClockwise();
        Direction right = facing.rotateYCounterclockwise();

        // Ao chamar canReplace, passamos o 'ctx' em vez de null
        return pos.getY() < world.getTopY() - 1
                && world.getBlockState(pos).canReplace(ctx)
                && world.getBlockState(pos.offset(left)).canReplace(ctx)
                && world.getBlockState(pos.offset(right)).canReplace(ctx)
                && world.getBlockState(pos.up()).canReplace(ctx)
                && world.getBlockState(pos.up().offset(left)).canReplace(ctx)
                && world.getBlockState(pos.up().offset(right)).canReplace(ctx);
    }

    // Método auxiliar para colocar blocos
    private void setBlockAt(World world, BlockPos pos, Direction facing, FirePlaceEnum part) {
        if (part != FirePlaceEnum.CENTER) {
            // Define o bloco com a parte específica e a mesma direção
            world.setBlockState(pos, this.getDefaultState().with(PART, part).with(FACING, facing), 3);
        }
    }

    // Quando o bloco é colocado pelo jogador (method_9567)
    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (placer == null) return;

        Direction facing = state.get(FACING);
        Direction left = facing.rotateYClockwise();
        Direction right = facing.rotateYCounterclockwise();

        // Constrói a estrutura Multi-Bloco
        this.setBlockAt(world, pos.up(), facing, FirePlaceEnum.UP);
        this.setBlockAt(world, pos.offset(left), facing, FirePlaceEnum.LEFT);
        this.setBlockAt(world, pos.up().offset(left), facing, FirePlaceEnum.LEFTUP);
        this.setBlockAt(world, pos.up().offset(right), facing, FirePlaceEnum.RIGHTUP);
        this.setBlockAt(world, pos.offset(right), facing, FirePlaceEnum.RIGHT);

        // O original define o próprio bloco como CENTER no final, mas o onPlaced já garante isso se não mexermos.
        // A linha original world.method_8652(pos, class_2246.field_10124.method_9564(), 3)
        // parece definir AR se falhar? Não, na verdade isso deve ser um bug de descompilação ou lógica específica.
        // Vamos manter o estado que foi colocado.
    }

    // Quando o bloco é quebrado pelo jogador (method_9576)
    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient) {
            if (player.isCreative()) {
                onBreakInCreative(world, pos, state, player);
            } else {
                // Dropa os itens e quebra normalmente
                dropStacks(state, world, pos, null, player, player.getMainHandStack());
            }
        }
        return super.onBreak(world, pos, state, player);
    }

    // Lógica extra de quebra (method_9556)
    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        super.onBroken(world, pos, state);
        // Se quebrou no survival/geral, garante que limpa os restos
        if (world instanceof World) {
            destroyBlocks((World) world, getCenterPos(pos, state.get(FACING), state.get(PART)), state.get(FACING));
        }
    }

    // Lógica para Criativo (evita dropar itens fantasmas e limpa tudo)
    protected static void onBreakInCreative(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        Direction facing = state.get(FACING);
        FirePlaceEnum part = state.get(PART);
        BlockPos centerPos = getCenterPos(pos, facing, part);

        // Destrói toda a estrutura
        destroyBlocks(world, centerPos, facing);
    }

    // Encontra o bloco central (CENTER) baseado na parte atual
    private static BlockPos getCenterPos(BlockPos pos, Direction facing, FirePlaceEnum part) {
        Direction left = facing.rotateYClockwise();
        Direction right = facing.rotateYCounterclockwise();

        switch (part) {
            case LEFT: return pos.offset(right); // Se sou esquerda, centro tá na direita
            case LEFTUP: return pos.down().offset(right);
            case UP: return pos.down();
            case RIGHTUP: return pos.down().offset(left);
            case RIGHT: return pos.offset(left); // Se sou direita, centro tá na esquerda
            default: return pos;
        }
    }

    // Destrói os 6 blocos da lareira
    private static void destroyBlocks(World world, BlockPos centerPos, Direction facing) {
        Direction left = facing.rotateYClockwise();
        Direction right = facing.rotateYCounterclockwise();

        // Define todos como AR (Blocks.AIR)
        // Usa flag false para não dropar itens nem tocar som de novo
        replaceWithAir(world, centerPos);
        replaceWithAir(world, centerPos.offset(left));
        replaceWithAir(world, centerPos.offset(right));
        replaceWithAir(world, centerPos.up());
        replaceWithAir(world, centerPos.up().offset(left));
        replaceWithAir(world, centerPos.up().offset(right));
    }

    private static void replaceWithAir(World world, BlockPos pos) {
        if (world.getBlockState(pos).getBlock() instanceof Custom_FirePlace) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 35); // 35 = flag para não atualizar vizinhos e evitar loop
        }
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }
}