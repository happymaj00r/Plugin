package myvcrime.raid;

import java.util.ArrayList;

import org.bukkit.Bukkit;

import myvcrime.main;
import myvcrime.timer.ComebackTimer;
import myvcrime.timer.TimerCondition;
import myvcrime.timer.TimerRegionalCondition;
import net.minecraft.server.v1_12_R1.Entity;

public class PlayerOutOfCombatRaidState implements RaidState {

	private WaveRaid waveRaid;
	private RaidState oldState;
	private ArrayList<Entity> spawnedEntitiesList = new ArrayList<Entity>();
	private boolean isTimerRunning = false;
	private boolean isStateChanged = false;
	
	public PlayerOutOfCombatRaidState(WaveRaid waveRaid,ArrayList<Entity> spawnedEntitiesList, RaidState oldState){
		this.waveRaid = waveRaid;
		this.oldState = oldState;
		this.spawnedEntitiesList = spawnedEntitiesList;
	}
	
	@Override
	public void handle() {
		if(!checkForTransition()){
			if(isTimerRunning == false)
			{
				TimerCondition condition = new TimerRegionalCondition(waveRaid.getPlayer(),waveRaid.shop.getMainRegion().getId());
				isTimerRunning = true;
				new ComebackTimer(condition,80L,main.getPlugin()){

					@Override
					public void onTimeExpired() {
						if(!isStateChanged)
							waveRaid.setRaidState(new PlayerLostRaidState(waveRaid,spawnedEntitiesList));
					}

					@Override
					public void onConditionMet() {
						if(!isStateChanged)
							waveRaid.setRaidState(oldState);
					}
					
				}.showCountdown(true);
			}
		}
	}
	
	private boolean checkForTransition(){
		if(waveRaid.getPlayer().isDead()){
			waveRaid.setRaidState(new PlayerLostRaidState(waveRaid,spawnedEntitiesList));
			isStateChanged = true;
			return true;
		}
		return false;
	}
}
