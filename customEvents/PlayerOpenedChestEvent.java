package myvcrime.customEvents;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import myvcrime.ChestType;
import myvcrime.CustomItemType;
import myvcrime.CustomItems;

public class PlayerOpenedChestEvent extends Event implements Cancellable {
	
	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled;
	
	private ChestType type;
	private Player p;
	
	public PlayerOpenedChestEvent(Player p, ChestType type){
		this.p = p;
		this.type = type;
		this.cancelled = false;
	}
	
	public Player getPlayer(){
		return p;
	}
	
	public ChestType getChestType(){
		return type;
	}
	
	public ItemStack getItemStack(){
		ItemStack item = CustomItems.getCustomItem(CustomItemType.DAILYCHEST);
		return item;
	}

	@Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return cancelled;
	}

	@Override
	public void setCancelled(boolean arg0) {
		this.cancelled = arg0;
	}

	@Override
	public HandlerList getHandlers() {
		// TODO Auto-generated method stub
		return handlers;
	}
	
	public static HandlerList getHandlerList(){
		return handlers;
	}
	
}
