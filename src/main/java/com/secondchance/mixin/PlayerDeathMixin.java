package com.secondchance.mixin;

import com.secondchance.PlayerReviveManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class PlayerDeathMixin {

	@Inject(method = "onDeath", at = @At("HEAD"), cancellable = true)
	private void onPlayerDeath(CallbackInfo ci) {
		ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
		
		if (PlayerReviveManager.canRevive(player)) {
			PlayerReviveManager.revivePlayer(player);
			ci.cancel();
		}
	}
}
