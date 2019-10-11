package myvcrime.spells.effects;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class EffectUtils {
	public static Vector rotateEffect(Vector v, Location loc)
	{
		double yaw = loc.getYaw()/180.0*Math.PI;
		double pitch = loc.getPitch()/180.0*Math.PI;
		
		v = rotateAboutX(v, pitch);
		v = rotateAboutY(v, -yaw);
		return v;
	}
	
	public static Vector rotateAboutY(Vector v, double yaw) 
	{
		double X = Math.cos(yaw) * v.getX() + Math.sin(yaw) * v.getZ();
		double Z = -Math.sin(yaw) * v.getX() + Math.cos(yaw) * v.getZ();
		v.setX(X);
		v.setZ(Z);
		return v;
	}

	public static Vector rotateAboutX(Vector v, double pitch)
	{
		double Y = v.getY()*Math.cos(pitch) - Math.sin(pitch) * v.getZ();
		double Z = v.getY()*Math.sin(pitch) + Math.cos(pitch)*v.getZ();
		v.setY(Y);
		v.setZ(Z);
		return v;
	}
	public static Vector rotateAboutZ(Vector v, double g)
	{
		double X = v.getX()*Math.cos(g) - Math.sin(g) * v.getY();
		double Y = v.getX()*Math.sin(g) + Math.cos(g) * v.getY();
		v.setX(X);
		v.setY(Y);
		return v;
	}
}
