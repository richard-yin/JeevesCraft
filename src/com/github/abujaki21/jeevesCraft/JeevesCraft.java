/************************************************************\
 * Author: abujaki21										*
 * Plugin: JeevesCraft										*
 * Notes: This is a collection of small one-offs used to 	*
 * fine tune things I dont agree with. Presented to Josh for*
 * his server.												*
 * 															*
 * website: https://github.com/abujaki21/minestock			*
 * 															*
 * Version: 2.6.5dev										*
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
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Server;

public final class JeevesCraft extends JavaPlugin implements Listener{
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
			logger.info(ChatColor.DARK_AQUA + "No configuration exists! Setting up for the first time");
			saveDefaultConfig();
		}

		//--Load configuration file
		try {
			config = new YamlConfiguration();
			config.load(configFile);
		} catch (InvalidConfigurationException e) { //Bad configuration
			logger.severe(ChatColor.RED + "Config file is broken. Restoring defaults...");
			saveDefaultConfig();
		} catch (IOException e){ //Issues with opening the file
			logger.severe(ChatColor.RED + "Unable to read the config file, Using defaults");
		}

		logger.info(ChatColor.DARK_AQUA + "Loading Recipes...");
		RecipeBook.enableRecipes(server, config);
		
		logger.info(ChatColor.DARK_AQUA + "Listening intently...");
		new GiantSpawner(this);
	}

	@Override
	public void onDisable(){
		//Try to save the config file
		try {
			config.save(configFile);
		} catch (IOException e) {
			logger.severe(ChatColor.RED + "Config file could not be saved");
		}
		logger.info(ChatColor.DARK_AQUA + "Goodnight");
	}
}
