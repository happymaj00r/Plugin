package myvcrime.abilities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import myvcrime.main;
import myvcrime.spells.effects.AbilityParticleEffect;

public class CastParticleEffectAbilityEffect implements AbilityEffect {
	
	protected AbilityParticleEffect effect;
	
	public CastParticleEffectAbilityEffect(AbilityParticleEffect effect) {
		this.effect = effect;
	}

	public void cast(Superhuman superhuman) {
		Player p = superhuman.getPlayer();
		Bukkit.getServer().getPluginManager().registerEvents((Listener) effect, main.plugin);
		effect.play(p);
	}
}
