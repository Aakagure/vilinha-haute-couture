package net.aakagure.blocks.util;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HelperUtil {

    /**
     * Alterna uma propriedade booleana de um bloco (ex: abrir/fechar, ligar/desligar).
     * @param state O estado atual do bloco.
     * @param world O mundo onde o bloco está.
     * @param pos A posição do bloco.
     * @param player O jogador que interagiu (pode ser null se for automação).
     * @param property A propriedade booleana a ser alternada (ex: Properties.OPEN).
     * @return O resultado da ação (SUCCESS).
     */
    public static ActionResult toggleProperty(BlockState state, World world, BlockPos pos, PlayerEntity player, BooleanProperty property) {
        if (!world.isClient) {
            // Lógica do Servidor
            boolean isCurrentlyOn = state.get(property);
            BlockState newState = state.with(property, !isCurrentlyOn);

            // Atualiza o bloco (Flag 3 = Atualizar bloco + Redesenhar no cliente)
            world.setBlockState(pos, newState, 3);

            // Define o pitch: mais grave se desligar (0.5), mais agudo se ligar (0.6)
            float pitch = isCurrentlyOn ? 0.5F : 0.6F;

            // Toca o som de clique
            world.playSound(null, pos, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.3F, pitch);

            return ActionResult.SUCCESS;
        } else {
            // Lógica do Cliente (apenas confirma que a ação aconteceu para animar a mão)
            return ActionResult.SUCCESS;
        }
    }
}