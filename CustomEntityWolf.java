package myvcrime;

import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

import net.minecraft.server.v1_12_R1.EntityLiving;
import net.minecraft.server.v1_12_R1.EntityWolf;
import net.minecraft.server.v1_12_R1.World;



public class CustomEntityWolf extends EntityWolf implements Cop{

	private EntityLiving owner;
	String at;
	boolean raid;
	
	public CustomEntityWolf(World world, boolean raid) {
		super(world);
		this.raid = raid;
		this.setSize(0.6F, 0.85F);
		this.setTamed(true);
	}
	
	public CustomEntityWolf(World world) {
		super(world);
		this.raid = false;
		this.setSize(0.6F, 0.85F);
		this.setTamed(true);
	}
	
	@Override
    public EntityLiving getOwner(){
		super.getOwner();
		if(owner != null) {
		return owner;
		} else {
		return null;
		}
	}
	public void setOwner(EntityLiving entity){
		owner = entity;
	}
	public void setAt(String name){
		at = name;
	}

	@Override
	public String getCopType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isRaidSpawn() {
		// TODO Auto-generated method stub
		return raid;
	}

	@Override
	public Long getSpawnTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEngaged() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getAt() {
		// TODO Auto-generated method stub
		return at;
	}

	@Override
	public void setRaidTarget(EntityLiving livingPlayer) {
		this.setGoalTarget(livingPlayer, TargetReason.CLOSEST_PLAYER, false);
	}

	@Override
	public String getRaidTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAt() {
		// TODO Auto-generated method stub
		
	}
}
