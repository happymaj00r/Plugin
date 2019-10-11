package myvcrime.abilities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import myvcrime.main;
import myvcrime.spells.effects.AbilityParticleEffect;

public class CastShadowDispersionEffectAbility extends CastParticleEffectAbilityEffect {

	public CastShadowDispersionEffectAbility(AbilityParticleEffect effect) {
		super(effect);
	}
	
	@Override
	public void cast(Superhuman superhuman){
		effect.play(superhuman.getPlayer());
		Player p = superhuman.getPlayer();
		
		p.hidePlayer(p);
		PotionEffect invisibleEffect = new PotionEffect(PotionEffectType.INVISIBILITY, 2000, 2000);
		p.addPotionEffect(invisibleEffect);
		Bukkit.broadcastMessage("INVISIBLE");
		new BukkitRunnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				p.showPlayer(p);
			}
			
		}.runTaskTimer(main.plugin, 0, 150);
	}
}
