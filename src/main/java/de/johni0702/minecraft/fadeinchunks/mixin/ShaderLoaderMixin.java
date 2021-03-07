package de.johni0702.minecraft.fadeinchunks.mixin;

import me.jellysquid.mods.sodium.client.gl.shader.ShaderLoader;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ShaderLoader.class, remap = false)
public abstract class ShaderLoaderMixin {
    @Shadow
    private static String getShaderPath(Identifier name) {
        return null;
    }

    @Inject(method = "getShaderSource", at = @At("RETURN"), cancellable = true)
    private static void modifyShaderForFadeInEffect(String path, CallbackInfoReturnable<String> cir) {
        boolean isVertexShader = path.equals(getShaderPath(new Identifier("sodium", "chunk_gl20.v.glsl")));
        boolean isFragmentShader = path.equals(getShaderPath(new Identifier("sodium", "chunk_gl20.f.glsl")));
        if (!isVertexShader && !isFragmentShader) {
            return;
        }

        StringBuilder source = new StringBuilder(cir.getReturnValue());
        prepend(source, "varying", "varying float v_FadeInProgress;");
        if (isVertexShader) {
            prepend(source, "v_Color = ", "v_FadeInProgress = d_ModelOffset.w;");
        }
        if (isFragmentShader) {
            // 0 is max fog, 1 is no fog
            replace(source, "(getFogFactor(),", "(min(v_FadeInProgress, getFogFactor()),");
        }
        cir.setReturnValue(source.toString());
    }

    private static void replace(StringBuilder buffer, String search, String str) {
        int idx = buffer.indexOf(search);
        buffer.replace(idx, idx + search.length(), str);
    }

    private static void prepend(StringBuilder buffer, String search, String str) {
        replace(buffer, search, str + search);
    }
}
