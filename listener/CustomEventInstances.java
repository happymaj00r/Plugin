package myvcrime.listener;

import org.bukkit.plugin.Plugin;

import myvcrime.Prison;

public class CustomEventInstances {
	
	
	public CustomEventInstances(Plugin plugin)
	{
		PrisonClass = new Prison(plugin);
	}
	
	public Prison PrisonClass;
}
