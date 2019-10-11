package myvcrime.abilities;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;

public class CastProjectileAbilityEffect extends ProjectileAbilityEffect {

	public CastProjectileAbilityEffect(Class<? extends Projectile> projectileClass) {
		super(projectileClass);
	}

	@Override
	public void cast(Superhuman superhuman) {
		Player p = superhuman.getPlayer();
		p.launchProjectile(projectileClass);
	}
}
