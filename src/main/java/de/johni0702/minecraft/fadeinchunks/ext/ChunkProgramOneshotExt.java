package de.johni0702.minecraft.fadeinchunks.ext;

import me.jellysquid.mods.sodium.client.render.chunk.oneshot.ChunkProgramOneshot;

public interface ChunkProgramOneshotExt {
    /**
     * Sets the fadeInProgress uniform.
     * This must be called before {@link ChunkProgramOneshot#setModelOffset(float, float, float)}!
     */
    void setFadeIn(float progress);

    static ChunkProgramOneshotExt ext(ChunkProgramOneshot self) {
        return (ChunkProgramOneshotExt) self;
    }
}
