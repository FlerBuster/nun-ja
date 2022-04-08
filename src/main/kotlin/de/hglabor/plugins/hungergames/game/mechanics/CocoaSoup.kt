package de.hglabor.plugins.hungergames.game.mechanics

import net.axay.kspigot.extensions.server
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapelessRecipe

object CocoaSoup {
    fun register() {
        val mushroom_soup = ItemStack(Material.MUSHROOM_SOUP, 1)
        val MUSHROOM_SOUP = ShapelessRecipe(mushroom_soup)
        MUSHROOM_SOUP.addIngredient(Material.BOWL)
        MUSHROOM_SOUP.addIngredient(Material.INK_SACK, 3)
        server.addRecipe(MUSHROOM_SOUP)
    }
}