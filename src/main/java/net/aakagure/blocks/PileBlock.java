package net.aakagure.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;

import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import java.util.List;

public class PileBlock extends Block {
    public static final int MAX_LAYERS = 8;
    public static final IntProperty LAYERS = Properties.LAYERS; // 1 a 8

    protected static final VoxelShape[] LAYERS_TO_SHAPE = new VoxelShape[]{
            VoxelShapes.empty(), // 0
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), // 1
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), // 2
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), // 3
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), // 4
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D),// 5
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),// 6
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),// 7
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D) // 8
    };

    public PileBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(LAYERS, 1));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LAYERS);
    }

    // method_9530
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return LAYERS_TO_SHAPE[state.get(LAYERS)];
    }

    // method_9575: Controla a sombra (Ambient Occlusion)
    // Se for 8 (bloco cheio), comporta-se como bloco sólido (0.2F), senão é "transparente" à luz (1.0F)
    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return state.get(LAYERS) == 8 ? 0.2F : 1.0F;
    }

    // method_9558: Lógica de suporte (só pode colocar se tiver bloco sólido embaixo)
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos belowPos = pos.down();
        BlockState belowState = world.getBlockState(belowPos);

        // Pode colocar se a face de cima do bloco de baixo for sólida OU se o bloco de baixo for um PileBlock cheio (8 camadas)
        return Block.isFaceFullSquare(belowState.getCollisionShape(world, belowPos), Direction.UP)
                || (belowState.isOf(this) && belowState.get(LAYERS) == 8);
    }

    // method_9605: Lógica de Colocação
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos pos = ctx.getBlockPos();
        BlockState state = ctx.getWorld().getBlockState(pos);

        // Se já existe um bloco Pile aqui, adiciona +1 camada
        if (state.isOf(this)) {
            int i = state.get(LAYERS);
            return state.with(LAYERS, Math.min(8, i + 1));
        }
        return super.getPlacementState(ctx);
    }

    // method_9616: Lógica de Substituição (Adicionar camadas)
    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        int i = state.get(LAYERS);

        // Se o jogador está segurando este item E não está cheio (i < 8)
        if (context.getStack().isOf(this.asItem()) && i < 8) {
            // Se o jogador está mirando especificamente neste bloco (canReplaceExisting)
            if (context.canReplaceExisting()) {
                // Só permite adicionar se clicar na face de CIMA (UP)
                return context.getSide() == Direction.UP;
            } else {
                return true;
            }
        }

        // Lógica do original: Se for apenas 1 camada, pode ser substituído por outro bloco?
        // (Similar à neve no vanilla)
        return i == 1;
    }

    // method_9568: Tooltip
    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        tooltip.add(Text.translatable("tooltip.vilinha_haute_couture.pile").formatted(Formatting.GRAY));
        super.appendTooltip(stack, context, tooltip, options);
    }
}