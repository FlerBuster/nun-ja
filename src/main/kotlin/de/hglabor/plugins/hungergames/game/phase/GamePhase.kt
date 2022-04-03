package de.hglabor.plugins.hungergames.game.phase

import de.hglabor.plugins.hungergames.game.GameManager
import org.bukkit.event.Listener

abstract class GamePhase(val maxDuration: Long, val nextPhase: GamePhase?) : Listener {
    var tickCount: Int = 0

    open fun onStart() {}

    open fun tick(tickCount: Int) {}

    abstract fun getTimeString(): String

    fun start() {
        onStart()
    }

    open fun incrementElapsedTime() {
        GameManager.elapsedTime.incrementAndGet()
    }
}