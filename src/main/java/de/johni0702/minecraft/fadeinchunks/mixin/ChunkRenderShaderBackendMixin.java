package de.johni0702.minecraft.fadeinchunks.mixin;

import me.jellysquid.mods.sodium.client.render.chunk.shader.ChunkProgram;
import me.jellysquid.mods.sodium.client.render.chunk.shader.ChunkRenderShaderBackend;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ChunkRenderShaderBackend.class, remap = false)
public abstract class ChunkRenderShaderBackendMixin<P extends ChunkProgram> {

    protected float currentTime;

    @Inject(method = "begin", at = @At("HEAD"))
    private void updateTime(CallbackInfo ci) {
        this.currentTime = Util.getMeasuringTimeMs() / 1000f;
    }

    @Shadow protected P activeProgram;
}
