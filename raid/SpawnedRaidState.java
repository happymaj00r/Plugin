package myvcrime.raid;

import java.util.ArrayList;

import org.bukkit.Bukkit;

import myvcrime.Utility;
import net.minecraft.server.v1_12_R1.Entity;

public class SpawnedRaidState implements RaidState {

	WaveRaid waveRaid;
	private ArrayList<Entity> spawnedEntitiesList = new ArrayList<Entity>();
	
	public SpawnedRaidState(WaveRaid waveRaid,ArrayList<Entity> spawnedEntitiesList){
		this.waveRaid = waveRaid;
		this.spawnedEntitiesList = spawnedEntitiesList;
	}

	@Override
	public void handle() {
		checkForTransition();
	}

	private void checkForTransition(){
		if(!Utility.isInRegion(waveRaid.getPlayer().getLocation(), waveRaid.shop.getMainRegion().getId())){
			waveRaid.setRaidState(new PlayerOutOfCombatRaidState(waveRaid,spawnedEntitiesList,this));
			return;
		}
		if(waveRaid.getPlayer().isDead()){
			waveRaid.setRaidState(new PlayerLostRaidState(waveRaid,spawnedEntitiesList));
			return;
		}
		
		if(isCompleted()){
			waveRaid.setRaidState(new PlayerWonRaidState(waveRaid));
		}
		
	}
	
	private boolean isCompleted(){
		boolean allDead = true;
		for(Entity entity : spawnedEntitiesList){
			if(entity.isAlive()){
				allDead = false;
				break;
			}
		}
		if(allDead)
			return true;
		return false;
	}
}
