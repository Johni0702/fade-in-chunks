package de.johni0702.minecraft.fadeinchunks.mixin;

import de.johni0702.minecraft.fadeinchunks.ext.ChunkProgramOneshotExt;
import me.jellysquid.mods.sodium.client.render.chunk.oneshot.ChunkProgramOneshot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.nio.FloatBuffer;

@Mixin(value = ChunkProgramOneshot.class, remap = false)
public abstract class ChunkProgramOneshotMixin implements ChunkProgramOneshotExt {
    @Shadow @Final private FloatBuffer uModelOffsetBuffer;

    @Override
    public void setFadeIn(float progress) {
        this.uModelOffsetBuffer.put(3, progress);
    }
}
