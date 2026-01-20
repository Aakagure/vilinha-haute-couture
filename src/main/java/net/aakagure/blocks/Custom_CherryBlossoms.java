package net.aakagure.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class Custom_CherryBlossoms extends ConnectNSEWBlock_08f {
    public Custom_CherryBlossoms(Settings settings) {
        super(settings);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);

        // Lógica de partículas (1 chance em 10 por tick, por exemplo)
        if (random.nextInt(10) == 0) {
            BlockPos belowPos = pos.down();
            BlockState belowState = world.getBlockState(belowPos);

            // Só solta partículas se tiver espaço embaixo
            if (!Block.isFaceFullSquare(belowState.getCollisionShape(world, belowPos), Direction.UP)) {
                double x = pos.getX() + random.nextDouble();
                double y = pos.getY() - 0.05D;
                double z = pos.getZ() + random.nextDouble();

                // Usa a partícula de cerejeira nativa do Minecraft 1.21
                world.addParticle(ParticleTypes.CHERRY_LEAVES, x, y, z, 0.0D, 0.0D, 0.0D);
            }
        }
    }
}