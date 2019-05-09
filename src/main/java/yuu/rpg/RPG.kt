package yuu.rpg

import org.bukkit.plugin.java.JavaPlugin

class RPG : JavaPlugin() {

    override fun onEnable() {
        // Plugin startup logic
        Test(this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }



}
