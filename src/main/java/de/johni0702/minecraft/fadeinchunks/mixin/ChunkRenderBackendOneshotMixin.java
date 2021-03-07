package de.johni0702.minecraft.fadeinchunks.mixin;

import me.jellysquid.mods.sodium.client.render.chunk.ChunkCameraContext;
import me.jellysquid.mods.sodium.client.render.chunk.ChunkGraphicsState;
import me.jellysquid.mods.sodium.client.render.chunk.oneshot.ChunkOneshotGraphicsState;
import me.jellysquid.mods.sodium.client.render.chunk.oneshot.ChunkProgramOneshot;
import me.jellysquid.mods.sodium.client.render.chunk.oneshot.ChunkRenderBackendOneshot;
import me.jellysquid.mods.sodium.client.render.chunk.passes.BlockRenderPass;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static de.johni0702.minecraft.fadeinchunks.ext.ChunkGraphicsStateExt.ext;
import static de.johni0702.minecraft.fadeinchunks.ext.ChunkProgramOneshotExt.ext;

@Mixin(value = ChunkRenderBackendOneshot.class, remap = false)
public abstract class ChunkRenderBackendOneshotMixin<T extends ChunkOneshotGraphicsState> extends ChunkRenderShaderBackendMixin<ChunkProgramOneshot> {

    @Inject(method = "prepareDrawBatch", at = @At("HEAD"))
    private void pushChunkDrawParamFadeInProgress(ChunkCameraContext camera, T state, CallbackInfo ci) {
        float progress = ext(state).getFadeInProgress(this.currentTime);
        ext(this.activeProgram).setFadeIn(progress);
    }

    @ModifyArg(method = "upload", at = @At(value = "INVOKE", target = "Lme/jellysquid/mods/sodium/client/render/chunk/ChunkRenderContainer;setGraphicsState(Lme/jellysquid/mods/sodium/client/render/chunk/passes/BlockRenderPass;Lme/jellysquid/mods/sodium/client/render/chunk/ChunkGraphicsState;)V"))
    private ChunkGraphicsState setLoadTime(BlockRenderPass pass, ChunkGraphicsState newState) {
        if (newState != null) {
            ChunkGraphicsState oldState = ext(newState).getContainer().getGraphicsState(pass);
            ext(newState).setLoadTime(oldState == null ? this.currentTime : ext(oldState).getLoadTime());
        }
        return newState;
    }
}
