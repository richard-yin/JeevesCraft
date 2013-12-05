package com.github.abujaki21.jeevesCraft;

import org.bukkit.Material;

public class Brew {

	//Good things to know
	private static final short WATER = 0x0000;
	private static final short SPLASH = 0x4000;
	private static final short DRINK = 0x2000;
	private static final short POWER = 0x0040;
	private static final short DURATION = 0x0020;
	//Impossible: 0x0060
	//Illogical: 0x6000 --> Splash will override drink
	//Effect: 0x000F
	//Name: 0x003F
	//Ignored: 0x9F80
	
	
	short pot;
	public Brew(){
		pot = WATER;
	}


	public boolean addIngredient(Material m){
		switch(m){
		case REDSTONE:
			elongatePotion();
			break;
		case GLOWSTONE_DUST:
			powerPotion();
			break;
		case SULPHUR:
			splashPotion();
			break;
		case FERMENTED_SPIDER_EYE:
			spoilPotion();
			break;
		case NETHER_WARTS:
		case SPIDER_EYE:
		case SUGAR:
		case SPECKLED_MELON:
		case GHAST_TEAR:
		case BLAZE_POWDER:
		case MAGMA_CREAM:
		case GOLDEN_CARROT:
			break;
		default:
			//Not an ingredient
			return false;
		}
		//If any of the correct ingredients were added.
		return true;
	}


	private void spoilPotion() {
		// TODO Auto-generated method stub
		//Does funny things.
	}


	private void splashPotion() {
		//Set splash flag, remove drink flag
		//Even though splash would override drink anyway, it's good practice(?)
		pot = (short) ((pot | SPLASH)& ~DRINK);
	}


	private void powerPotion() {
		//Add to the power flag, remove the duration if it exists.
		//((Potion OR power) AND NOT duration)
		pot = (short)((pot | POWER) & ~DURATION);
	}


	private void elongatePotion() {
		//Add the duration flag, remove the power flag if exists.
		//((potion OR duration) AND NOT power)
		pot = (short)((pot | DURATION)& ~POWER);
	}
}
