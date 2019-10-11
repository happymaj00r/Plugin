package myvcrime.potionEffects;

import org.bukkit.Color;
import org.bukkit.potion.PotionEffectType;

public enum CustomPotionEffectType {
	
	HANDSCHELLEN(new PotionEffectType(2){

		@Override
		public Color getColor() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public double getDurationModifier() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return "Handschellen";
		}

		@Override
		public boolean isInstant() {
			// TODO Auto-generated method stub
			return true;
		}
		
	});
	
	PotionEffectType type;
	
	CustomPotionEffectType(PotionEffectType type){
		this.type = type;
	}
	
	public PotionEffectType getType(){
		return type;
	}
}
