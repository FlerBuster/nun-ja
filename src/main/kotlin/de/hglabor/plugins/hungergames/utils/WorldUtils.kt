package de.hglabor.plugins.hungergames.utils

import de.hglabor.plugins.hungergames.utils.WorldUtils.setBlockInstantly
import net.axay.kspigot.extensions.broadcast
import net.axay.kspigot.runnables.KSpigotRunnable
import net.axay.kspigot.runnables.sync
import net.axay.kspigot.runnables.task
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.material.MaterialData
import kotlin.math.max

object WorldUtils {
    private val defaultQueue = BlockQueue()

    fun setBlockInstantly(block: Block, material: Material, data: Byte = 0) {
        block.setTypeIdAndData(material.id, data, true)
    }

    fun setBlockInstantly(location: Location, material: Material, data: Byte = 0) {
        setBlockInstantly(location.block, material, data)
    }

    fun setBlock(block: Block, material: Material, data: Byte = 0, queue: BlockQueue = defaultQueue) {
        setBlock(block.location, material, data, queue)
    }

    fun setBlock(location: Location, material: Material, data: Byte, queue: BlockQueue = defaultQueue) {
        val queuedBlock = queue.queuedBlocks[location]
        val shouldntPlace =  (queuedBlock != null && queuedBlock.first == material && queuedBlock.second == data) ||
                (queuedBlock?.first == Material.AIR && material == Material.AIR)

        if (!shouldntPlace) {
            queue.queuedBlocks[location] = material to data
        }
        queue.startPlacingBlocksInQueue()
    }

    fun buildFilledCylinder(location: Location, radius: Int, height: Int, materialData: MaterialData, blockQueue: BlockQueue = defaultQueue) {
        val height = height-1
        val radiusSquared = (radius * radius).toDouble()
        for (x in -radius until radius) {
            for (z in -radius until radius) {
                if (x * x + z * z <= radiusSquared) {
                    for (y in 0..height) {
                        val loc = location.clone().block.getRelative(x, y, z).location
                        setBlock(loc, materialData.itemType, materialData.data, blockQueue)
                    }
                }
            }
        }
    }

    fun buildHollowCylinder(location: Location, radius: Int, height: Int, materialData: MaterialData, blockQueue: BlockQueue = defaultQueue) {
        val height = max(1, height)
        val radiusSquared = (radius * radius).toDouble()
        for (x in -radius until radius) {
            for (z in -radius until radius) {
                if (x * x + z * z > radiusSquared) {
                    for (y in 0..height) {
                        val loc = location.clone().block.getRelative(x, y, z).location
                        setBlock(loc, materialData.itemType, materialData.data, blockQueue)
                    }
                }
            }
        }
    }
}

class BlockQueue {
    var queueTask: KSpigotRunnable? = null
    val queuedBlocks: MutableMap<Location, Pair<Material, Byte>> = mutableMapOf()

    fun startPlacingBlocksInQueue() {
        if (queueTask != null) return

        queueTask = task(false, 2, 2) {
            if (queuedBlocks.isEmpty()) {
                it.cancel()
                queueTask = null
                return@task
            }

            queuedBlocks.toList().take(75).forEach { (loc, pair) ->
                val (material, data) = pair
                sync {
                    val block = loc.block
                    if (block.type != material || block.data != data)
                        setBlockInstantly(loc, material, data)
                    queuedBlocks -= loc
                }
            }
        }
    }
}