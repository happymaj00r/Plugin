package myvcrime.customEvents;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class PlayerPickupBottlesEvent extends Event implements Cancellable {

	private boolean cancelled;
	private int amount;
	private Player p;
	private ItemStack item;
	private static final HandlerList handlers = new HandlerList();
	
	public PlayerPickupBottlesEvent(Player p,int amount,ItemStack item){
		this.amount = amount;
		this.p = p;
		this.item = item;
	}
	
	@Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return cancelled;
	}
	
	public Player getPlayer(){
		return p;
	}
	
	public int getAmount(){
		return amount;
	}
	
	public ItemStack getItem(){
		return item;
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
