package myvcrime.abilities;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class Superhuman {
	
	private HashMap<Ability,Long> lastUseAbilityMap = new HashMap<Ability,Long>();
	private Player p;
	private double mana;
	private int level;
	private double experience;
	
	private Superhuman(Player p){
		this.p = p;
	}
	
	protected static Superhuman instance(Player p){
		return new Superhuman(p);
	}
	
	public Player getPlayer(){
		return p;
	}
	
	public double getMana(){
		return mana;
	}
	
	public void addMana(double mana){
		this.mana+=mana;
	}
	
	public int getLevel(){
		return level;
	}
	
	public void setLevel(int level){
		this.level = level;
	}
	
	public double getExperience(){
		return this.experience;
	}
	
	public boolean canCast(PassiveAbilities ability){
		return true;
	}
	
	public boolean canCast(PrimaryAbilities ability){
		return true;
	}
	
	public void decreaseMana(double amount){
		this.mana-=amount;
	}
	
	public boolean canCast(Ability ability){
		if(!this.isAbilityOnCooldown(ability)) return true;
		return false;
	}
	
	public void addExperience(double amount){
		this.experience+=amount;
	}
	
	public void setExperience(double experience){
		this.experience = experience;
	}
	
	private Long getLastUse(Ability ability){
		if(lastUseAbilityMap.containsKey(ability)){
			return lastUseAbilityMap.get(ability);
		} else {
			return 0L;
		}
	}
	
	private boolean isAbilityOnCooldown(Ability ability){
		Long lastUsePlusCooldown = (long) (this.getLastUse(ability) + ability.getCooldown() * 1000);
		if(lastUsePlusCooldown < System.currentTimeMillis()){
			return false;
		} else {
			return true;
		}
	}
	
	public void onCastedAbility(Ability ability){
		Long currentTime = System.currentTimeMillis();
		this.lastUseAbilityMap.put(ability, currentTime);
	}
}
