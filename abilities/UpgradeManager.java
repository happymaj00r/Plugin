package myvcrime.abilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import myvcrime.CustomItemType;
import myvcrime.CustomItems;
import myvcrime.SpielerProfil;
import myvcrime.Utility;
import myvcrime.main;
import myvcrime.customGUI.InfoButton;
import myvcrime.customGUI.InventoryMenu;
import myvcrime.customGUI.UpgradeButton;
import myvcrime.items.ItemBuilder;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.plugins.Economy_iConomy6;
import net.minecraft.server.v1_12_R1.EntityHuman;

public class UpgradeManager {
	
	private static HashMap<Superhuman,HashMap<UpgradeType,ArrayList<AbilityItems>>> activatedAbilitiesMap = new HashMap<Superhuman,HashMap<UpgradeType,ArrayList<AbilityItems>>>();
	private static HashMap<Superhuman,ArrayList<AbilityItems>> boughtAbilityItemsMap = new HashMap<Superhuman,ArrayList<AbilityItems>>();
	private static final String PATH = "UpgradeManager";
	
	
	public UpgradeManager(){
		
	}
	
	public static void saveToConfig(){
		FileConfiguration config = main.plugin.getConfig();
		Set<Superhuman> superhumanList = boughtAbilityItemsMap.keySet();
		for(Superhuman superhuman : superhumanList){
			if(boughtAbilityItemsMap.containsKey(superhuman)){
				ArrayList<AbilityItems> abilityItemsList = boughtAbilityItemsMap.get(superhuman);
				ArrayList<String> stringAbilityItemsList = new ArrayList<String>();
				for(AbilityItems abilityItems : abilityItemsList){
					stringAbilityItemsList.add(abilityItems.toString());
					Bukkit.broadcastMessage(abilityItems.toString());
				}
				config.set(PATH + "." + superhuman.getPlayer().getName() + ".Bought" , stringAbilityItemsList);
			}
		}
		
		superhumanList = activatedAbilitiesMap.keySet();
		Bukkit.broadcastMessage("empty?:" + superhumanList.isEmpty());
		Bukkit.broadcastMessage(activatedAbilitiesMap.size() + " size");
		Superhuman su = SuperhumanManager.getSuperhuman(Bukkit.getPlayer("HappyMajorr"));
		Bukkit.broadcastMessage("contains:" + hasUpgrade(su,AbilityItems.BAISY_UPGRADE_LV1));
		for(Superhuman superhuman : superhumanList){
			Bukkit.broadcastMessage(superhuman.getPlayer().getName());
			HashMap<UpgradeType,ArrayList<AbilityItems>> upgradeTypeListMap = activatedAbilitiesMap.get(superhuman);
			Set<UpgradeType> upgradeTypeSet = upgradeTypeListMap.keySet();
			ArrayList<String> stringAbilityItemsList = new ArrayList<String>();
			for(UpgradeType upgradeType : upgradeTypeSet){
				ArrayList<AbilityItems> abilityItemsList = upgradeTypeListMap.get(upgradeType);
				Bukkit.broadcastMessage(upgradeType.toString());
				for(AbilityItems abilityItem : abilityItemsList){
					stringAbilityItemsList.add(abilityItem.toString());
					Bukkit.broadcastMessage(abilityItem.toString());
				}
			}
			config.set(PATH + "." + superhuman.getPlayer().getName() + ".Active", stringAbilityItemsList);
		}
		main.plugin.saveConfig();
	}
	
	
	public static void loadFromConfig(){
		FileConfiguration config = main.plugin.getConfig();
		
		if(config.contains(PATH)){
			Set<String> keys = config.getConfigurationSection(PATH).getKeys(false);
			for(String playerName : keys){
				Player p = Bukkit.getPlayer(playerName);
				if(p == null) continue;
				Superhuman superhuman = SuperhumanManager.getSuperhuman(p);
				if(superhuman == null) continue;
				List<String> boughtAbilityItems = config.getStringList(PATH + "." + playerName + ".Bought");
				List<String> activeAbilityItems = config.getStringList(PATH + "." + playerName + ".Active");
				
				for(String boughtAbility : boughtAbilityItems){
					AbilityItems abilityItem = AbilityItems.valueOf(boughtAbility);
					if(abilityItem == null) continue;
					buyAbilityItemFree(superhuman,abilityItem);
				}
				
				for(String activeAbility : activeAbilityItems){
					AbilityItems abilityItem = AbilityItems.valueOf(activeAbility);
					if(abilityItem == null) continue;
					setUpgrade(superhuman,abilityItem);
				}
			}
		}
		main.plugin.saveConfig();
	}
	
