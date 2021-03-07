package de.johni0702.minecraft.fadeinchunks.ext;

import me.jellysquid.mods.sodium.client.render.chunk.ChunkGraphicsState;
import me.jellysquid.mods.sodium.client.render.chunk.ChunkRenderContainer;

public interface ChunkGraphicsStateExt {
    ChunkRenderContainer<?> getContainer();

    float getLoadTime();
    void setLoadTime(float time);

    default float getFadeInProgress(float currentTime) {
        return (currentTime - getLoadTime()) * 5;
        /* Custom fade progress for icon creation. Seed: -8255308814763093104
        ChunkGraphicsState state = (ChunkGraphicsState) this;
        int dx = Math.abs(state.getX() - 56) / 16;
        int dz = Math.abs(state.getZ() - 24) / 16;
        int d = Math.max(dx, dz);
        return d == 0 ? 1 : d == 1 ? 0.8f : 0;
        */
    }

    static ChunkGraphicsStateExt ext(ChunkGraphicsState self) {
        return (ChunkGraphicsStateExt) self;
    }
}
