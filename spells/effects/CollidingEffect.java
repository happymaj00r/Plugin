package myvcrime.spells.effects;

import java.util.List;

import org.bukkit.entity.Entity;

public abstract class CollidingEffect extends EffectHandler {
	public abstract void onCollision(List<Entity> entityList);
}
