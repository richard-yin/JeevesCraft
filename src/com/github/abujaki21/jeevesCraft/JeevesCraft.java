/************************************************************\
 * Author: abujaki21										*
 * Plugin: JoshCraft										*
 * Notes: This is a collection of small one-offs used to 	*
 * fine tune things I dont agree with. Presented to Josh for*
 * his server.												*
 * 															*
 * website: https://github.com/abujaki21/minestock			*
 * 															*
 * Version: 2.1												*
 * 															*
 * This software is presented AS IS and without warranty	*
 * of any kind. I will not be held responsible for			*
 * losses imagined or otherwise from use of this software.	*
 *  														*
 \***********************************************************/

package com.github.abujaki21.jeevesCraft;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Server;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

public final class JeevesCraft extends JavaPlugin{
	private Logger logger;
	private Server server;
	private File configFile;
	private YamlConfiguration config;
	
	@Override
	public void onEnable(){
		logger = getLogger();
		server = getServer();
		configFile = new File(getDataFolder(), "config.yml");
		
		//---If there is no configuration file, create one from default
		if(!configFile.exists()){
			logger.info("No configuration exists! Setting up for the first time");
			saveDefaultConfig();
		}
		
		//--Load configuration file
		try {
			config = new YamlConfiguration();
			config.load(configFile);
		} catch (InvalidConfigurationException e) { //Bad configuration
			logger.severe("Config file is broken. Restoring defaults...");
			saveDefaultConfig();
		} catch (IOException e){ //Issues with opening the file
			logger.severe("Unable to read the config file, Using defaults");
		}
		
		logger.info("Loading Recipes...");
		enableRecipes();
	}
	
	@Override
	public void onDisable(){
		
		//Try to save the config file
		try {
			config.save(configFile);
		} catch (IOException e) {
			logger.severe("Config file could not be saved");
		}
		logger.info("Goodnight");
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
		
		//Recipe for Horse armor:
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
