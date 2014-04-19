package com.github.abujaki21.jeevesCraft;

import java.util.logging.Logger;

import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.inventory.*;

public class SnowPistons implements Listener {
	Logger logger;
	public SnowPistons(JeevesCraft jeeves) {
		logger = jeeves.getServer().getLogger();
		logger.info("Loaded piston/snow mechanics");
		jeeves.getServer().getPluginManager().registerEvents(this, jeeves);
	}
	
	@EventHandler
	public void onSnowCoverPushed(BlockPistonExtendEvent event) {
		int i = event.getBlocks().size(); //number of blocks pushed
		Block snowBlock = event.getBlock().getRelative(event.getDirection(), i+1);//block with snow in it
		Block endBlock = event.getBlock().getRelative(event.getDirection(), i+2);//block where snowball ends up
		if(snowBlock.getType() == Material.SNOW) {
			int x = endBlock.getX(); //coords of end block
			int y = endBlock.getY();
			int z = endBlock.getZ();
			
			//drop item at coords
			snowBlock.getWorld().dropItem(new Location(snowBlock.getWorld(), x+0.5, y+0.5, z+0.5), new ItemStack(Material.SNOW_BALL, 1));
		}
	}
}
