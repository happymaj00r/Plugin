package myvcrime.abilities;

import org.bukkit.entity.Projectile;

public abstract class ProjectileAbilityEffect implements AbilityEffect {
	
	protected Class<? extends Projectile> projectileClass;
	
	public ProjectileAbilityEffect(Class<? extends Projectile> projectileClass) {
		this.projectileClass = projectileClass;
	}
	
	public abstract void cast(Superhuman superhuman);
}
