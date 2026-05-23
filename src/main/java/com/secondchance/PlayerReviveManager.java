package com.secondchance;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerReviveManager {
	private static final Map<UUID, PlayerReviveData> playerData = new HashMap<>();
	private static final int MAX_REVIVES = 10;
	private static final long COOLDOWN_TIME = 5 * 60 * 1000; // 5 minut
	
	public static boolean canRevive(ServerPlayerEntity player) {
		UUID uuid = player.getUuid();
		PlayerReviveData data = playerData.getOrDefault(uuid, new PlayerReviveData());
		
		// Agar CD da bo'lsa
		if (data.isOnCooldown()) {
			player.sendMessage(Text.of("§c5 minut CD kutib turing!"), false);
			return false;
		}
		
		// Agar 10 marta tugasa
		if (data.revivesUsed >= MAX_REVIVES) {
			player.sendMessage(Text.of("§c10 marta tugadi! 5 minut CD..."), false);
			data.startCooldown();
			playerData.put(uuid, data);
			return false;
		}
		
		return true;
	}
	
	public static void revivePlayer(ServerPlayerEntity player) {
		UUID uuid = player.getUuid();
		PlayerReviveData data = playerData.getOrDefault(uuid, new PlayerReviveData());
		
		// Teleport orqaga 2 soniya
		Vec3d lastPos = data.lastPosition;
		if (lastPos != null) {
			player.teleport(player.getServerWorld(), lastPos.x, lastPos.y, lastPos.z, player.getYaw(), player.getPitch());
		}
		
		// Health qaytarish
		player.setHealth(20);
		
		// Counter oshirish
		data.revivesUsed++;
		data.lastPosition = player.getPos();
		
		player.sendMessage(Text.of("§a✓ Revive! (" + data.revivesUsed + "/10)"), false);
		
		// 10 martadan keyin CD
		if (data.revivesUsed >= MAX_REVIVES) {
			data.startCooldown();
		}
		
		playerData.put(uuid, data);
	}
	
	public static class PlayerReviveData {
		public int revivesUsed = 0;
		public long cooldownEnd = 0;
		public Vec3d lastPosition = null;
		
		public boolean isOnCooldown() {
			return System.currentTimeMillis() < cooldownEnd;
		}
		
		public void startCooldown() {
			cooldownEnd = System.currentTimeMillis() + COOLDOWN_TIME;
		}
	}
}
