package myvcrime.timer;

import org.bukkit.entity.Player;

import myvcrime.Utility;

public class TimerRegionalCondition implements TimerCondition {
	
	Player p;
	String region;
	
	public TimerRegionalCondition(Player p,String region){
		this.p = p;
		this.region = region;
	}
	
	@Override
	public boolean isMet() {
		return Utility.isInRegion(p.getLocation(), region);
	}

	@Override
	public Player getPlayer() {
		return p;
	}
}
