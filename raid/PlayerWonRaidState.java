package myvcrime.raid;

import org.bukkit.Bukkit;

import myvcrime.Utility;
import net.md_5.bungee.api.ChatColor;

public class PlayerWonRaidState implements RaidState {

	private WaveRaid waveRaid;
	
	public PlayerWonRaidState(WaveRaid waveRaid) {
		this.waveRaid = waveRaid;
	}

	@Override
	public void handle() {
		if(!waveRaid.shop.hasWave(waveRaid.currentWave + 1)){
			waveRaid.finished();
		} else {
			checkForTransition();
		}
	}
	
	private void checkForTransition(){
		if(waveRaid.shop.hasWave(waveRaid.currentWave + 1)){
			waveRaid.setRaidState(new SpawnRaidState(waveRaid,waveRaid.currentWave + 1));
			waveRaid.currentWave++;
			Utility.sendTitle(waveRaid.getPlayer(), ChatColor.GREEN + "Completed", "Wave " + waveRaid.currentWave + " starts!");
			return;
		}
	}

}
