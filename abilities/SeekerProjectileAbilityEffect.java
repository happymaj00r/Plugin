package myvcrime.abilities;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;

public abstract class SeekerProjectileAbilityEffect implements AbilityEffect {
	
	private Class<? extends Projectile> projectileClass;
	
	public SeekerProjectileAbilityEffect(Class<? extends Projectile> projectileClass) {
		this.projectileClass = projectileClass;
	}
	
	public abstract void cast(Superhuman superhuman, Entity entity);
	
}
