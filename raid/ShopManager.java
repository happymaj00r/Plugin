package myvcrime.raid;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import myvcrime.Utility;
import myvcrime.mobs.Chief;
import myvcrime.mobs.Security;
import net.minecraft.server.v1_12_R1.Entity;

public class ShopManager {
	
	private Plugin plugin;
	private final String pathWaveShop;
	private final String pathShop;
	private ArrayList<RaidableShop> shopsList = new ArrayList<RaidableShop>();
	
	public ShopManager(Plugin plugin){
		this.plugin = plugin;
		pathWaveShop = "MyVCrime.Shops.Raidable";
		pathShop = "MyVCrime.Shops.Normal";
	}
	
	public void saveToConfig(RaidableShop shop){
		FileConfiguration config = plugin.getConfig();
		String shopName = shop.getName();
		boolean isWaveShop = shop.hasWave(0) || shop.hasWave(1);
		if(isWaveShop){
			config.createSection(pathWaveShop + "." + shopName);
			config.createSection(pathWaveShop + "." + shopName + ".Wave1" );
			config.createSection(pathWaveShop + "." + shopName + ".Wave2" );
			config.createSection(pathWaveShop + "." + shopName + ".Wave3" );
			config.createSection(pathWaveShop + "." + shopName + ".SpawnLocations");
			for(int i = 0; i < 3; i++){
				if(!shop.hasWave(i))
					continue;
				Set<Class<? extends net.minecraft.server.v1_12_R1.Entity>> entitySet = shop.getEntityList(i);
				Iterator<Class<? extends net.minecraft.server.v1_12_R1.Entity>> iterator = entitySet.iterator();
				while(iterator.hasNext()){
					Class<? extends net.minecraft.server.v1_12_R1.Entity> entity = iterator.next();
					config.set(pathWaveShop + "." + shopName + ".Wave" + i + "." + entity.getSimpleName(), shop.getWaveEntityAmount(i, entity));
				}
			}
			
			// Save the spawn locations of the shop to the config
			// serialize it first.
			ArrayList<String> serializedLocationList = new ArrayList<String>();
			for(int i = 0; i < 3; i++){
				if(shop.getSpawnLocations(i, 0) == null) continue;
				for(Location loc : shop.getSpawnLocations(i, 0)){
					serializedLocationList.add(Utility.serializeLocation(loc));
				}
			}		
			//now save it
			config.set(pathWaveShop + "." + shopName + ".SpawnLocations", serializedLocationList);
			
		} else {
			config.createSection(pathShop + "." + shopName);
		}
		
		plugin.saveConfig();
	}
		
	public ArrayList<Location> loadShopSpawnLocations(String shopName){
		String path = pathWaveShop + "." + shopName + "." + "SpawnLocations";
		FileConfiguration config = plugin.getConfig();
		if(config.contains(path)){
			ArrayList<String> stringLocationList = (ArrayList<String>) config.getList(path);
			ArrayList<Location> locationList = new ArrayList<Location>();
			for(String stringLoc : stringLocationList) {
				Location loc = Utility.deserializeLocation(stringLoc);
				locationList.add(loc);
			}
			return locationList;
		}
		return null;
	}
	
	public void loadRaidableShops(){
		FileConfiguration config = plugin.getConfig();
		if(!config.isConfigurationSection(this.pathWaveShop)){
			config.createSection(pathShop);
			config.createSection(pathWaveShop);
		}
		Set<String> keys = config.getConfigurationSection(this.pathWaveShop).getKeys(false);
		for(String s : keys){
			String shopName = s;

			RaidableShop shop = new RaidableShop(shopName,shopName,Bukkit.getWorld("Map"));
			shop.setName(shopName);
			this.shopsList.add(shop);
			Bukkit.broadcastMessage("--LOADING--");
			Bukkit.broadcastMessage("SHOPNAME: " + shop.getName());
			
			// load the spawn locations of the shop
			ArrayList<Location> locationList = this.loadShopSpawnLocations(s);		
			shop.addWaveSpawnLocations(0,0,locationList);
			
			for(int i = 0; i < 3 ; i++){
				
				if(!config.isConfigurationSection(pathWaveShop + "." + shopName + ".Wave" + i)){
					Bukkit.broadcastMessage("No section found");
					continue;
				}
				
				Set<String> waveKeys = config.getConfigurationSection(pathWaveShop + "." + shopName + ".Wave" + i).getKeys(false);
				Bukkit.broadcastMessage("WaveKeysIsEmpty: " + waveKeys.isEmpty());
				Iterator<String> iterator = waveKeys.iterator();
				while(iterator.hasNext()){
					String entity = iterator.next();
					int amount = config.getInt(pathWaveShop + "." + shopName + ".Wave" + i + "." + entity);
					if(getEntityClassBySimpleName(entity) != null){
						Class<? extends Entity> entityClazz = getEntityClassBySimpleName(entity);
						shop.addEntityToWave(i, (Class<? extends Entity>) entityClazz, amount);
						Bukkit.broadcastMessage("No Entity class found");
					} else {
						Bukkit.broadcastMessage("IS NULL ENTITY DUMB FUCKER!");
					}
				}
			}
		}
	}
	
	public Class<? extends Entity> getEntityClassBySimpleName(String simpleName){
		if(Chief.class.getSimpleName().equalsIgnoreCase(simpleName)){
			return (Class<? extends Entity>) Chief.class;
		}
		if(Security.class.getSimpleName().equalsIgnoreCase(simpleName)){
			return (Class<? extends Entity>) Security.class;
		}
		return null;
	}
	
	public void loadShops(){
		FileConfiguration config = plugin.getConfig();
		Set<String> keys = config.getConfigurationSection(this.pathWaveShop).getKeys(true);
		for(String s : keys){
			
		}
	}
	
	public RaidableShop getRaidableShop(String name){
		for(RaidableShop shop : shopsList){
			if(shop.getName().equalsIgnoreCase(name))
				return shop;
		}
		return null;
	}
}
