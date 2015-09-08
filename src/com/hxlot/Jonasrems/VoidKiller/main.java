package com.hxlot.Jonasrems.VoidKiller;

import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener{
	
	public static Logger log = Bukkit.getLogger();
	public HashMap<Entity,Entity> lastPlayerDamagePlayer = new HashMap<Entity,Entity>();
	public static main plugin;
	
	public static void out(String string){
		log.info("[Void Killer] " + string);
	}
	
	
	public void onEnable()
	{
		out("Plugin enabled!");
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);
		plugin = this;
	}
	
	public void onDisable()
	{
		out("Plugin disabled!");
	}
	
	@EventHandler
	public void PDP(EntityDamageByEntityEvent e){
		Entity Attacked = e.getEntity();
		Entity Attacker = e.getDamager();
		lastPlayerDamagePlayer.put(Attacked, Attacker);
	}
	
	@EventHandler
	public void onDamage(EntityDamageByBlockEvent e){
		if(e.getCause() == DamageCause.VOID){
			e.setCancelled(true);
		if(e.getEntityType() == EntityType.PLAYER){
			Player p = (Player) e.getEntity();
			if(lastPlayerDamagePlayer.containsKey(p)){
				p.damage(1000, lastPlayerDamagePlayer.get(p));
			}else{
				p.damage(1000);
			}
		}
		}
	}
	
	@EventHandler
	public void onDeath(EntityDeathEvent e){
		lastPlayerDamagePlayer.remove(e.getEntity());
	}
	
}
