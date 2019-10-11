package myvcrime;

public enum CustomItemType {
	DAILYCHEST(ChestType.DAILY),
	P99(WeaponsType.P99),
	RPG(WeaponsType.RPG),
	REVOLVER(WeaponsType.REVOLVER),
	LMG(WeaponsType.LMG),
	MINIGUN(WeaponsType.MINIGUN),
	BOLTCUTTER(ToolsType.BOLTCUTTER),
	FREIBRIEF(CustomConsumableType.FREIBRIEF),
	MACHETE(WeaponsType.MACHETE),
	BAISY(WeaponsType.BAISY),
	CLEAVER(WeaponsType.CLEAVER),
	KATANA(WeaponsType.KATANA),
	KNIFE(WeaponsType.KNIFE)
	;
		
	CustomItemsInterface c;
	
	CustomItemType(CustomItemsInterface c){
		this.c = c;
	}
	
	public CustomItemsInterface getType(){
		return c;
	}
	
	public static CustomItemType[] getCustomTypes(){
		CustomItemType[] re = {KNIFE,DAILYCHEST,P99,RPG,REVOLVER,LMG,MINIGUN,BOLTCUTTER,MACHETE,BAISY,CLEAVER,KATANA};
		return re;
	}
}
