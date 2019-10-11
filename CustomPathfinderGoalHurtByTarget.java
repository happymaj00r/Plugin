package myvcrime;

import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;

import net.minecraft.server.v1_12_R1.AxisAlignedBB;
import net.minecraft.server.v1_12_R1.EntityCreature;
import net.minecraft.server.v1_12_R1.EntityLiving;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.EntityTameableAnimal;
import net.minecraft.server.v1_12_R1.PathfinderGoalTarget;

public class CustomPathfinderGoalHurtByTarget extends PathfinderGoalTarget {

    private final boolean a;
    private int b;
    private final Class<?>[] c;

    public CustomPathfinderGoalHurtByTarget(EntityCreature entitycreature, boolean flag, Class<?>... aclass) {
        super(entitycreature, true);
        this.a = flag;
        this.c = aclass;
        this.a(1);
    }

    public boolean a() {
        int i = this.e.bT();
        EntityLiving entityliving = this.e.getLastDamager();

        return i != this.b && entityliving != null && this.a(entityliving, false);
    }

    public void c() {
    	//Bukkit.broadcastMessage("METHOD C");
    	if(this.e.getGoalTarget() instanceof EntityPlayer){
    		//Bukkit.broadcastMessage("STOP");
    		this.e.setGoalTarget((EntityLiving) null);
    	} else {
        this.e.setGoalTarget(this.e.getLastDamager());
        this.g = this.e.getGoalTarget();
        this.b = this.e.bT();
        this.h = 300;
        if (this.a) {
            this.f();
        }
    	}
        super.c();
    }

    protected void f() {
        double d0 = this.i();
        List list = this.e.world.a(this.e.getClass(), (new AxisAlignedBB(this.e.locX, this.e.locY, this.e.locZ, this.e.locX + 1.0D, this.e.locY + 1.0D, this.e.locZ + 1.0D)).grow(d0, 10.0D, d0));
        Iterator iterator = list.iterator();

        while (iterator.hasNext()) {
            EntityCreature entitycreature = (EntityCreature) iterator.next();

            if (this.e != entitycreature && entitycreature.getGoalTarget() == null && (!(this.e instanceof EntityTameableAnimal) || ((EntityTameableAnimal) this.e).getOwner() == ((EntityTameableAnimal) entitycreature).getOwner()) && !entitycreature.r(this.e.getLastDamager())) {
                boolean flag = false;
                Class[] aclass = this.c;
                int i = aclass.length;

                for (int j = 0; j < i; ++j) {
                    Class oclass = aclass[j];

                    if (entitycreature.getClass() == oclass) {
                        flag = true;
                        break;
                    }
                }

                if (!flag) {
                    this.a(entitycreature, this.e.getLastDamager());
                }
            }
        }

    }

    protected void a(EntityCreature entitycreature, EntityLiving entityliving) {
        entitycreature.setGoalTarget(entityliving);
    }
}