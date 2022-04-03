package de.hglabor.plugins.hungergames.game.phase.phases

import de.hglabor.plugins.hungergames.game.GameManager
import de.hglabor.plugins.hungergames.game.phase.GamePhase
import de.hglabor.plugins.hungergames.player.PlayerList
import net.axay.kspigot.extensions.broadcast
import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

object LobbyPhase : GamePhase(120, InvincibilityPhase) {
    override fun onStart() {
        broadcast("Lobby!")
    }

    override fun getTimeString(): String = "Starting: ${maxDuration - GameManager.elapsedTime.get()}"


    override fun incrementElapsedTime() {
        if (PlayerList.allPlayers.size >= 2) GameManager.elapsedTime.getAndIncrement()
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        PlayerList.getPlayer(event.player)
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        PlayerList.remove(event.player.uniqueId)
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun onDamage(event: EntityDamageEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun onDropItem(event: PlayerDropItemEvent) {
        event.isCancelled = true
    }
}