package net.aakagure.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType; // Importante para 1.21
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import java.util.List;

// Mudei de "extends FacingBlock" para "extends Block" para evitar conflitos de propriedade
public class FacingBlock_Reverse_ShapePlate extends Block {

    // AQUI ESTAVA O ERRO: Usamos Properties.FACING (6 direções) em vez de HORIZONTAL_FACING
    public static final DirectionProperty FACING = Properties.FACING;

    // Formas (VoxelShapes)
    private static final VoxelShape SHAPE_NORTH = Block.createCuboidShape(0.0D, 0.0D, 12.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape SHAPE_SOUTH = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 4.0D);
    private static final VoxelShape SHAPE_EAST  = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 4.0D, 16.0D, 16.0D);
    private static final VoxelShape SHAPE_WEST  = Block.createCuboidShape(12.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape SHAPE_UP    = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);   // No chão
    private static final VoxelShape SHAPE_DOWN  = Block.createCuboidShape(0.0D, 12.0D, 0.0D, 16.0D, 16.0D, 16.0D); // No teto

    public FacingBlock_Reverse_ShapePlate(Settings settings) {
        super(settings);
        // Define o estado padrão inicial
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    // Registra a propriedade no bloco
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    // Define a Hitbox baseada na direção
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(FACING);
        switch (direction) {
            case NORTH: return SHAPE_NORTH;
            case SOUTH: return SHAPE_SOUTH;
            case WEST:  return SHAPE_WEST;
            case EAST:  return SHAPE_EAST;
            case UP:    return SHAPE_UP;
            case DOWN:  return SHAPE_DOWN;
            default:    return SHAPE_NORTH;
        }
    }

    // Lógica de colocação (Placement Logic)
    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction facing = ctx.getSide(); // A face do bloco onde você clicou
        var player = ctx.getPlayer();
        boolean isSneaking = player != null && player.isSneaking();
        Direction playerHorizontalFacing = ctx.getHorizontalPlayerFacing();

        // Se clicou nas laterais (Norte, Sul, Leste, Oeste)
        if (facing.getAxis().isHorizontal()) {
            if (isSneaking) {
                // Se estiver agachado, tenta colocar no teto ou chão dependendo da altura do clique
                boolean isTopHalf = ctx.getHitPos().y - (double)ctx.getBlockPos().getY() > 0.5D;
                // AGORA VAI FUNCIONAR: A propriedade FACING aceita UP e DOWN
                return this.getDefaultState().with(FACING, isTopHalf ? Direction.UP : Direction.DOWN);
            } else {
                // Se não estiver agachado, coloca virado para o jogador (horizontal)
                return this.getDefaultState().with(FACING, playerHorizontalFacing.getOpposite());
            }
        }
        // Se clicou no chão ou teto
        else {
            if (isSneaking) {
                return this.getDefaultState().with(FACING, playerHorizontalFacing.getOpposite());
            } else {
                return this.getDefaultState().with(FACING, facing);
            }
        }
    }

    // Adiciona o texto cinza (Tooltip)
    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        tooltip.add(Text.translatable("tooltip.vilinha_haute_couture.reverse_plate").formatted(Formatting.GRAY));
        super.appendTooltip(stack, context, tooltip, options);
    }
}