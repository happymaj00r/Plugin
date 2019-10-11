package myvcrime.listener;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import myvcrime.NPE.TrainingZone;

public class TrainingZoneEvents implements Listener {
	
	private ArrayList<TrainingZone> trainingZoneList = new ArrayList<TrainingZone>();
	
	public void addTrainingZone(TrainingZone zone){
		trainingZoneList.add(zone);
	}
	
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent e){
		if(!trainingZoneList.isEmpty()){
			if(e.getEntity() instanceof Player){
				Player p = (Player) e.getEntity();
				for(TrainingZone zone : trainingZoneList){
					if(zone.getPlayers().contains(p)){
						e.setCancelled(true);
					}
				}
			}
		}
	}
	
	
}
