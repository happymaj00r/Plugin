package myvcrime.quests;

import org.bukkit.event.Event;

import myvcrime.ChestType;
import myvcrime.customEvents.PlayerOpenedChestEvent;

public class PlayerOpenedChestQuestObjective implements QuestObjective {
	
	private ChestType type;
	
	public PlayerOpenedChestQuestObjective(ChestType type){
		this.type = type;
	}

	@Override
	public boolean isMet(Event e) {
		if(!(e instanceof PlayerOpenedChestEvent))
			return false;
		PlayerOpenedChestEvent ev = (PlayerOpenedChestEvent) e;
		if(ev.getChestType() == type)
			return true;
		return false;
	}

	@Override
	public int getPoints(Event e) {
		if(isMet(e)){
			return 1;
		}
		return 0;
	}
}
