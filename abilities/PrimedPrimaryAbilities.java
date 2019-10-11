package myvcrime.abilities;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class PrimedPrimaryAbilities implements Listener {
	
	private HashMap<Superhuman,ArrayList<PrimaryAbilities>> playerAbilitiesMap = new HashMap<Superhuman,ArrayList<PrimaryAbilities>>();
	private Plugin plugin;
	
	public PrimedPrimaryAbilities(Plugin plugin){
		this.plugin = plugin;
	}
	
	public void primeAbility(Superhuman superhuman, PrimaryAbilities ability){
		AbilityEffect effect = ability.getAbilityEffect();
		if(effect instanceof CastProjectileAbilityEffect){
			CastProjectileAbilityEffect castProjectileAbilityEffect = (CastProjectileAbilityEffect) effect;
			castProjectileAbilityEffect.cast(superhuman);
		}
		if(effect instanceof CastParticleEffectAbilityEffect){
			CastParticleEffectAbilityEffect castParticleEffectAbilityEffect = (CastParticleEffectAbilityEffect) effect;
			castParticleEffectAbilityEffect.cast(superhuman);
		}
	}
	
	public void primeAbility(Superhuman superhuman, AbilityEffect abilityEffect, PrimaryAbilities ability){
		if(abilityEffect instanceof TargetableAbilityEffect){
			TargetableAbilityEffect targetableAbilityEffect = (TargetableAbilityEffect) abilityEffect;
		}
	}
	
	private void registerAbility(Superhuman superhuman, PrimaryAbilities ability){
		if(playerAbilitiesMap.containsKey(superhuman)){
			   ArrayList<PrimaryAbilities> abilityList = playerAbilitiesMap.get(superhuman);
			   abilityList.add(ability);
			   playerAbilitiesMap.put(superhuman, abilityList);
			} else {
			  ArrayList<PrimaryAbilities> abilityList = new ArrayList<PrimaryAbilities>();
			  abilityList.add(ability);
			  playerAbilitiesMap.put(superhuman, abilityList);
			}
	}	
	
	
}