	public static void setUpgrade(Superhuman superhuman, AbilityItems abilityItem){
		if(canUpgrade(superhuman,abilityItem)){
			if(activatedAbilitiesMap.containsKey(superhuman)){
				HashMap<UpgradeType, ArrayList<AbilityItems>> abilityItemsMap = activatedAbilitiesMap.get(superhuman);
				if(!abilityItemsMap.containsKey(abilityItem.getUpgradeType())){
					ArrayList<AbilityItems> abilityItemsList = new ArrayList<AbilityItems>();
					abilityItemsList.add(abilityItem);
					abilityItemsMap.put(abilityItem.getUpgradeType(), abilityItemsList);
					activatedAbilitiesMap.put(superhuman, abilityItemsMap);
				} else {
					ArrayList<AbilityItems> abilityItemsList = abilityItemsMap.get(abilityItem.getUpgradeType());
					abilityItemsList.clear();
					abilityItemsList.add(abilityItem);
					abilityItemsMap.put(abilityItem.getUpgradeType(), abilityItemsList);
					activatedAbilitiesMap.put(superhuman, abilityItemsMap);
				}
			} else {
				HashMap<UpgradeType, ArrayList<AbilityItems>> abilityItemsMap = new HashMap<UpgradeType, ArrayList<AbilityItems>>();
				ArrayList<AbilityItems> abilityItemsList = new ArrayList<AbilityItems>();
				abilityItemsList.add(abilityItem);
				abilityItemsMap.put(abilityItem.getUpgradeType(), abilityItemsList);
				activatedAbilitiesMap.put(superhuman, abilityItemsMap);
			}
		} 
	}
	
	public static UpgradeType getUpgradeTypeByItem(ItemStack item){
		Material mat = item.getType();
		switch(mat){
		case WOOD_SWORD:
			return UpgradeType.BAISY;
		case STONE_SWORD:
			return UpgradeType.KNIFE;
		case IRON_SWORD:
			return UpgradeType.MACHETE;
		case GOLD_SWORD:
			return UpgradeType.CLEAVER;
		case DIAMOND_SWORD:
			return UpgradeType.KATANA;
		}
		return null;
	}
	
	public static ArrayList<AbilityItems> getActiveUpgrades(Superhuman superhuman,UpgradeType type){
		Bukkit.broadcastMessage("LEVEL0");
		if(activatedAbilitiesMap.containsKey(superhuman)){
			HashMap<UpgradeType, ArrayList<AbilityItems>> abilityItemsMap = activatedAbilitiesMap.get(superhuman);
			Bukkit.broadcastMessage("LEVEL1");
			if(abilityItemsMap.containsKey(type)){
				Bukkit.broadcastMessage("LEVEL2");
				ArrayList<AbilityItems> abilityItemsList = abilityItemsMap.get(type);
				return abilityItemsList;
			}
		}
		return null;
	}
	
