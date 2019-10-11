package myvcrime;

import org.bukkit.Material;

public enum WeaponsType implements CustomToolsInterface{
	P99(Material.WOOD_AXE,"P99"),
	REVOLVER(Material.STONE_AXE,"Revolver"),
	LMG(Material.IRON_AXE,"LMG"),
	MINIGUN(Material.GOLD_AXE,"Minigun"),
	RPG(Material.DIAMOND_AXE,"RPG"),
	KATANA(Material.DIAMOND_SWORD,"Katana"),
	CLEAVER(Material.GOLD_SWORD,"Cleaver"),
	KNIFE(Material.STONE_SWORD,"Knife"),
	BAISY(Material.WOOD_SWORD,"Baisy"),
	MACHETE(Material.IRON_SWORD,"Machete");
	
	Material mat;
	String name;
	
	WeaponsType(Material mat, String name){
		this.name = name;
		this.mat = mat;
	}
	
	public String getName(){
		return name;
	}
	public Material getMaterial(){
		return mat;
	}

	@Override
	public String getDescription() {
		return "";
	}

	@Override
	public short getDeltaDurability() {
		// TODO Auto-generated method stub
		return 0;
	}
}
