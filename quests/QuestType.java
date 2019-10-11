package myvcrime.quests;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import myvcrime.ChestType;
import myvcrime.LootChest;

public enum QuestType {
	//name,description,Loot,amount of needes points,level needed, points for each
	STARTER("Copkiller","Kill 10 Cops!",new ItemStack[]{
			new ItemStack(Material.STICK),
			new ItemStack(Material.DIAMOND),
			LootChest.createChest(ChestType.DAILY)
			},10,new KillQuestObjective(EntityType.ZOMBIE),100,1),
	
	TUT1("Dailychest","> Open a daily chest pack.",
			new ItemStack[]{
			new ItemStack(Material.DIAMOND),
			LootChest.createChest(ChestType.DAILY)
	},1,new PlayerOpenedChestQuestObjective(ChestType.DAILY),0,1),
			
	TUT2("Get Cash!","> Trade nuggets for cash in the bank", 
			new ItemStack[]{
			new ItemStack(Material.DIAMOND),
			LootChest.createChest(ChestType.DAILY)
	},40,new SellQuestObjective(Material.GOLD_NUGGET),0,1),
	
	TUT3("GarbageCollecter","> Collect 64 bottles from trashcans.", 
			new ItemStack[]{
			new ItemStack(Material.DIAMOND),
			LootChest.createChest(ChestType.DAILY)
	},64,new PickupQuestObjective(Material.GLASS_BOTTLE),0,1),
	
	TUT4("Golddigger","> Mine 16 Goldnuggets", 
			new ItemStack[]{
			new ItemStack(Material.DIAMOND),
			LootChest.createChest(ChestType.DAILY)
	},16,new PlayerMinedQuestObjective(Material.GOLD_ORE),0,1),
	TUT5("Robbery","> Rob a shop!", 
			new ItemStack[]{
			new ItemStack(Material.DIAMOND),
			LootChest.createChest(ChestType.DAILY)
	},1,new RobberyQuestObjective(),0,1),
	TUT6("dsds","> Rob a shop!", 
			new ItemStack[]{
			new ItemStack(Material.DIAMOND),
			LootChest.createChest(ChestType.DAILY)
	},10,new PlayerMinedQuestObjective(Material.GOLD_NUGGET),10,30);
	
	
	
	ItemStack[] loot;
	String name;
	int lvl;
	String description;
	QuestObjective objective;
	double price;
	int points;
	
	QuestType(String name,String description,ItemStack[] loot, int points,QuestObjective objective,double price,int lvl){
		this.name = name;
		this.loot = loot;
		this.description = description;
		this.points = points;
		this.objective = objective;
		this.price = price;
		this.lvl = lvl;
	}
	
	public int getNeededPoints(){
		return points;
	}
	
	public int getLevel(){
		return lvl;
	}
	
	public double getPrice(){
		return price;
	}
	
	public QuestObjective getObjective(){
		return objective;
	}
	
	public String getName(){
		return name;
	}
	
	public static QuestType[] getPickupQuests(){
		QuestType[] quests = {TUT3,TUT4};
		return quests;
	}
	
	public ItemStack[] getLoot(){
		return loot;
	}
	
	public String getDescription(){
		return description;
	}
	
	public static QuestType[] getQuests(){
		QuestType[] quests = {STARTER,TUT1,TUT2,TUT3,TUT4,TUT5};
		return quests;
	} 
}
