package myvcrime.abilities;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CastPoisiningAbilityEffect extends TargetableAbilityEffect {
	
	@Override
	public void cast(Superhuman superhuman,LivingEntity targetEntity) {
		Player p = superhuman.getPlayer();
		PotionEffect potionEffect = new PotionEffect(PotionEffectType.POISON,1,60,true);
		targetEntity.addPotionEffect(potionEffect);
	}
	
}
