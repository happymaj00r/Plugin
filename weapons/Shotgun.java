package myvcrime.weapons;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Shotgun extends Weapon implements UseableWeapon {
	
	private long lastUse;
	private final int COOLDOWN = 40; //milli seconds

	public Shotgun(ItemStack item) {
		super(item);
	}

	@Override
	public void use(Player p) {
		this.lastUse = System.currentTimeMillis();
	}
	
	@Override
	protected void shoot(){	
	}

	@Override
	public boolean canShoot(Player p) {
		if(lastUse + COOLDOWN < System.currentTimeMillis()){
			return true;
		}
		return false;
	}
}
