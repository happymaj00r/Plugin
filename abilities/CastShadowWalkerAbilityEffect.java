package myvcrime.abilities;

import myvcrime.spells.effects.AbilityParticleEffect;

public class CastShadowWalkerAbilityEffect extends CastParticleEffectAbilityEffect {

	public CastShadowWalkerAbilityEffect(AbilityParticleEffect effect) {
		super(effect);
	}
	
	@Override
	public void cast(Superhuman superhuman){
		effect.play(superhuman.getPlayer());
	}
}
