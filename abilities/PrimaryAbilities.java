package myvcrime.abilities;

import org.bukkit.entity.Fireball;

import myvcrime.spells.effects.AbilityParticleEffect;
import myvcrime.spells.effects.ShadowDispersionEffect;
import myvcrime.spells.effects.ShadowWalkerEffect;
import myvcrime.spells.effects.SusanooFlameEffect;

public enum PrimaryAbilities implements Ability {
	//Name,Description,required Level,damage,cooldown(seconds),mana,bindable,abilityEffect
	FIRE_KEGEL("Fire", new String[]{"Sprays Fire"},1,1.0,1,0,true,(AbilityEffect) new CastParticleEffectAbilityEffect((AbilityParticleEffect)new SusanooFlameEffect(1.0))),
	FIREBALL("Fire", new String[]{"Sprays Fire"},1,1.0,1,0,true,(AbilityEffect) new CastProjectileAbilityEffect(Fireball.class)),
	POISINING("Poisining", new String[]{"Poisens the target"},1,1.0,1,0,true,(AbilityEffect) new CastPoisiningAbilityEffect()),
	SHADOW_DISPERSION("Shadow Dispersion", new String[]{"Cloaks and creates 3 fake shadows"},1,1.0,1,0,true,(AbilityEffect) new CastShadowDispersionEffectAbility(new ShadowDispersionEffect(3,150))),
	SHADOW_WALKER("Shadow Walker", new String[]{"Cloaks and creates 3 fake shadows"},1,1.0,1,0,true,(AbilityEffect) new CastParticleEffectAbilityEffect(new ShadowWalkerEffect(120)));
	private String name;
	private String[] description;
	private int requiredLevel;
	private double damage;
	private double cooldown;
	private double mana;
	private boolean bindable;
	private AbilityEffect abilityEffect;
	
	PrimaryAbilities(String name, String[] description, int requiredLevel, double damage, double cooldown, double mana, boolean bindable,AbilityEffect abilityEffect){
		this.name = name;
		this.description = description;
		this.requiredLevel = requiredLevel;
		this.damage = damage;
		this.cooldown = cooldown;
		this.mana = mana;
		this.bindable = bindable;
		this.abilityEffect = abilityEffect;
	}
	
	public String getName(){
		return name;
	}
	
	public AbilityEffect getAbilityEffect(){
		return abilityEffect;
	}
	
	public String[] getDescription(){
		return description;
	}
	
	public double getDamage(){
		return damage;
	}
	
	public boolean isBindabe(){
		return bindable;
	}
	
	public double getMana(){
		return mana;
	}
	
	public double getCooldown(){
		return cooldown;
	}
	
	public int getRequiredLevel(){
		return requiredLevel;
	}
}
