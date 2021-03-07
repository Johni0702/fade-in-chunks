package de.johni0702.minecraft.fadeinchunks.mixin;

import me.jellysquid.mods.sodium.client.render.chunk.ChunkCameraContext;
import me.jellysquid.mods.sodium.client.render.chunk.ChunkGraphicsState;
import me.jellysquid.mods.sodium.client.render.chunk.backends.gl43.GL43ChunkRenderBackend;
import me.jellysquid.mods.sodium.client.render.chunk.backends.gl43.LCBGraphicsState;
import me.jellysquid.mods.sodium.client.render.chunk.lists.ChunkRenderListIterator;
import me.jellysquid.mods.sodium.client.render.chunk.multidraw.ChunkDrawParamsVector;
import me.jellysquid.mods.sodium.client.render.chunk.multidraw.ChunkProgramMultiDraw;
import me.jellysquid.mods.sodium.client.render.chunk.passes.BlockRenderPass;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static de.johni0702.minecraft.fadeinchunks.ext.ChunkDrawParamsVectorExt.ext;
import static de.johni0702.minecraft.fadeinchunks.ext.ChunkGraphicsStateExt.ext;

@Mixin(value = GL43ChunkRenderBackend.class, remap = false)
public abstract class GL43ChunkRenderBackendMixin extends ChunkRenderShaderBackendMixin<ChunkProgramMultiDraw> {
    @Shadow @Final private ChunkDrawParamsVector uniformBufferBuilder;

    @Inject(method = "setupDrawBatches", at = @At(value = "INVOKE", target = "Lme/jellysquid/mods/sodium/client/render/chunk/multidraw/ChunkDrawParamsVector;pushChunkDrawParams(FFF)V", shift = At.Shift.AFTER))
    private void pushChunkDrawParamFadeInProgress(ChunkRenderListIterator<LCBGraphicsState> it, ChunkCameraContext camera, CallbackInfo ci) {
        float progress = ext(it.getGraphicsState()).getFadeInProgress(this.currentTime);
        ext(this.uniformBufferBuilder).pushChunkDrawParamFadeIn(progress);
    }

    @ModifyArg(method = "upload", at = @At(value = "INVOKE", target = "Lme/jellysquid/mods/sodium/client/render/chunk/ChunkRenderContainer;setGraphicsState(Lme/jellysquid/mods/sodium/client/render/chunk/passes/BlockRenderPass;Lme/jellysquid/mods/sodium/client/render/chunk/ChunkGraphicsState;)V", ordinal = 0))
    private ChunkGraphicsState setLoadTime(BlockRenderPass pass, ChunkGraphicsState newState) {
        ChunkGraphicsState oldState = ext(newState).getContainer().getGraphicsState(pass);
        ext(newState).setLoadTime(oldState == null ? this.currentTime : ext(oldState).getLoadTime());
        return newState;
    }
}
