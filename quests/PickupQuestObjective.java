package myvcrime.quests;

import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import myvcrime.customEvents.PlayerPickupBottlesEvent;

public class PickupQuestObjective implements QuestObjective {
	
	private ItemStack item = null;
	private Material mat = null;
	
	public PickupQuestObjective(ItemStack item){
		this.item = item;
	}
	
	public PickupQuestObjective(Material mat){
		this.mat = mat;
	}
	
	@Override
	public boolean isMet(Event e){
		if(!(e instanceof PlayerPickupBottlesEvent))
			return false;
		PlayerPickupBottlesEvent ev = (PlayerPickupBottlesEvent) e;
		if(item != null)
			if(ev.getItem() == item)
				return true;
		if(mat != null)
			if(mat == ev.getItem().getType())
				return true;
		return false;
	}

	@Override
	public int getPoints(Event e) {
		if(isMet(e)){
			PlayerPickupBottlesEvent ev = (PlayerPickupBottlesEvent) e;
			return ev.getAmount();
		}
		return 0;
	}
	
}
