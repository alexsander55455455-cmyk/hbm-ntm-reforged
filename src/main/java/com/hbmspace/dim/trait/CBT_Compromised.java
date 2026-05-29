package com.hbmspace.dim.trait;

/**
 * Marker trait indicating a celestial body has been compromised.
 * Ported from X5687 1.7.10 {@code com.hbm.dim.trait.CBT_Compromised}.
 *
 * Note: {@link CelestialBodyTrait} also contains an inner class
 * {@code CBT_COMPROMISED} with a static instance {@code COMP},
 * registered under the key {@code "infected"} in the trait map.
 * This standalone class exists for X5687 parity; both forms extend
 * {@link CelestialBodyTrait} and serve as a compromised-body marker.
 */
public class CBT_Compromised extends CelestialBodyTrait {


}
