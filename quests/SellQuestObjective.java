package myvcrime.quests;

import org.bukkit.Material;
import org.bukkit.event.Event;

import myvcrime.customEvents.PlayerSoldEvent;

public class SellQuestObjective implements QuestObjective{
	
	Material mat;
	
	public SellQuestObjective(Material mat){
		this.mat = mat;
	}
	
	public boolean isMet(Event e){
		if(!(e instanceof PlayerSoldEvent))
			return false;
		PlayerSoldEvent ev = (PlayerSoldEvent) e;
		if(ev.getItemType() == mat){
			return true;
		}
		return false;
	}

	@Override
	public int getPoints(Event e) {
		if(isMet(e)){
			PlayerSoldEvent ev = (PlayerSoldEvent) e;
			return ev.getAmount();
		}
		return 0;
	}
}
