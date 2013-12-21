package com.github.abujaki21.jeevesCraft;

import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class PlayerDeath implements Listener{
	public PlayerDeath (JeevesCraft jeeves){

		//Register as a listener
		jeeves.getServer().getPluginManager().registerEvents(this, jeeves);
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event){
		if(event.getEntityType() == EntityType.PLAYER){
			Player dead = event.getEntity().getPlayer();
			//Pry the skull from their head
			ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.PLAYER.ordinal());
			SkullMeta skullmeta = (SkullMeta) skull.getItemMeta();
			skullmeta.setOwner(dead.getName());
			skull.setItemMeta(skullmeta);
			//Drop it
			event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), skull);
		}
	}
}