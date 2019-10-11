package myvcrime.spells.effects;

import org.bukkit.Location;

public abstract class EffectHandler {
	public abstract void onTick(Location[] particleLoc);
	public abstract void onEnd();
	public void cancel(){};
}
