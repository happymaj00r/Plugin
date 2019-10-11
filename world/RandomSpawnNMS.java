package myvcrime.world;

import java.util.ArrayList;

import org.bukkit.plugin.Plugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import myvcrime.Utility;
import net.minecraft.server.v1_12_R1.Entity;
import net.minecraft.server.v1_12_R1.World;

public class RandomSpawnNMS {
	
	private Plugin plugin;
	private org.bukkit.World world;
	private World nmsWorld;
	
	private static RandomSpawnNMS instance = null;
	private ArrayList<RandomSpawner> spawnerList = new ArrayList<RandomSpawner>();
	
	private RandomSpawnNMS(Plugin plugin,org.bukkit.World world,World nmsWorld){
		this.plugin = plugin;
		this.world = world;
		this.nmsWorld = nmsWorld;
	}
	
	public static RandomSpawnNMS getInstance(Plugin plugin,org.bukkit.World world,World nmsWorld){
		if(instance == null){
			instance = new RandomSpawnNMS(plugin,world,nmsWorld);
		}
		return instance;
	}
	
	public ArrayList<RandomSpawner> getSpawnerList(){
		return spawnerList;
	}
	
	public void createRandomSpawnInRegion(Class<? extends Entity> clazz,String region,int limit){	
		WorldGuardPlugin guard = Utility.getWorldGuard1();
		ProtectedRegion rg = guard.getRegionManager(world).getRegion(region);
		if(rg != null){
			RandomSpawner spawner = new RandomSpawner(plugin,clazz,20L,rg.getMaximumPoint(),rg.getMinimumPoint(),nmsWorld,limit,region);
			spawnerList.add(spawner);
		}
	}
}
