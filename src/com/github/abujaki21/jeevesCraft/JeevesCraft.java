/************************************************************\
 * Author: abujaki21										*
 * Plugin: JoshCraft										*
 * Notes: This is a collection of small one-offs used to 	*
 * fine tune things I dont agree with. Presented to Josh for*
 * his server.												*
 * 															*
 * website: https://github.com/abujaki21/minestock			*
 * 															*
 * Version: 1.0.0											*
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public final class JeevesCraft extends JavaPlugin{
	private FileConfiguration config;
	private Logger logger;
	
	@Override
	public void onEnable(){
		logger = this.getLogger();
		logger.info("OnEnable");
		if(!(new File(getDataFolder().toString().concat("config.yml")).exists())){
			logger.info("Setting up for the first time...");
			this.saveDefaultConfig();
			logger.info("Loaded defaults");
		}
		
		config = getConfig();
		logger.info("Loading Recipes...");
		enableRecipes();
	}
	
	@Override
	public void onDisable(){
		//Don't think there will be much here
		
		//Not necessary yet as there is no way to change yet it in the plugin
		//this.saveConfig();
		logger.info("Goodnight");
	}
	
	//-------Recipes--------
	private void enableRecipes(){
		Server server = this.getServer();
		
		if(config.getBoolean("Recipe.Leather.Enabled")){
			int amt = config.getInt("Recipe.Leather.FleshForLeather");
		
			//Add Reciepe rotten Flesh ==> Leather 
			ShapelessRecipe leather = new ShapelessRecipe(new ItemStack(Material.LEATHER,1));
			leather = leather.addIngredient(amt, Material.ROTTEN_FLESH);
			server.addRecipe(leather);
			logger.info("Added recipe: " + amt + " ROTTEN_FLESH for 1 LEATHER");
		}
	}
}
