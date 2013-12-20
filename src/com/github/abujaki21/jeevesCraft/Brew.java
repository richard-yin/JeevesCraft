package com.github.abujaki21.jeevesCraft;

import org.bukkit.Material;

public enum Brew {
	WATER (0x0000),
	DRINK (0x0020),
	DURATION (0x0020),
	POWER (0x0040),
	SPLASH (0x003F);
	
	
	//Good things to know

	//Impossible: 0x0060
	//Illogical: 0x6000 --> Splash will override drink
	//Effect: 0x000F
	//Name: 0x003F
	//Ignored: 0x9F80
	
	/*
	private static final short DRINK = 0x2000; //Placeholder
	private static final short DURATION = 0x0020; //Placeholder
	private static final short POWER = 0x0040; //Placeholder
	private static final short SPLASH = 0x4000; //Placeholder
	private static final short WATER = 0x0000; //Base case
	*/
	
	/*
	private static final short ACRID = 0x003B;
	private static final short AWKWARD = 0x0010;
	private static final short BUNGLING = 0x0017;
	private static final short CHARMING = 0x0027;
	private static final short CLEAR = 0x0007;
	private static final short DEBONAIR = 0x001F;
	private static final short DIFFUSE = 0x000B;
	private static final short FIRERESIST = 0x2003;
	private static final short FIRERESISTE = 0x2043;
	private static final short HARMING = 0x200C;
	private static final short HARMING2 = 0x202C;
	private static final short HEALING = 0x2005;
	private static final short HEALING2 = 0x2025;
	private static final short INVISIBILITY = 0x200E;
	private static final short INVISIBILITYE = 0x204E;
	private static final short MUNDANE = 0x2000;
	private static final short MUNDANEE = 0x0040;
	private static final short NIGHTVISION = 0x2006;
	private static final short NIGHTVISIONE = 0x2046;
	private static final short POISON = 0x2004;
	private static final short POISON2 = 0x2024;
	private static final short POISON2E = 0x2064;
	private static final short POISONE = 0x2044;
	private static final short POTENT = 0x0030;
	private static final short RANK = 0x0037;
	private static final short REFINED = 0x002B;
	private static final short REGEN = 0x2001;
	private static final short REGEN2 = 0x2021;
	private static final short REGENE = 0x2041;
	private static final short REGENERATION2E = 0x2061;
	private static final short SLOWNESS = 0x200A;
	private static final short SLOWNESSE = 0x204A;
	private static final short SMOOTH = 0x001B;
	private static final short SPARKLING = 0x002F;
	private static final short STINKY = 0x003F;
	private static final short STRENGTH = 0x2009;
	private static final short STRENGTH2 = 0x2029;
	private static final short STRENGTH2E = 0x2069;
	private static final short STRENGTHE = 0x2049;
	private static final short SWIFTNESS = 0x2002;
	private static final short SWIFTNESS2 = 0x2022;
	private static final short SWIFTNESS2E = 0x2062;
	private static final short SWIFTNESSE = 0x2042;
	private static final short THICK = 0x0020;
	private static final short THIN = 0x000F;
	private static final short WATERBREATHING = 0x200D;
	private static final short WATERBREATHINGE = 0x204D;
	private static final short WEAKNESS = 0x2008;
	private static final short WEAKNESSE = 0x2048;
	*/
	
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
		// TODO Auto-generated method stub
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
