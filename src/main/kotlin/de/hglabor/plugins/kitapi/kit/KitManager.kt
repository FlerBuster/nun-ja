package de.hglabor.plugins.kitapi.kit

import de.hglabor.plugins.kitapi.implementation.*

object KitManager {
    val kits = setOf(
        Anchor,
        Automatic,
        Blink,
        //Counter,
        Digger,
        Domino,
        Gladiator,
        Lumberjack,
        Magma,
        Ninja,
        None,
        Perfect,
        Phantom,
        Reaper,
        Redstoner,
        //Relaxo,
        Revive,
        Snail,
        Spider,
        Sponge,
        Squid,
        Stomper,
        Ultimato,
        Viking,
        ZickZack,
    ).map { it.value }
}
