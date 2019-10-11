package myvcrime.abilities;

import org.bukkit.entity.Player;

import myvcrime.main;

public class AbilityExecutor {
	
	public AbilityExecutor(){}
	
	public void executeAbility(Player p, Ability ability){
		Superhuman superhuman = SuperhumanManager.getSuperhuman(p);
		if(superhuman == null) return;
		if(superhuman.getMana() < ability.getMana()) return;
		if(!superhuman.canCast(ability)) return;

		
		if(ability instanceof PassiveAbilities){
			executePassiveAbility(superhuman, (PassiveAbilities) ability);
		} else if (ability instanceof PrimaryAbilities){
			executePrimaryAbility(superhuman, (PrimaryAbilities) ability);
		}
	}
	
	private void executePassiveAbility(Superhuman superhuman, PassiveAbilities ability){
		if(!superhuman.canCast(ability)) return;
		
		superhuman.decreaseMana(ability.getMana());
		superhuman.onCastedAbility(ability);
	}
	
	private void executePrimaryAbility(Superhuman superhuman, PrimaryAbilities ability){
		if(!superhuman.canCast(ability)) return;
		
		superhuman.decreaseMana(ability.getMana());
		superhuman.onCastedAbility(ability);
		PrimedPrimaryAbilities primedPrimaryAbilities = new PrimedPrimaryAbilities(main.plugin);
		primedPrimaryAbilities.primeAbility(superhuman, ability);
	}
	
}
