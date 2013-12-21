package com.github.abujaki21.jeevesCraft;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public final class GiantSpawner implements Listener{
	private Server server;
	private int maxGiants, spawnN, spawnD;
	private String spawnMessage;

	public GiantSpawner(JeevesCraft jeeves) {

		//Read the configuration file into memory
		maxGiants = jeeves.getConfig().getInt("Spawn.Giant.Max");
		spawnN = jeeves.getConfig().getInt("Spawn.Giant.SpawnNumerator");
		spawnD = jeeves.getConfig().getInt("Spawn.Giant.SpawnDenominator");
		spawnMessage = jeeves.getConfig().getString("Spawn.Giant.Message");
		if(spawnD == 0){
			//For smart-asses who want to destroy the universe, we substitute the default value
			//Even though technically for any values D,N s.t. D <= N, the spawn rate is 1 giant 
			//per zombie up to the cap, I don't want to encourage dividing by zero.
			spawnD = 1000;
		}
		
		server = jeeves.getServer();
		//Register the class as a listener
		jeeves.getServer().getPluginManager().registerEvents(this, jeeves);
	}

	@EventHandler
	public void onMobSpawn(CreatureSpawnEvent event){
		if(event.getEntityType() == EntityType.GIANT){
			server.broadcastMessage(ChatColor.RED + spawnMessage);
		}
		//If a zombie spawns (Brains!)
		else if(event.getEntityType() == EntityType.ZOMBIE){
			//And there is a slot for another giant to spawn
			if(maxGiants > 0){
				//Roll for a chance to spawn a giant
				//Chance to spawn a giant is spawnN / spawnD
				int sChance = (int)(Math.random() * spawnD);
				if(sChance <= spawnN){
					//Abort the zombie spawn
					event.setCancelled(true);
					//Spawn the giant where the Zombie would have been
					event.getEntity().getWorld().spawnEntity(event.getLocation(), EntityType.GIANT);
					//Reduce number of open slots
					maxGiants--;
				}
			}
		}
	}

	@EventHandler
	public void onGiantDeath(EntityDeathEvent event){
		if(event.getEntityType() == EntityType.GIANT){
			//Add slightly better drops

			//Important numbers.
			short flesh = (short)((Math.random()*56) + 8); //8-64 Rotten flesh
			short gold = (short)((Math.random()*6)+2); //2-8 Gold ingots
			short coin = (short)((Math.random())+1); //1-2 Diamonds; Level 1-2 Potion of Strength
			Location loc = event.getEntity().getLocation(); //Where the giant was slain

			//Drop the items
			event.getEntity().getWorld().dropItemNaturally(loc, new ItemStack(Material.ROTTEN_FLESH, flesh));
			event.getEntity().getWorld().dropItemNaturally(loc, new ItemStack(Material.GOLD_INGOT, gold));
			event.getEntity().getWorld().dropItemNaturally(loc, new ItemStack(Material.DIAMOND, coin));

			//Potion of MUSCLES!
			Potion str = new Potion(PotionType.STRENGTH);
			str.setLevel(coin);
			ItemStack pot = str.toItemStack((short)((Math.random()*2)+1)); //1-3 Potions
			event.getEntity().getWorld().dropItemNaturally(loc, pot);

			//Add more EXP because 5 EXP for a giant is balls
			event.setDroppedExp(30);

			//Open up a giant slot 
			maxGiants++;
		}
	}
}
