package myvcrime.quests;

import org.bukkit.event.Event;

public interface QuestObjective {
	public boolean isMet(Event e);
	public int getPoints(Event e);
}
