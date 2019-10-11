package myvcrime.quests;

import org.bukkit.event.Event;

import myvcrime.customEvents.PlayerRobEvent;

public class RobberyQuestObjective implements QuestObjective {

	@Override
	public boolean isMet(Event e) {
		if(e instanceof PlayerRobEvent){
			return true;
		}
		return false;
	}

	@Override
	public int getPoints(Event e) {
		return 1;
	}

}
