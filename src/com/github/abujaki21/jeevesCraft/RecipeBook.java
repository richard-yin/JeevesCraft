package com.github.abujaki21.jeevesCraft;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

public class RecipeBook {
	public static void enableRecipes(Server server, YamlConfiguration config){
		//Recipe to turn Rotten Flesh into Leather
		Logger logger = server.getLogger();
		
		if(config.getBoolean("Recipe.Leather.Enabled")){
			int amt = config.getInt("Recipe.Leather.FleshForLeather");
			ShapelessRecipe leather = new ShapelessRecipe(new ItemStack(Material.LEATHER,1));
			leather = leather.addIngredient(amt, Material.ROTTEN_FLESH);
			server.addRecipe(leather);
			logger.info(ChatColor.DARK_AQUA + "Added recipe: " + amt + " ROTTEN_FLESH for 1 LEATHER");
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
			logger.info(ChatColor.DARK_AQUA + "Added recipe: Horse Armor (Iron)");
		}
		if(config.getBoolean("Recipe.HorseArmor.Gold")){
			ShapedRecipe HAGarmor = new ShapedRecipe(new ItemStack(Material.GOLD_BARDING));
			HAGarmor.shape("  G","GGG","G G");
			HAGarmor.setIngredient('G', Material.GOLD_INGOT);
			server.addRecipe(HAGarmor);
			logger.info(ChatColor.DARK_AQUA + "Added recipe: Horse Armor (Gold)");
		}
		if(config.getBoolean("Recipe.HorseArmor.Diamond")){
			ShapedRecipe HADarmor = new ShapedRecipe(new ItemStack(Material.DIAMOND_BARDING));
			HADarmor.shape("  D","DDD","D D");
			HADarmor.setIngredient('D', Material.DIAMOND);
			server.addRecipe(HADarmor);
			logger.info(ChatColor.DARK_AQUA + "Added recipe: Horse Armor (Diamond)");
		}

		//Recipe for Saddle
		if(config.getBoolean("Recipe.Saddle")){
			ShapedRecipe saddle = new ShapedRecipe(new ItemStack(Material.SADDLE));
			saddle.shape("  u","LLL","LIL");
			saddle.setIngredient('u', Material.LEASH);
			saddle.setIngredient('L', Material.LEATHER);
			saddle.setIngredient('I', Material.IRON_INGOT);
			server.addRecipe(saddle);
			logger.info(ChatColor.DARK_AQUA + "Added recipe: Saddle");
		}

		//Recipe for Nametag
		if(config.getBoolean("Recipe.Nametag")){
			ShapelessRecipe label = new ShapelessRecipe(new ItemStack(Material.NAME_TAG));
			label.addIngredient(Material.STRING);
			label.addIngredient(Material.PAPER);
			label.addIngredient(Material.INK_SACK);
			server.addRecipe(label);
			logger.info(ChatColor.DARK_AQUA + "Added recipe: Nametag");
		}
		
		//RESERVED FOR PACKED ICE RECIPE
		
	}
}
