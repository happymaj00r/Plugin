package myvcrime.timer;

import org.bukkit.entity.Player;

public interface TimerCondition {
	public boolean isMet();
	public Player getPlayer();
}
