package myvcrime.NPE;

import org.bukkit.event.Listener;

public interface Stage extends Listener {
	public void start();
	public boolean hasStarted();
	public boolean isPaused();
	public boolean hasFinished();
}
