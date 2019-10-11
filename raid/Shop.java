package myvcrime.raid;

import java.util.ArrayList;

import org.bukkit.World;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import myvcrime.Utility;

public class Shop {
	
	private String name;
	private World world;
	private ProtectedRegion protectedMainRegion;
	private ArrayList<String> regionList = new ArrayList<String>();
	
	public Shop(String name, String region, World world){	
		this.name = name;
		this.world = world;
		this.regionList.add(region);
		protectedMainRegion = getWGRegion(region);
	}
	
	public String getName(){
		return name;
	}
	
	private ProtectedRegion getWGRegion(String name){
		ProtectedRegion rg = null;
		if(Utility.getWorldGuard1() == null)
			return null;
		if(Utility.getWorldGuard1().getRegionManager(world).hasRegion(name)){			
			rg = Utility.getWorldGuard1().getRegionManager(world).getRegion(name);
		}
		return rg;
	}
	public ProtectedRegion getMainRegion(){
		return protectedMainRegion;
	}
	
	public boolean hasRegion(String regionName){
		if(regionList.contains(regionName)) return true;
		return false;
	}
	
	public ProtectedRegion getRegionOf(String regionName){
		return this.getWGRegion(regionName);
	}
	
	public ArrayList<String> getParts(){
		return regionList;
	}
	
	public boolean isPlayerInShop(Player p){
		if(Utility.getWorldGuard1() == null)
			return false;
		if(Utility.isInRegions(p.getLocation(), this.regionList))
			return true;
		return false;
	}
	
	public void addPart(String regionName){
		if(Utility.getWorldGuard1() == null)
			return;
		if(Utility.getWorldGuard1().getRegionManager(world).hasRegion(regionName)){
			if(!regionList.contains(regionName)){
				regionList.add(regionName);
			}
		}
	}
}