	public static boolean hasUpgrade(Superhuman superhuman,AbilityItems abilityItem){
		if(activatedAbilitiesMap.containsKey(superhuman)){
			HashMap<UpgradeType,ArrayList<AbilityItems>> upgradeTypeAbilityItemsListMap = activatedAbilitiesMap.get(superhuman);
			if(upgradeTypeAbilityItemsListMap.containsKey(abilityItem.getUpgradeType())){
				ArrayList<AbilityItems> abilityItemsList = upgradeTypeAbilityItemsListMap.get(abilityItem.getUpgradeType());
				if(abilityItemsList.contains(abilityItem)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean isBought(Superhuman superhuman, AbilityItems abilityItem){
		if(boughtAbilityItemsMap.containsKey(superhuman)){
			ArrayList<AbilityItems> abilityItemsList = boughtAbilityItemsMap.get(superhuman);
			if(abilityItemsList.contains(abilityItem)) return true;
		}
		return false;
	}
	
	public static void buyAbilityItem(Superhuman superhuman, AbilityItems abilityItem){
		if(SpielerProfil.getMoney(superhuman.getPlayer()) < abilityItem.getPrice()) return;
		if(superhuman.getLevel() < abilityItem.getRequiredLevel()) return;
		if(boughtAbilityItemsMap.containsKey(superhuman)){
			ArrayList<AbilityItems> abilityItemsList = boughtAbilityItemsMap.get(superhuman);
			abilityItemsList.add(abilityItem);
			boughtAbilityItemsMap.put(superhuman, abilityItemsList);
			Utility.sendTitle(superhuman.getPlayer(), "Congratulation", ChatColor.GREEN + abilityItem.getName() + " bought!");
			SpielerProfil.decreaseMoney(superhuman.getPlayer(), abilityItem.getPrice());
		} else {
			ArrayList<AbilityItems> abilityItemsList = new ArrayList<AbilityItems>();
			abilityItemsList.add(abilityItem);
			boughtAbilityItemsMap.put(superhuman, abilityItemsList);
			Utility.sendTitle(superhuman.getPlayer(), "Congratulation", ChatColor.GREEN + abilityItem.getName() + " bought!");
			SpielerProfil.decreaseMoney(superhuman.getPlayer(), abilityItem.getPrice());
		}
	}
	
	public static void buyAbilityItemFree(Superhuman superhuman, AbilityItems abilityItem){
		if(boughtAbilityItemsMap.containsKey(superhuman)){
			ArrayList<AbilityItems> abilityItemsList = boughtAbilityItemsMap.get(superhuman);
			abilityItemsList.add(abilityItem);
			boughtAbilityItemsMap.put(superhuman, abilityItemsList);
		} else {
			ArrayList<AbilityItems> abilityItemsList = new ArrayList<AbilityItems>();
			abilityItemsList.add(abilityItem);
			boughtAbilityItemsMap.put(superhuman, abilityItemsList);
		}
	}
	
	private static ArrayList<AbilityItems> getValidAbilityItems(Superhuman superhuman){
		ArrayList<AbilityItems> validAbilityItemsList = new ArrayList<AbilityItems>();
		int level = superhuman.getLevel();
		for(AbilityItems abilityItem : AbilityItems.values()){
			if(!(abilityItem.getRequiredLevel() == level)) continue;
			validAbilityItemsList.add(abilityItem);
		}
		return validAbilityItemsList;
	}
	
	public static InventoryMenu getUpgradeInventory(Superhuman superhuman){
		ArrayList<AbilityItems> validAbilityItems = getValidAbilityItems(superhuman);
		Bukkit.broadcastMessage(validAbilityItems.isEmpty() + " ");
		Player p = superhuman.getPlayer();
		EntityHuman entityHuman = (EntityHuman)((CraftPlayer) p).getHandle();
		InventoryMenu inventoryMenu = new InventoryMenu(entityHuman);	
		InventoryMenu secondPage = new InventoryMenu(entityHuman);
		InventoryMenu thirdPage = new InventoryMenu(entityHuman);
		
		ArrayList<AbilityItems> knifeUpgrades = arrayToList(AbilityItems.knifeUpgrades());
		ArrayList<AbilityItems> baisyUpgrades = arrayToList(AbilityItems.baisyUpgrades());
		ArrayList<AbilityItems> cleaverUpgrades = arrayToList(AbilityItems.knifeUpgrades());
		ArrayList<AbilityItems> macheteUpgrades = arrayToList(AbilityItems.macheteUpgrades());
		ArrayList<AbilityItems> katanaUpgrades = arrayToList(AbilityItems.knifeUpgrades());
		
		/*
		for(AbilityItems abilityItem : validAbilityItems){
			if(abilityItem.getUpgradeType() == UpgradeType.BAISY){
				baisyUpgrades.add(abilityItem);
			} else if (abilityItem.getUpgradeType() == UpgradeType.KNIFE) {
				knifeUpgrades.add(abilityItem);
			} else if (abilityItem.getUpgradeType() == UpgradeType.MACHETE) {
				macheteUpgrades.add(abilityItem);
			} else if (abilityItem.getUpgradeType() == UpgradeType.CLEAVER) {
				cleaverUpgrades.add(abilityItem);
			} else if (abilityItem.getUpgradeType() == UpgradeType.KATANA) {
				katanaUpgrades.add(abilityItem);
			}
		}
		*/
		
		inventoryMenu.addButton(new InfoButton(CustomItems.getCustomItem(CustomItemType.BAISY)), 28);
		inventoryMenu.addButton(new InfoButton(CustomItems.getCustomItem(CustomItemType.KNIFE)), 29);
		inventoryMenu.addButton(new InfoButton(CustomItems.getCustomItem(CustomItemType.MACHETE)), 30);
		
		inventoryMenu.addButton(new InfoButton(CustomItems.getCustomItem(CustomItemType.CLEAVER)), 31);
		inventoryMenu.addButton(new InfoButton(CustomItems.getCustomItem(CustomItemType.KATANA)), 32);
		fillRowVertically(superhuman,baisyUpgrades,inventoryMenu, 1);
		fillRowVertically(superhuman,knifeUpgrades,inventoryMenu, 2);
		fillRowVertically(superhuman,macheteUpgrades,inventoryMenu, 3);
		fillRowVertically(superhuman,cleaverUpgrades,inventoryMenu, 4);
		fillRowVertically(superhuman,katanaUpgrades,inventoryMenu, 5);
		
		return inventoryMenu;
	}
	
	private static ArrayList<AbilityItems> arrayToList(AbilityItems[] abilityItems){
		ArrayList<AbilityItems> abilityItemsList = new ArrayList<AbilityItems>();
		for(AbilityItems abilityItem : abilityItems){
			abilityItemsList.add(abilityItem);
		}
		return abilityItemsList;
	}
	
	private static ArrayList<String> arrayToList(String[] abilityItems){
		ArrayList<String> abilityItemsList = new ArrayList<String>();
		for(String abilityItem : abilityItems){
			abilityItemsList.add(abilityItem);
		}
		return abilityItemsList;
	}
	
	private static boolean canUpgrade(Superhuman superhuman, AbilityItems abilityItem){
		if(isBought(superhuman,abilityItem)){
			return true;
		}
		return false;
	}
	
	private static void fillRowVertically(Superhuman superhuman,ArrayList<AbilityItems> abilityItems, InventoryMenu menu,int rowIndex){
		final int columnLength = 4;
		final int rowLength = 9;
		
		int currentIndex = 0;
		Iterator<AbilityItems> iterator = abilityItems.iterator();
		int counter = 0;
		for(int i = 0; i < columnLength - 1; i++){
			for(int j = 0; j < rowLength; j++){
				if(j == rowIndex){
					if(iterator.hasNext()){
						AbilityItems abilityItem = iterator.next();
						String state = isBought(superhuman,abilityItem) ? ChatColor.GREEN + "USEABLE" : ChatColor.RED + "LOCKED";
						Material mat = hasUpgrade(superhuman,abilityItem) ? Material.ENCHANTED_BOOK : abilityItem.getMaterial();
						ItemStack is = new ItemBuilder(mat).name(abilityItem.getName())
								.amount(1)
								.lore(ChatColor.DARK_AQUA + "Price:" + ChatColor.YELLOW + " " + abilityItem.getPrice())
								.lore(ChatColor.DARK_AQUA + "Level:" + ChatColor.YELLOW + " " + abilityItem.getRequiredLevel())
								.lore(state)
								.make();
						menu.addButton(new UpgradeButton(is, abilityItem), counter);

					}
				}
				counter++;
			}
		}
	}
}
