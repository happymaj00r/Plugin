package myvcrime.quests;

import org.bukkit.Material;
import org.bukkit.event.Event;

import myvcrime.customEvents.PlayerMinedEvent;

public class PlayerMinedQuestObjective implements QuestObjective {

	private Material mat;
	
	public PlayerMinedQuestObjective(Material mat){
		this.mat = mat;
	}
	
	@Override
	public boolean isMet(Event e) {
		if(!(e instanceof PlayerMinedEvent))
			return false;
		PlayerMinedEvent ev = (PlayerMinedEvent) e;
		if(ev.getOreType() == mat)
			return true;
		return false;
	}

	@Override
	public int getPoints(Event e) {
		if(isMet(e)){
			PlayerMinedEvent ev = (PlayerMinedEvent) e;
			return ev.getAmount();
		}
		return 0;
	}

}
