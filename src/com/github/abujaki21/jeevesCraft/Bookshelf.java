package com.github.abujaki21.jeevesCraft;

import java.io.*;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Bookshelf implements Listener{
	private static HashMap<Location, Inventory> bookMap;
//	private File folder;
	
	public Bookshelf(JeevesCraft jeeves, File dataFolder){
		bookMap = new HashMap<Location, Inventory>();
		//Load the shelves from file
//		folder = dataFolder;
		load();
		jeeves.getServer().getPluginManager().registerEvents(this, jeeves);
		//TODO: Saving the bookshelves to file is also probably a good idea
	}
	
	public void load(){
		
	}
	
	public void save(){
		
	}
	
	@EventHandler
	public void breakBookshelf(BlockBreakEvent e){
		if((e.getBlock().getType() == Material.BOOKSHELF)){
			//If a bookshelf is broken
			Location loc = e.getBlock().getLocation();
			//And it has an inventory in the map
			if(bookMap.containsKey(loc)){
				//Drop the inventory
				ItemStack[] inv = bookMap.get(loc).getContents();
				for(int i = 0; i < inv.length; i++){
					if(inv[i] != null){
						//If the item isn't null, drop it
						e.getBlock().getWorld().dropItemNaturally(loc, inv[i]);
					}
				}
				//Remove the inventory from the map
				bookMap.remove(loc);
			}
		}
	}

	@EventHandler
	public void inventoryClickBookshelf(InventoryClickEvent e){
		if(e.getInventory().getTitle() == "Bookshelf"){
			Player p = (Player) e.getWhoClicked();
			p.sendMessage("Inventory event: " + e.getAction());
			if((e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) ||
					(e.getAction() == InventoryAction.PLACE_ALL) ||
					(e.getAction() == InventoryAction.PLACE_ONE) ||
					(e.getAction() == InventoryAction.PLACE_SOME)){

				ItemStack itemcur = e.getCursor();
				Material it = itemcur.getType();
				//If the inventory is a bookshelf inventory
				//Limit the inventory to only
				//Books
				//Books and Quills
				//Signed books
				//Enchanted books
				//Maps
				//Records
				switch (it){
				case BOOK:
				case BOOK_AND_QUILL:
				case WRITTEN_BOOK:
				case ENCHANTED_BOOK:
				case MAP:
				case GOLD_RECORD:
				case GREEN_RECORD:
				case RECORD_3:
				case RECORD_4:
				case RECORD_5:
				case RECORD_6:
				case RECORD_7:
				case RECORD_8:
				case RECORD_9:
				case RECORD_10:
				case RECORD_11:
				case RECORD_12:
					break;
				case AIR:
					//Strange things happen when shift-Click moving
					break;
				default:
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void placeBookshelf(BlockPlaceEvent e){
		//If a bookshelf is placed, give it an inventory
		if((e.getBlock().getType() == Material.BOOKSHELF)){
			makeShelf(e.getBlock().getLocation());
		}
	}

	@EventHandler
	public void clickBookshelf(PlayerInteractEvent e){
		if((e.getAction() == Action.RIGHT_CLICK_BLOCK) &&
				(e.getClickedBlock().getType() == Material.BOOKSHELF)){
			//Bookshelf is right-clicked
			if(e.isBlockInHand()){ //Convenience method to see if block was placed
				//Unplace block
				e.setCancelled(true);	
			}

			Location loc = e.getClickedBlock().getLocation();
			if(!bookMap.containsKey(loc)){
				//If there's no inventory, make one
				makeShelf(loc);
			}
			//Open the inventory
			e.getPlayer().openInventory(bookMap.get(loc));
		}
	}

	private void makeShelf(Location shelf){
		//Create the inventory
		Inventory newInv = Bukkit.createInventory(null, 9, "Bookshelf");
		//Limit the stack size
		//newInv.setMaxStackSize(1);
		//Add the bookshelf to the map
		bookMap.put(shelf, newInv);
	}
}