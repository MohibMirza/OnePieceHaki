package me.KingFrozo.onepiecehaki.mobs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Rabbit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class KillerBunny implements CommandExecutor, Listener {

 @Override
 public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

  if (!(sender instanceof Player)) {
   sender.sendMessage("Only players may use this command.");
   return false;
  }
  Player player = (Player) sender;
  Rabbit killer = (Rabbit) player.getWorld().spawnEntity(player.getLocation(), EntityType.RABBIT);
  killer.setRabbitType(Rabbit.Type.THE_KILLER_BUNNY);
  killer.setCustomName("The Killer Bunny");
  killer.setMaxHealth(30);
  killer.setHealth(30);

  return false;
 }

 @EventHandler
 public void onKillerBunnyDeath(EntityDeathEvent ev) {
  if (ev.getEntityType() == EntityType.RABBIT) {
   Rabbit killerbunny = (Rabbit) ev.getEntity();
   String killercn = killerbunny.getCustomName();
   Player killer = killerbunny.getKiller();
   String killern = killerbunny.getKiller().getName();
   if (killerbunny.getRabbitType() == Rabbit.Type.THE_KILLER_BUNNY) {
    if (killerbunny.getMaxHealth() == 30) {
     if (!(killer instanceof Player)) {
      int x = 22;
      int y = 34;
      int z = 23;
      World world = Bukkit.getWorld("PVPAREA");
      Location loc = new Location(world, x, y, z);

      Rabbit killerbunny1 = (Rabbit) killer.getWorld().spawnEntity(loc, EntityType.RABBIT);
      killerbunny1.setRabbitType(Rabbit.Type.THE_KILLER_BUNNY);
      killerbunny1.setMaxHealth(30);
      killerbunny1.setHealth(30);
     } else {

      Bukkit.broadcastMessage(ChatColor.GREEN + "" + killern + ChatColor.GOLD + " has slain "
        + ChatColor.RED + "The_Killer_Bunny");
      killer.sendMessage(
        ChatColor.GRAY + "The_Killer_Bunny » I will get my revenge on you, " + killern + "!");
      int x = 22;
      int y = 34;
      int z = 23;
      World world = Bukkit.getWorld("PVPAREA");
      Location loc = new Location(world, x, y, z);

      Rabbit killerbunny1 = (Rabbit) killer.getWorld().spawnEntity(loc, EntityType.RABBIT);
      killerbunny1.setRabbitType(Rabbit.Type.THE_KILLER_BUNNY);
      killerbunny1.setMaxHealth(30);
      killerbunny1.setHealth(30);
     }
    }
   }

  }

 }
}
