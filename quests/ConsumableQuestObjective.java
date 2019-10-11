package myvcrime.quests;

import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import myvcrime.customEvents.PlayerConsumableEvent;

public class ConsumableQuestObjective implements QuestObjective {
	
	ItemStack objectiveItem;
	
	public ConsumableQuestObjective(ItemStack objectiveItem){
		this.objectiveItem = objectiveItem;
	}
	
	@Override
	public boolean isMet(Event e){
		if(!(e instanceof PlayerConsumableEvent))
			return false;
		
		PlayerConsumableEvent ev = (PlayerConsumableEvent) e;
		if(ev.getItem() == objectiveItem)
			return true;
		
		return false;
	}

	@Override
	public int getPoints(Event e) {
		if(isMet(e)){
			PlayerConsumableEvent ev = (PlayerConsumableEvent) e;
			return ev.getAmount();
		}
		return 0;
	}
}
