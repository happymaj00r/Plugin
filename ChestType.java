package myvcrime;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public enum ChestType implements CustomItemsInterface {
	COMMON("Common Lootchest","common",Material.TOTEM,
			new Material[]{},//common 70%
			new Material[]{},//rare 20%
			new Material[]{},//epic 9%
			new Material[]{} //legendary 1%
	),
	DAILY(ChatColor.DARK_PURPLE + "Pack","A mysterious pack which got mysterious content",Material.TOTEM,
			new Material[]{Material.WOOD_AXE,Material.WOOD_SWORD},//common 70%
			new Material[]{Material.STONE_AXE,Material.STONE_SWORD},//rare 20%
			new Material[]{Material.IRON_AXE,Material.IRON_SWORD},//epic 9%
			new Material[]{Material.GOLD_SWORD,Material.GOLD_AXE,Material.DIAMOND_AXE,Material.DIAMOND_SWORD} //legendary 1%
			);
	
	
	String name;
	String description;
	Material[] commonLoot;
	Material[] rareLoot;
	Material[] epicLoot;
	Material[] legendaryLoot;
	Material mat;
	
	ChestType(String name, String description, Material mat, Material[] commonLoot,Material[] rareLoot, Material[] epicLoot, Material[] legendaryLoot){
		this.name = name;
		this.description = description;
		this.mat = mat;
		this.commonLoot = commonLoot;
		this.rareLoot = rareLoot;
		this.epicLoot = epicLoot;
		this.legendaryLoot = legendaryLoot;
	}
	
	public String getName(){
		return name;
	}
	
	public String getDescription(){
		return description;
	}
	
	public Material getMaterial(){
		return mat;
	}
	
	public Material[] specialLoot(){
		Material[] mat = {};
		switch(name){
			case "Dailychest":
				mat[0] = Material.GOLD_NUGGET;
				mat[1] = Material.BREAD;
				mat[2] = Material.ARROW;
				break;
		}
		return mat;
	}
	
	public ItemStack[] getFixedLoot(){
		ItemStack[] nothing = {};
		if(ChatColor.stripColor(name).equalsIgnoreCase("Pack")){
			ItemStack[] items = {new ItemStack(Material.GOLD_NUGGET,40),new ItemStack(Material.BREAD,10),new ItemStack(Material.ARROW,16)};
			return items;
		}
		return nothing;
	}
	
	public Material[] getCommonLoot(){
		return commonLoot;
	}
	public Material[] getRareLoot(){
		return rareLoot;
	}
	public Material[] getEpicLoot(){
		return epicLoot;
	}
	public Material[] getLegendaryLoot(){
		return legendaryLoot;
	}
	public static ChestType[] getChests(){
		ChestType[] chests = {DAILY,COMMON};
		return chests;
	}
}
