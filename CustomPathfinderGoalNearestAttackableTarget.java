package myvcrime;

import org.bukkit.Bukkit;

import net.minecraft.server.v1_12_R1.EntityCreature;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.PathfinderGoalNearestAttackableTarget;



public class CustomPathfinderGoalNearestAttackableTarget extends PathfinderGoalNearestAttackableTarget{

	public CustomPathfinderGoalNearestAttackableTarget(EntityCreature entitycreature, Class oclass, boolean flag) {
		super(entitycreature, oclass, flag);
	}
	@Override
	 public void c() {
		if(d instanceof EntityPlayer){
			String name = d.getName();
			if(SpielerProfil.protectedPlayer.contains(name)){
				this.e.setGoalTarget(null);
				this.d = null;
				
				return;
			}
			int crime = SpielerProfil.getCrime(name);
			if(crime >= 50 && !SpielerProfil.protectedPlayer.contains(name)){
			     this.e.setGoalTarget(this.d, d instanceof EntityPlayer ? org.bukkit.event.entity.EntityTargetEvent.TargetReason.CLOSEST_PLAYER : org.bukkit.event.entity.EntityTargetEvent.TargetReason.CLOSEST_ENTITY, true); // Craftbukkit - reason			
			} else {
			}
		}
    }
}
