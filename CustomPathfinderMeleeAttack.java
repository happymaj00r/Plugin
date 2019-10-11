package myvcrime;

import org.bukkit.Bukkit;
import net.minecraft.server.v1_12_R1.Entity;
import net.minecraft.server.v1_12_R1.EntityCreature;
import net.minecraft.server.v1_12_R1.EntityHuman;
import net.minecraft.server.v1_12_R1.EntityLiving;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.PathEntity;
import net.minecraft.server.v1_12_R1.PathfinderGoalMeleeAttack;

public class CustomPathfinderMeleeAttack extends PathfinderGoalMeleeAttack{

	public CustomPathfinderMeleeAttack(EntityCreature arg0, double arg1, boolean arg2) {
		super(arg0, arg1, arg2);
		//Bukkit.broadcastMessage("CUSTOM INIT3");
	}
	
	PathEntity f;
	
	@Override
    public void d() {
        EntityLiving entityliving = this.b.getGoalTarget();
        String name = "loldodldoldold";
        if(entityliving instanceof EntityHuman){
        	name = entityliving.getName();
        }
        if (entityliving instanceof EntityHuman && (((EntityHuman) entityliving).isSpectator() || ((EntityHuman) entityliving).z()) || SpielerProfil.protectedPlayer.contains(name)) {
            this.b.setGoalTarget((EntityLiving) null);
            //Bukkit.broadcastMessage("DEEAGGROOOO:: " + name);
            // if(SpielerProfil.prisoner.contains(name))
            	//Bukkit.broadcastMessage("PLAYER IS PROTECTED!");
        }

        this.b.getNavigation().p();
    }
	
	@Override
    public boolean a() {
        EntityLiving entityliving = this.b.getGoalTarget();
        if(entityliving instanceof EntityPlayer){
        	     	
        	if(SpielerProfil.protectedPlayer.contains(entityliving.getName())){
            	this.d();
            	//Bukkit.broadcastMessage("IS PROTECTED!");
            	this.b.lastDamager = (EntityLiving) null;
            	this.b.exitCombat();
            	this.b.setGoalTarget((EntityLiving) null);
            	return false;
        	}
        }
        
        if (entityliving == null) {
            return false;
        } else if (!entityliving.isAlive()) {
            return false;
        } else {
        	this.f = this.b.getNavigation().a((Entity) entityliving);
            return this.f != null ? true : this.a(entityliving) >= this.b.d(entityliving.locX, entityliving.getBoundingBox().b, entityliving.locZ);
        }
    }
    //range
	@Override
    protected double a(EntityLiving entityliving) {
        return (double) (this.b.width * 2.0F * this.b.width * 2.0F * entityliving.width);
    }

}
