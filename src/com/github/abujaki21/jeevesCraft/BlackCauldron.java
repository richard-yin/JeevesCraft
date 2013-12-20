package com.github.abujaki21.jeevesCraft;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlackCauldron implements Listener {
	private static HashMap<Location, Brew> cauldronMap;

	public BlackCauldron(JeevesCraft jeeves){
		cauldronMap = new HashMap<Location, Brew>(); 
		jeeves.getServer().getPluginManager().registerEvents(this, jeeves);
	}

	public void onCauldronPlace(BlockPlaceEvent e){
		if(e.getBlock().getType() == Material.CAULDRON){
			buildCauldron(e.getBlock().getLocation());

		}
	}

	@EventHandler
	public void onCauldronInteract(PlayerInteractEvent e){
		if((e.getClickedBlock().getType() == Material.CAULDRON) &&
				(e.getAction() == Action.LEFT_CLICK_BLOCK)){
			//If a glass bottle is used
			if(e.getItem().getType() == Material.GLASS_BOTTLE){
				//Remove potion from the cauldron
				
			} else {
				//Save location for effects later
				Location loc = e.getClickedBlock().getLocation();
				//Add the ingredient to the brew
				if(cauldronMap.get(loc).addIngredient(e.getItem().getType())){
					//Effects
					e.getPlayer().playSound(loc, Sound.LAVA, 1F, 1F);
					//The ingredient is spent. Remove one from the inventory
					e.getItem().setAmount(e.getItem().getAmount()-1);
				} else {
					//Error effects
					e.getPlayer().playSound(loc, Sound.FIZZ, 1F, 1F);
				}
			}
		}
	}

	private void buildCauldron(Location loc){
		Brew brew = Brew.WATER;
		cauldronMap.put(loc, brew);
	}
}
