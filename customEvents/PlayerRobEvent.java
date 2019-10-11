package myvcrime.customEvents;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import myvcrime.raid.RaidableShop;
import myvcrime.raid.WaveRaid;

public class PlayerRobEvent extends Event implements Cancellable {
	private Player p;
	private RaidableShop shop;
	private WaveRaid raid;
	private boolean cancelled;
	
	private static final HandlerList handlers = new HandlerList();
	
	public PlayerRobEvent(Player p,RaidableShop shop, WaveRaid raid){
		this.p = p;
		this.shop = shop;
		this.raid = raid;
	}
	
	public Player getPlayer(){
		return p;
	}
	
	public RaidableShop getShop(){
		return shop;
	}
	
	public WaveRaid getRaid(){
		return raid;
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
