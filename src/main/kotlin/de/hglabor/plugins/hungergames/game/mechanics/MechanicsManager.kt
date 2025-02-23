package de.hglabor.plugins.hungergames.game.mechanics

import de.hglabor.plugins.hungergames.game.mechanics.implementation.*
import de.hglabor.plugins.hungergames.game.mechanics.implementation.arena.ArenaMechanic

object MechanicsManager {
    val mechanics = listOf(
        ArenaMechanic,
        BuildHeightLimit,
        DamageNerf,
        OreNerf,
        LapisInEnchanter,
        BlocksToInv,
        RemoveFishingRod,
        NoInvDropOnClose,
        MoreDurability,
        MushroomCowNerf,
        HungerNerf
    )
}