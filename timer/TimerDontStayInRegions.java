package myvcrime.timer;

import org.bukkit.entity.Player;

import myvcrime.Utility;

public class TimerDontStayInRegions implements TimerCondition {
	
	Player p;
	String[] region;
	
	public TimerDontStayInRegions(Player p,String region[]){
		this.p = p;
		this.region = region;
	}
	
	@Override
	public boolean isMet() {
		for(String s : region){
			if(Utility.isInRegion(p.getLocation(), s))
				return false;
		}
		return true;
	}

	@Override
	public Player getPlayer() {
		return p;
	}
}