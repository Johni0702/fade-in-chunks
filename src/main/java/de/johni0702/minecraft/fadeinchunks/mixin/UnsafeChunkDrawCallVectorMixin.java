package de.johni0702.minecraft.fadeinchunks.mixin;

import de.johni0702.minecraft.fadeinchunks.ext.ChunkDrawParamsVectorExt;
import me.jellysquid.mods.sodium.client.render.chunk.multidraw.ChunkDrawParamsVector;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import sun.misc.Unsafe;

@Mixin(value = ChunkDrawParamsVector.UnsafeChunkDrawCallVector.class, remap = false)
public abstract class UnsafeChunkDrawCallVectorMixin extends ChunkDrawParamsVector implements ChunkDrawParamsVectorExt {
    @Shadow @Final private static Unsafe UNSAFE;

    @Shadow private long writePointer;

    protected UnsafeChunkDrawCallVectorMixin(int capacity) {
        super(capacity);
    }

    @Override
    public void pushChunkDrawParamFadeIn(float progress) {
        UNSAFE.putFloat(this.writePointer - 4, progress);
    }
}
