package de.johni0702.minecraft.fadeinchunks.ext;

import me.jellysquid.mods.sodium.client.render.chunk.multidraw.ChunkDrawParamsVector;

public interface ChunkDrawParamsVectorExt {
    /**
     * Pushes the fadeInProgress parameter onto the buffer.
     * This must be called after {@link ChunkDrawParamsVector#pushChunkDrawParams}!
     */
    void pushChunkDrawParamFadeIn(float progress);

    static ChunkDrawParamsVectorExt ext(ChunkDrawParamsVector self) {
        return (ChunkDrawParamsVectorExt) self;
    }
}
