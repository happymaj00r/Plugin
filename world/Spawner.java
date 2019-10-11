package myvcrime.world;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import net.minecraft.server.v1_12_R1.Entity;

public class Spawner {
	
	private ProtectedRegion region;
	private World world;
	private net.minecraft.server.v1_12_R1.World nmsWorld;
	
	public Spawner(World world){
		init(world);
	}

	public void init(World world){
		this.world = world;
		this.nmsWorld = ((CraftWorld)world).getHandle();
	}
	
	public Entity spawn(Class<? extends Entity> clazz,Location loc) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Constructor<? extends Entity> constructor = clazz.getConstructor(net.minecraft.server.v1_12_R1.World.class);
		Entity entity = constructor.newInstance(nmsWorld);
		entity.setPosition(loc.getX(),loc.getY(), loc.getZ());
		Block block = world.getBlockAt(loc);
		if(block != null){
			if(block.getType() != Material.AIR){
				Bukkit.broadcastMessage("[ERROR] Entity spawned in block: " + block.getType().toString());
			}
		}
		nmsWorld.addEntity(entity);
		return entity;
	}
}
