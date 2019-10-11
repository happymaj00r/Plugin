package myvcrime.world;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.sk89q.worldedit.BlockVector;

import myvcrime.listener.Jobs;
import net.minecraft.server.v1_12_R1.Entity;
import net.minecraft.server.v1_12_R1.World;

public class RandomSpawner extends BukkitRunnable {

	private Class<? extends Entity> type;
	private BlockVector max;
	private BlockVector min;
	private World world;
	private int limit;
	private String region;
	
	private ArrayList<Entity> entityList = new ArrayList<Entity>();
	
	public RandomSpawner(Plugin plugin,Class<? extends Entity> type,Long delta,BlockVector max,BlockVector min, World world, int limit,String region){
		this.type = type;
		this.runTaskTimer(plugin, 0L, delta);
		this.max = max;
		this.min = min;
		this.world = world;
		this.limit = limit;
		this.region = region;
	}
	
	public String getRegion(){
		return region;
	}
	
	@Override
	public void run() {		
		Integer randX = Jobs.zufall(min.getBlockX(),max.getBlockX());
		Integer randY = Jobs.zufall(min.getBlockY(), max.getBlockY());
		Integer randZ = Jobs.zufall(min.getBlockZ(), max.getBlockZ());
		if(entityList.size() > limit){
			ArrayList<Entity> newList = new ArrayList<Entity>();
			for(Entity entity : entityList){
				if(entity == null){
					continue;
				}
				if(entity.isAlive() == false){
					continue;
				}
				newList.add(entity);
			}
			entityList = newList;
			return;
		} else {
		try {
			Constructor<? extends Entity> constr = type.getConstructor(World.class);
			try {
				Entity entity = constr.newInstance(world);
				entity.setPosition(randX.doubleValue(), randY.doubleValue(), randZ.doubleValue());
				world.addEntity(entity);
				entityList.add(entity);
				entity.addScoreboardTag("pluginSpawn");
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		}
	}
}
