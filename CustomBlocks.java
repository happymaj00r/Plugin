package myvcrime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

public class CustomBlocks {
	private ArrayList<Location> placedBlockLocations = new ArrayList<Location>();
	private HashMap<Location, Long> placedBlockTime = new HashMap<Location, Long>();
	private boolean isCycleRunning = false;
	private Long delay = 60000L;
	main plugin;
	
	public CustomBlocks(main plugin){
		this.plugin = plugin;
	}
	
	public void createTempBlock(Location loc, Material type){
		if(!placedBlockLocations.contains(loc)){
			loc.getBlock().setType(type);
			Collections.reverse(placedBlockLocations);
			placedBlockLocations.add(loc);
			Collections.reverse(placedBlockLocations);
			placedBlockTime.put(loc, System.currentTimeMillis());
			if(!isCycleRunning){
				startCycle();
			}
		}
	}
	
	public void removeTempBlock(Location loc){
		if(placedBlockLocations.contains(loc)){
			placedBlockLocations.remove(loc);
			placedBlockTime.remove(loc);
			loc.getBlock().setType(Material.AIR);
		}
	}
	
	public void startCycle(){
		if(!placedBlockLocations.isEmpty()){
			final Location loc = placedBlockLocations.get(placedBlockLocations.size()-1);
			Long time = placedBlockTime.get(loc);
			isCycleRunning = true;
			if(time + delay > System.currentTimeMillis()){
				Long diff = (time + delay) - System.currentTimeMillis();
				Long ticks = diff/50;
				new BukkitRunnable(){
					public void run(){
						removeTempBlock(loc);
						startCycle();
					}
				}.runTaskLater(plugin, ticks);
			} else {
				removeTempBlock(loc);
				startCycle();
			}
		} else {
			isCycleRunning = false;
		}
	}
	
	public boolean isSchedulerActive(){
		return isCycleRunning;
	}
}
