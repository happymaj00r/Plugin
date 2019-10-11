package myvcrime.quests;

import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;

public class KillQuestObjective implements QuestObjective {
	
	EntityType entityType;
	boolean finished = false;
	
	public KillQuestObjective(EntityType entityType){
		this.entityType = entityType;
	}
	
	@Override
	public boolean isMet(Event e){
		if(!(e instanceof EntityDeathEvent))
			return false;
		
		EntityDeathEvent ev = (EntityDeathEvent) e;
		if(ev.getEntity().getType() == entityType)
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
