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
 * Version: 2.6.4mer										*
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
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Server;

public final class JeevesCraft extends JavaPlugin implements Listener{
	private Logger logger;
	private Server server;
	private File configFile;
	private YamlConfiguration config;
	private boolean makeGiant;

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
		logger.info("Listening intently...");
		server.getPluginManager().registerEvents(this, this);

		makeGiant = config.getBoolean("Spawn.Giant");
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

	@EventHandler
	public void onMobSpawn(CreatureSpawnEvent event){
		if(event.getEntityType() == EntityType.GIANT){
			server.broadcastMessage(ChatColor.RED + "Fee, Fi, Fo, Fum");
		}
		else if(event.getEntityType() == EntityType.ZOMBIE){
			if(makeGiant){
				int sChance = (int)(Math.random() * 1000);
				if(sChance <= 5){
					event.getEntity().getWorld().spawnEntity(event.getLocation(), EntityType.GIANT);
					event.setCancelled(true);
					makeGiant = false;
				}
			}
		}
	}

	@EventHandler
	public void onGiantDeath(EntityDeathEvent event){
		//If a giant dies
		if(event.getEntityType() == EntityType.GIANT){
			//Add slightly better drops
			short flesh = (short)((Math.random()*48) + 16);
			short gold = (short)((Math.random()*4)+2);
			event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), new ItemStack(Material.ROTTEN_FLESH,flesh));
			event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), new ItemStack(Material.GOLD_INGOT, gold));
			//Add more EXP
			event.setDroppedExp(30);
			//Because 5 exp for a giant is balls
			makeGiant = true;
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

		//if(config.getBoolean("Recipe.GiantEgg")){
		//Commented out until I can figure out how to update the config file
		ItemStack egg = new ItemStack(Material.MONSTER_EGG);
		egg.setDurability((short)53);
		ShapedRecipe gEgg = new ShapedRecipe(egg);
		gEgg.shape("fbf","beb","fbf");
		gEgg.setIngredient('f', Material.ROTTEN_FLESH);
		gEgg.setIngredient('b', Material.BONE);
		gEgg.setIngredient('e', Material.EGG);
		server.addRecipe(gEgg);
		//Make it rotatable as well
		gEgg.shape("bfb","fef","bfb");
		server.addRecipe(gEgg);
		//}
	}
}
