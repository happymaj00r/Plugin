package myvcrime;

import org.bukkit.Material;

public enum DrugRecipes {
	SPEED("Speed",new Material[] {Material.SUGAR});
	
	String name;
	Material[] recipes;
	
	DrugRecipes(String name, Material[] recipes){
		this.name = name;
		this.recipes = recipes;
	}
	
	public String getName(){
		return name;
	}
	
	public Material[] getRecipes(){
		return recipes;
	}
	
	public static DrugRecipes[] getDrugs(){
		DrugRecipes[] drugs = {SPEED};
		return drugs;
	}
}





