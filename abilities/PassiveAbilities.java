package myvcrime.abilities;

public enum PassiveAbilities implements Ability{
	//Name,Description,required Level,damage,cooldown(seconds),mana,bindOn
	FIRE_KEGEL("Fire", new String[]{"Sprays Fire"},1,1.0,1,10,"Katana"),
	SHADOW_WALKER_TRIGGER("Shadow Walker Trigger", new String[]{"Trigger"},1,1.0,1,10,"");
	
	private String name;
	private String[] description;
	private int requiredLevel;
	private double damage;
	private double cooldown;
	private double mana;
	private String bindOn;
	
	PassiveAbilities(String name, String[] description, int requiredLevel, double damage, double cooldown, double mana, String bindOn){
		this.name = name;
		this.description = description;
		this.requiredLevel = requiredLevel;
		this.damage = damage;
		this.cooldown = cooldown;
		this.mana = mana;
		this.bindOn = bindOn;
	}
	
	public String getName(){
		return name;
	}
	
	public String[] getDescription(){
		return description;
	}
	
	public double getDamage(){
		return damage;
	}
	
	public String bindOn(){
		return bindOn;
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