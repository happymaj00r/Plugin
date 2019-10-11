package myvcrime.raid;

import org.bukkit.World;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;

import myvcrime.CustomEntityZombie;
import net.minecraft.server.v1_12_R1.Entity;

public class EntityClassParser {
	
	private final net.minecraft.server.v1_12_R1.World nmsWorld;
	
	public EntityClassParser(World world){
		nmsWorld = ((CraftWorld) world).getHandle();
	}
	
	public Entity getEntityInstance(String entity){
		switch(entity){
			case "SMALL_COP":
				return new CustomEntityZombie(nmsWorld,false,CustomEntityZombie.Type.SMALL_COP);
			case "CHIEF":
				
				break;
			case "MIDDLE":
				
				break;
		}
		return null;
	}
	
	public Class<? extends Entity> getEntityClass(String entity){
		if(entity.equalsIgnoreCase(CustomEntityZombie.class.getSimpleName())){
			return CustomEntityZombie.class;
		}
		return null;
	}
}
