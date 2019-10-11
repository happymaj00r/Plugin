package myvcrime.weapons;

import org.bukkit.entity.Player;

public interface UseableWeapon {
	public void use(Player p);
	public boolean canShoot(Player p);
}
