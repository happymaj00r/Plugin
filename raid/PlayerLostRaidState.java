package myvcrime.raid;

import java.util.ArrayList;

import org.bukkit.Bukkit;

import myvcrime.Utility;
import net.minecraft.server.v1_12_R1.Entity;

public class PlayerLostRaidState implements RaidState{

	private WaveRaid waveRaid;
	private ArrayList<Entity> spawnedEntitiesList = new ArrayList<Entity>();
	
	public PlayerLostRaidState(WaveRaid waveRaid,ArrayList<Entity> spawnedEntitiesList){
		this.waveRaid = waveRaid;
		this.spawnedEntitiesList = spawnedEntitiesList;
	}
	
	@Override
	public void handle() {
		despawnEntities();
		waveRaid.quit();
		Utility.sendTitle(waveRaid.getPlayer(), "You lost!", "Try again ;)");
	}
	
	private void despawnEntities(){
		for(Entity entity : spawnedEntitiesList){
			entity.killEntity();
		}
	}
}
