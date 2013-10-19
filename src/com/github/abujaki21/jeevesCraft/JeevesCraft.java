/************************************************************\
 * Author: abujaki21										*
 * Plugin: JoshCraft										*
 * Notes: This is a collection of small one-offs used to 	*
 * fine tune things I dont agree with. Presented to Josh for*
 * his server.												*
 * 															*
 * website: https://github.com/abujaki21/minestock			*
 * 															*
 * Version: 1.2												*
 * 															*
 * This software is presented AS IS and without warranty	*
 * of any kind. I will not be held responsible for			*
 * losses imagined or otherwise from use of this software.	*
 *  														*
 \***********************************************************/

package com.github.abujaki21.jeevesCraft;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public final class JeevesCraft extends JavaPlugin implements Listener{
	private FileConfiguration config;
	private Logger logger;
	private Server server;
	
	@Override
	public void onEnable(){
		logger = this.getLogger();
		server = this.getServer();
		logger.info("OnEnable");
		if(!(new File(getDataFolder().toString().concat("config.yml")).exists())){
			logger.info("Setting up for the first time...");
			this.saveDefaultConfig();
			logger.info("Loaded defaults");
		}
		
		config = getConfig();
		logger.info("Loading Recipes...");
		enableRecipes();
		logger.info("Listening intently...");
		server.getPluginManager().registerEvents(this, this);
	}
	
	@Override
	public void onDisable(){
		//Don't think there will be much here
		
		//Not necessary yet as there is no way to change yet it in the plugin
		//this.saveConfig();
		logger.info("Goodnight");
	}
	
	@EventHandler
	public void onPlayerFishing(PlayerFishEvent event){
		if(!(event.getCaught() == null)){
			event.setCancelled(true);
			double num = (Math.random()*100);
			ItemStack fishy = new ItemStack(Material.RAW_FISH);
			int expdrop = 2;
			if(num < 10){
				expdrop = 1;
			}else if(num < 60){
				//Nothing, this is our default
			}else if(num < 70){
				expdrop = 3;
			}else if(num < 76){
				expdrop = 3;
				fishy = new ItemStack(Material.RAW_FISH, 2);
			}else if(num < 81){
				fishy = new ItemStack(Material.FISHING_ROD);
				fishy.setDurability((short)(64 - (num % 5)));
			}else if(num < 86){
				fishy = new ItemStack(Material.LEATHER_BOOTS,1);
				fishy.setDurability((short)(65 - (num % 5)));
			}else if(num < 94){
				fishy = new ItemStack(Material.GLASS_BOTTLE);
			}else if(num < 96){
				//random potion
			}else if(num < 99){
				fishy = new ItemStack(Material.GRASS);
			}else if(num == 99){
				fishy = new ItemStack(Material.FISHING_ROD);
				fishy.addEnchantment(Enchantment.DURABILITY, 1);
			}else if(num == 100){
				fishy = new ItemStack(Material.DIAMOND);
				expdrop = 10;
			}
			
			/*
			 * Fish - 50% of the time
			 * Boots (Damaged) 5%
			 * 
			 * Diamond - 1%
			 */
			
			event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), fishy);
			event.getPlayer().giveExp(expdrop);
		}
	}
	
	//-------Recipes--------
	private void enableRecipes(){
		
		//Recipe to turn Rotten Flesh into Leather
		if(config.getBoolean("Recipe.Leather.Enabled")){
			int amt = config.getInt("Recipe.Leather.FleshForLeather");
			ShapelessRecipe leather = new ShapelessRecipe(new ItemStack(Material.LEATHER,1));
			leather = leather.addIngredient(amt, Material.ROTTEN_FLESH);
			server.addRecipe(leather);
			logger.info("Added recipe: " + amt + " ROTTEN_FLESH for 1 LEATHER");
		}
		
		//Redcipes for Horse armor:
		// _ _ X
		// X X X     - Where X is Iron, Gold, or Diamond
		// X _ X
		if(config.getBoolean("Recipe.HorseArmor.Iron")){
			ShapedRecipe HAIarmor = new ShapedRecipe(new ItemStack(Material.IRON_BARDING));
			HAIarmor.shape("  I","III","I I");
			HAIarmor.setIngredient('I',Material.IRON_INGOT);
			server.addRecipe(HAIarmor);
			logger.info("Added recipe: Horse Armor (Iron)");
		}
		if(config.getBoolean("Recipe.HorseArmor.Gold")){
			ShapedRecipe HAGarmor = new ShapedRecipe(new ItemStack(Material.GOLD_BARDING));
			HAGarmor.shape("  G","GGG","G G");
			HAGarmor.setIngredient('G', Material.GOLD_INGOT);
			server.addRecipe(HAGarmor);
			logger.info("Added recipe: Horse Armor (Gold)");
		}
		if(config.getBoolean("Recipe.HorseArmor.Diamond")){
			ShapedRecipe HADarmor = new ShapedRecipe(new ItemStack(Material.DIAMOND_BARDING));
			HADarmor.shape("  D","DDD","D D");
			HADarmor.setIngredient('D', Material.DIAMOND);
			server.addRecipe(HADarmor);
			logger.info("Added recipe: Horse Armor (Diamond)");
		}

		//Recipe for Saddle
		if(config.getBoolean("Recipe.Saddle")){
			ShapedRecipe saddle = new ShapedRecipe(new ItemStack(Material.SADDLE));
			saddle.shape("  u","LLL","LIL");
			saddle.setIngredient('u', Material.LEASH);
			saddle.setIngredient('L', Material.LEATHER);
			saddle.setIngredient('I', Material.IRON_INGOT);
			server.addRecipe(saddle);
			logger.info("Added recipe: Saddle");
		}
		
		//Recipe for Nametag
		if(config.getBoolean("Recipe.Nametag")){
			ShapelessRecipe label = new ShapelessRecipe(new ItemStack(Material.NAME_TAG));
			label.addIngredient(Material.STRING);
			label.addIngredient(Material.PAPER);
			label.addIngredient(Material.INK_SACK);
			server.addRecipe(label);
			logger.info("Added recipe: Nametag");
		}
	}
}
