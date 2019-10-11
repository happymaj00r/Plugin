package myvcrime.abilities;

import org.bukkit.Material;

public enum AbilityItems {
	
	KNIFE_UPGRADE_LV1((Ability)PrimaryAbilities.FIRE_KEGEL,"Knife lv1",new String[]{"lol"},1,UpgradeType.KNIFE, Material.BOOK,500.0),
	BAISY_UPGRADE_LV1((Ability)PrimaryAbilities.FIRE_KEGEL,"baisy lv1",new String[]{"lol"},1,UpgradeType.BAISY, Material.BOOK,500.0),
	MACHETE_UPGRADE_LV1((Ability)PrimaryAbilities.FIRE_KEGEL,"machete lv1",new String[]{"lol"},1,UpgradeType.MACHETE, Material.BOOK,500.0),
	MACHETE_UPGRADE_LV3((Ability)PrimaryAbilities.SHADOW_WALKER,"machete lv1",new String[]{"shadow walker"},1,UpgradeType.MACHETE, Material.BOOK,1500.0),
	KNIFE_UPGRADE_LV2((Ability)PrimaryAbilities.FIRE_KEGEL,"knife lv2",new String[]{"lol"},1,UpgradeType.KNIFE, Material.BOOK,500.0),
	MACHETE_UPGRADE_LV2((Ability)PrimaryAbilities.SHADOW_DISPERSION,"Machete Upgrade lv 2",new String[]{"Shadow Dispersion"},1,UpgradeType.MACHETE, Material.BOOK,1000.0),
	BAISY_UPGRADE_LV2((Ability)PrimaryAbilities.FIREBALL,"baidy lv2",new String[]{"lol"},1,UpgradeType.BAISY, Material.BOOK,500.0);
	private Ability ability;
	private String name;
	private String[] description;
	private int requiredLevel;
	private UpgradeType upgradeType;
	private Material mat;
	private double price;
	
	AbilityItems(Ability ability,String name, String[] description, int requiredLevel,UpgradeType upgradeType, Material mat, double price){
		this.ability = ability;
		this.name = name;
		this.description = description;
		this.requiredLevel = requiredLevel;
		this.upgradeType = upgradeType;
		this.mat = mat;
		this.price = price;
	}
	
	public double getPrice(){
		return price;
	}
	
	public Material getMaterial(){
		return mat;
	}
	
	public String getName(){
		return name;
	}
	
	public String[] getDescription(){
		return description;
	}
	
	public UpgradeType getUpgradeType(){
		return upgradeType;
	}
	
	public static AbilityItems[] knifeUpgrades(){
		return new AbilityItems[]{KNIFE_UPGRADE_LV1,KNIFE_UPGRADE_LV2};
	}
	
	public static AbilityItems[] baisyUpgrades(){
		return new AbilityItems[]{BAISY_UPGRADE_LV1,BAISY_UPGRADE_LV2};
	}
	
	public static AbilityItems[] macheteUpgrades(){
		return new AbilityItems[]{MACHETE_UPGRADE_LV1,MACHETE_UPGRADE_LV2,MACHETE_UPGRADE_LV3};
	}
		
	public int getRequiredLevel(){
		return requiredLevel;
	}
	
	public Ability getAbility(){
		return ability;
	}
}
