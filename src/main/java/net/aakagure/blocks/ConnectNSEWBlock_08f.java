package net.aakagure.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class ConnectNSEWBlock_08f extends ConnectNSEWBlock {
    public ConnectNSEWBlock_08f(Settings settings) { super(settings); }

    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 0.8F; // Levemente mais claro que o normal
    }
}