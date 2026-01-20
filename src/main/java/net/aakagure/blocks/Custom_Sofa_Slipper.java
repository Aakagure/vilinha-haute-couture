package net.aakagure.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.StairShape;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

// Estendemos StairsBlock para aproveitar a lógica complexa de formas (Inner, Outer, Straight)
// Precisamos passar um 'baseBlockState' para o construtor do StairsBlock no Fabric 1.21
public class Custom_Sofa_Slipper extends StairsBlock {

    // Construtor atualizado para 1.21
    public Custom_Sofa_Slipper(BlockState baseBlockState, Settings settings) {
        super(baseBlockState, settings);
    }

    // Se preferir não depender de um bloco base no construtor, podemos passar o próprio defaultState
    // Mas o StairsBlock exige um estado no super.
    // Hack comum: passar Blocks.OAK_PLANKS.getDefaultState() já que a lógica visual é nossa.
    public Custom_Sofa_Slipper(Settings settings) {
        super(net.minecraft.block.Blocks.WHITE_WOOL.getDefaultState(), settings);
    }

    // O StairsBlock já lida com FACING, HALF, SHAPE e WATERLOGGED.
    // Talvez precisemos travar o HALF para BOTTOM se o sofá não puder ficar de cabeça para baixo.

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        // Se quiser formas customizadas, sobrescreva aqui.
        // Caso contrário, ele usará a forma de escada padrão (que funciona bem para sofás de canto).
        return super.getOutlineShape(state, world, pos, context);
    }
}