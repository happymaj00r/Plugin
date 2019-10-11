package myvcrime.abilities;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.plugin.Plugin;

public class PrimedPassiveAbilities {
	private HashMap<Superhuman,ArrayList<PassiveAbilities>> playerAbilitiesMap = new HashMap<Superhuman,ArrayList<PassiveAbilities>>();
	private Plugin plugin;
	
	public PrimedPassiveAbilities(Plugin plugin){
		this.plugin = plugin;
	}
	
	public void primeAbility(Superhuman superhuman, PassiveAbilities ability){
		registerAbility(superhuman,ability);
	}
	
	private void registerAbility(Superhuman superhuman, PassiveAbilities ability){
		if(playerAbilitiesMap.containsKey(superhuman)){
			   ArrayList<PassiveAbilities> abilityList = playerAbilitiesMap.get(superhuman);
			   abilityList.add(ability);
			   playerAbilitiesMap.put(superhuman, abilityList);
			} else {
			  ArrayList<PassiveAbilities> abilityList = new ArrayList<PassiveAbilities>();
			  abilityList.add(ability);
			  playerAbilitiesMap.put(superhuman, abilityList);
			}
	}
}
