package com.github.abujaki21.jeevesCraft;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class SpongeMech implements Listener{
	private int range;
	
	public SpongeMech(JeevesCraft jeeves){
		//Range = (Diameter - 1) / 2
		//Eg. 5x5x5 block: range = (5-1)/2 = 4/2 = 2.
		
		range = 2;
		//TODO: Make configurable if there's a demand for it
		//range = jeeves.getConfig().getInt("Sponge.Range");
		
		//Register listener with the plugin
		jeeves.getServer().getPluginManager().registerEvents(this, jeeves);
	}
	
	@EventHandler
	public void onSpongePlace(BlockPlaceEvent event){
		//If a sponge block is placed
		if(event.getBlockPlaced().getType() == Material.SPONGE){
			//Get x y z values of the sponge
			int locX = event.getBlockPlaced().getX() - range;
			int locY = event.getBlockPlaced().getY() - range;
			int locZ = event.getBlockPlaced().getZ() - range;
			World world = event.getBlockPlaced().getWorld();
			Block curr;
			
			//Loop through xyz and replace all water with air
			for(int i = locX; i <= (locX + (2*range)); i++){ //Loop x
				for(int j = locY; j <= (locY + (2*range)); j++){ //Loop y
					for(int k = locZ; k <= (locZ + (2*range)); k++){ //Loop z
						curr = world.getBlockAt(i, j, k); //X, Y, Z
						if(curr.getType() == Material.WATER)
								curr.setType(Material.AIR);
					}
				}
			}
		}
	}
}