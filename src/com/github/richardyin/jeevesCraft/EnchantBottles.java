/**
 * Plugin: JeevesCraft
 * Author: Richard Yin
 * 
 * Allows water bottles and potions to be converted to experience bottles using an enchanting table.
 */

package com.github.richardyin.jeevesCraft;

import java.util.logging.*;
import java.util.*;

import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.enchantment.*;

import com.github.abujaki21.jeevesCraft.JeevesCraft;

public class EnchantBottles implements Listener {
	Random rand;
	Logger logger;
	public EnchantBottles(JeevesCraft jeeves) {
		rand = new Random();
		logger = jeeves.getServer().getLogger();
		logger.info("Loaded experience bottle enchanting");
		jeeves.getServer().getPluginManager().registerEvents(this, jeeves);
	}
	
	/*
	 * From Jikoo's EnchantedFurnace mod, available on github.
	 */
	private int getEnchantingLevel(int slot, int shelves) {
		// taken from vanilla removed itemstack's enchantability == 0 check.
		if (shelves > 15) {
			shelves = 15;
		}
		int var6 = rand.nextInt(8) + 1 + (shelves >> 1) + rand.nextInt(shelves + 1);
		return slot == 0 ? Math.max(var6 / 3, 1): (slot == 1 ? var6 * 2 / 3 + 1 : Math.max(var6, shelves * 2));
	}
	
	@EventHandler
	public void onPrepareEnchantBottle(PrepareItemEnchantEvent event) {
		if(event.getItem().getType() == Material.POTION) {
			event.setCancelled(false);
			for (int i = 0; i < 3; i++) {
				event.getExpLevelCostsOffered()[i] = getEnchantingLevel(i, event.getEnchantmentBonus());
			}
		}
	}
	
	
	@EventHandler
	public void onEnchantBottle(EnchantItemEvent event) {
		System.out.println("got here");
		if(event.getItem().getType() == Material.POTION) {
			event.getEnchantsToAdd().clear();
			event.getItem().setType(Material.EXP_BOTTLE);
			event.getItem().setAmount(event.getExpLevelCost() * 2);
		}
	}
}
