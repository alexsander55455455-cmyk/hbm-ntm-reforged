package com.hbmspace.dim;

import net.minecraft.world.biome.Biome;

/**
 * Thrown when an NTM Space biome is registered on a biome ID that is already in use.
 * Ported from X5687 1.7.10 {@code com.hbm.dim.BiomeCollisionException}.
 *
 * 1.12.2 differences from 1.7.10:
 * - {@code BiomeGenBase} → {@code Biome}
 * - {@code biomeID} field → {@code Biome.getIdForBiome(biome)}
 * - {@code biomeName} field → {@code biome.getBiomeName()}
 * - {@code getBiomeClass()} → {@code getClass()}
 */
public class BiomeCollisionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private static String EXCEPTION_MESSAGE
		= "Biome ID conflict!"
		+ "\n\n!!!!!  ALERT ALERT - READ ME - I AM THE REASON YOUR GAME IS CRASHING  !!!!!"
		+ "\n\n!!!!!             FOLLOW THE INSTRUCTIONS BELOW TO RESOLVE            !!!!!"
		+ "\n\nAttempted to register NTM Space biome to an ID which is already in use by:"
		+ "\nBiome ID: %d"
		+ "\nBiome name: %s"
		+ "\nBiome class: %s"
		+ "\nPlease modify hbm.cfg to fix this error. Note that the maximum biome ID is 255, if you run out you MUST install EndlessIDs!";

	public BiomeCollisionException(Biome conflictsWith) {
		super(String.format(EXCEPTION_MESSAGE, Biome.getIdForBiome(conflictsWith), conflictsWith.getBiomeName(), conflictsWith.getClass().getName()));
	}

	public BiomeCollisionException(String message) {
		super(message);
	}

}
