package de.johni0702.minecraft.fadeinchunks.mixin;

import de.johni0702.minecraft.fadeinchunks.ext.ChunkDrawParamsVectorExt;
import me.jellysquid.mods.sodium.client.render.chunk.multidraw.ChunkDrawParamsVector;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = ChunkDrawParamsVector.NioChunkDrawCallVector.class, remap = false)
public abstract class NioChunkDrawCallVectorMixin extends ChunkDrawParamsVector implements ChunkDrawParamsVectorExt {
    @Shadow private int writeOffset;

    protected NioChunkDrawCallVectorMixin(int capacity) {
        super(capacity);
    }

    @Override
    public void pushChunkDrawParamFadeIn(float progress) {
        this.buffer.putFloat(this.writeOffset - 4, progress);
    }
}
