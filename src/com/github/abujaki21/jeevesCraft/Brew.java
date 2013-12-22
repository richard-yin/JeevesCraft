package com.github.abujaki21.jeevesCraft;

import org.bukkit.Material;

public enum Brew {
	WATER (0x0000), //---
	DRINK (0x0020),
	DURATION (0x0020),
	POWER (0x0040),
	SPLASH (0x003F),
	
	//---Good things to know---
	//Impossible: 0x0060
	//Illogical: 0x6000 --> Splash will override drink
	//Effect: 0x000F
	//Name: 0x003F
	//Ignored: 0x9F80

	ACRID (0x003B),
	AWKWARD (0x0010),
	BUNGLING (0x0017),
	CHARMING (0x0027),
	CLEAR (0x0007),
	DEBONAIR (0x001F),
	DIFFUSE (0x000B),
	FIRERESIST (0x2003), //---
	FIRERESISTE (0x2043), //---E
	HARMING (0x200C), //---
	HARMING2 (0x202C), //---2
	HEALING (0x2005), //---
	HEALING2 (0x2025), //---2
	INVISIBILITY (0x200E), //---
	INVISIBILITYE (0x204E), //---E
	MUNDANE (0x2000), //---
	MUNDANEE (0x0040),
	NIGHTVISION (0x2006), //---
	NIGHTVISIONE (0x2046), //---E
	POISON (0x2004), //---
	POISON2 (0x2024), //---
	POISON2E (0x2064), //---
	POISONE (0x2044), //---
	POTENT (0x0030),
	RANK (0x0037),
	REFINED (0x002B),
	REGEN (0x2001), //---
	REGEN2 (0x2021), //---
	REGENE (0x2041), //---
	REGENERATION2E (0x2061),
	SLOWNESS (0x200A), //---
	SLOWNESSE (0x204A), //---
	SMOOTH (0x001B),
	SPARKLING (0x002F),
	STINKY (0x003F),
	STRENGTH (0x2009), //---
	STRENGTH2 (0x2029), //---
	STRENGTH2E (0x2069),
	STRENGTHE (0x2049), //---
	SWIFTNESS (0x2002), //---
	SWIFTNESS2 (0x2022), //---
	SWIFTNESS2E (0x2062),
	SWIFTNESSE (0x2042), //---
	THICK (0x0020),
	THIN (0x000F),
	WATERBREATHING (0x200D), //---
	WATERBREATHINGE (0x204D), //---
	WEAKNESS (0x2008), //---
	WEAKNESSE (0x2048); //---
	
	int pot; //Placeholder 
	Brew(int data){
		this.pot = data;
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
		//Does funny things.
	}

	private void splashPotion() {
		//Set splash flag, remove drink flag
		//Even though splash would override drink anyway, it's good practice(?)
		//pot = (short) ((pot | SPLASH)& ~DRINK);
	}

	private void powerPotion() {
		//Add to the power flag, remove the duration if it exists.
		//((Potion OR power) AND NOT duration)
		//pot = (short)((pot | POWER) & ~DURATION);
	}

	private void elongatePotion() {
		//Add the duration flag, remove the power flag if exists.
		//((potion OR duration) AND NOT power)
		//pot = (short)((pot | DURATION)& ~POWER);
	}
}
