package myvcrime;

import net.minecraft.server.v1_12_R1.EntityLiving;

public interface Cop {
	public String getCopType();
	public boolean isRaidSpawn();
	public Long getSpawnTime();
	public boolean isEngaged();
	public String getRaidTarget();
	public void setAt(String name);
	public String getAt();
	public void setAt();
	void setRaidTarget(EntityLiving livingPlayer);
}
