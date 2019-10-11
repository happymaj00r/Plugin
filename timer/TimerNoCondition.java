package myvcrime.timer;

import org.bukkit.entity.Player;

public class TimerNoCondition implements TimerCondition{

	private Player p;
	
	public TimerNoCondition(Player p){
		this.p = p;
	}
	
	@Override
	public boolean isMet() {
		return false;
	}

	@Override
	public Player getPlayer() {
		return p;
	}

}
