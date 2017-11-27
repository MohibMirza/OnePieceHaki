package me.KingFrozo.onepiecehaki;

import me.KingFrozo.onepiecehaki.commands.Stats;
import me.KingFrozo.onepiecehaki.events.Join;
import me.KingFrozo.onepiecehaki.mobs.KillerBunny;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInventoryEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.FileUtil;
import org.bukkit.util.Vector;

public class Main
  extends JavaPlugin
  implements Listener, CommandExecutor
{
	public List<String> drown = new ArrayList();
	public int[] armamentHakiLevels = {200,400,800,1600,3200,6400,12800,25600,51200,124000};
    int dodge;
    public Inventory inv;
    
  public void onEnable()
  {
    getCommand("stats").setExecutor(this);
    getCommand("killerbunny").setExecutor(new KillerBunny());

    PluginManager pm = Bukkit.getPluginManager();
    pm.registerEvents(new KillerBunny(), this);
    Bukkit.getPluginManager().registerEvents(new Join(), this);
    Bukkit.getPluginManager().registerEvents(this, this);
  
  }
  
  public void onDisable() {
	  drown.clear();
  }
  
  
  @EventHandler
  public void onJoin(PlayerJoinEvent e)
  {
    Player p = e.getPlayer();
    createFile(p);
    p.sendMessage("Welcome!");
    }
  
  @EventHandler
  public void observationDodge(EntityDamageByEntityEvent e) {
   if ((e.getEntity() instanceof Player) && (e.getDamager() instanceof Player)) {
     Player p = (Player) e.getDamager();
     Player d = (Player) e.getEntity();
     dodge = (int) (Math.random()*101);
     if(dodge <= getObservationPercent(d.getName())){
         e.setCancelled(true);
         d.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.RED + "OPGC" + ChatColor.DARK_GRAY + "]" + ChatColor.BLUE + " You have dodged an attack from " + p.getName() + " using observation haki!");
         p.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.RED + "OPGC" + ChatColor.DARK_GRAY + "]" + ChatColor.RED + " *Attack Dodged*");
     }
   }else if(e.getEntity() instanceof Player){
	     Entity damager = e.getDamager();
	     Player observationist = (Player) e.getEntity();
	     int dodge = (int) (Math.random() * 101);
	     if(dodge <= getObservationPercent(observationist.getName())){
	         e.setCancelled(true);
	         observationist.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.RED + "OPGC" + ChatColor.DARK_GRAY + "]" + ChatColor.BLUE + " You have dodged an attack from a " + damager.getType() + " using observation haki!");
   }
   }
   
   }
  
  @EventHandler
  public void playerDeath(PlayerDeathEvent e) {
      Player player = (Player) e.getEntity();   
      Player killer = player.getKiller();
      if (player.getKiller() != null) { 
          Bukkit.broadcastMessage(ChatColor.GREEN + killer.getName() + ChatColor.DARK_GRAY + " has killed " + ChatColor.GREEN + player.getName());
          for (PotionEffect effect : player.getActivePotionEffects())
              player.removePotionEffect(effect.getType());
      e.setKeepInventory(true);
   
          addDeath(player.getName());
          addKill(killer.getName());
          }else{
        	  addDeath(player.getName());
          }
      if(getClass(killer.getName()).equalsIgnoreCase("Brawler")){
    	  ItemStack item = killer.getInventory().getItemInHand();
    	  if((item == null) || (item.getTypeId() == 0)){
    		  addArmamentHakiXP(killer.getName(), 20);
    	  }
      }
      }
  
  @EventHandler
  public void onDrown(PlayerMoveEvent e) {
   Player drowner = (Player) e.getPlayer();
   Material to = e.getTo().getBlock().getType();
   if (drowner.hasPermission("onepiece.drown")) {
    if ((to != Material.WATER) && (to != Material.STATIONARY_WATER)) {
    } else {
     drowner.setVelocity(new Vector(0, -2, 0));
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 2), true);
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 2), true);
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 200, 2), true);
     if (!(this.drown.contains(drowner.getName()))) {
      drown.add(drowner.getName());
     }

    }

   }

  }

  @EventHandler
  public void onPlayerMove(final PlayerMoveEvent event) {
   Player player = event.getPlayer();
   Material to = event.getTo().getBlock().getType();
   if (this.drown.contains(player.getName())) {
    if (to != Material.AIR) {
     if (drown.contains(player.getName())) {
      if (player.hasPermission("onepiece.drown")) {
       Vector dir = player.getLocation().getDirection().normalize().multiply(0);
       Vector vec = new Vector(dir.getX(), dir.getY(), dir.getZ());
       player.setVelocity(vec);
       drown.remove(player.getName());
      }
     }
    }
   }
  }
  
  @EventHandler
  public void armamentBoost(EntityDamageByEntityEvent e){
	  if(e.getEntity() instanceof Player){
	  Player damager = (Player) e.getDamager();
	  ItemStack item = damager.getInventory().getItemInHand();
	  
	  if(getClass(damager.getName()).equalsIgnoreCase("Brawler")){
	  if((item == null) || (item.getTypeId() == 0)){
		  addArmamentHakiXP(damager.getName(), 1);
	  }
	  }else if(getClass(damager.getName()).equalsIgnoreCase("Slasher")){
			  if((item.getTypeId() == 276) || (item.getTypeId() == 267) || (item.getTypeId() == 283) || (item.getTypeId() == 272) || (item.getTypeId() == 268)){
				  addArmamentHakiXP(damager.getName(), 1);
			  }
		  
	  }
	  armamentLevelUpManager(damager.getName());
	  }
  } 
  
  @EventHandler
  public void noSteal(InventoryClickEvent e){
	  Inventory inv1 = (Inventory) e.getInventory();
	  if(inv != null && inv1.getName().equals(inv.getName())){
		  e.setCancelled(true);
	  }
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
	  Player p = (Player) sender;
	  if(label.equalsIgnoreCase("stats")){
    if (args.length == 0)
    {
    openStatsGUI(p);
    
    }else if(args.length == 1){
    	Player playerStat = (Player) Bukkit.getPlayer(args[0]);
    	if(playerStat != null){
    		openStatsGUIOtherPlayer(playerStat, p);
    	}else{
    		p.sendMessage(ChatColor.RED + args[0] + " is not a valid player.");
    	}
    }
	return false;
      
	  }
	  
	if(label.equalsIgnoreCase("setClass")){
		if(sender.hasPermission("onepiece.admin")){
		if(args.length < 2){
			sender.sendMessage(ChatColor.RED + "/setclass <Username> <Class>");
		}else{
			//if(Bukkit.getPlayer(args[0]) != null){
				if(args[1].equalsIgnoreCase("Brawler")){
					setClass(args[0], args[1]);
					sender.sendMessage(ChatColor.GREEN + "Class set.");
				}else if(args[1].equalsIgnoreCase("Slasher")){
					setClass(args[0], args[1]);
					sender.sendMessage(ChatColor.GREEN + "Class set.");
				}else{
					sender.sendMessage(ChatColor.RED + "Invalid class. Choose Brawler or Slasher.");
					setClass(args[0], "Invalid Class (Contact an admin)");
				}
			/*}else{
				sender.sendMessage("Player is not online or invalid.");
			}*/
		}
		}
	}
	if(label.equals("setlevel")){
		if(sender.hasPermission("onepiece.admin")){
		if(args.length < 2){
	sender.sendMessage(ChatColor.RED + "/setlevel <haki> <level>");
		}else{
			if(args[0].equals("armament")){
				
			}else if(args[0].equalsIgnoreCase("")){
				
			}
		}
		}
	}
	return false;
  }
  
  public void openStatsGUI(Player p){
	  inv = Bukkit.createInventory(null, 27, ChatColor.DARK_GRAY + "Stats");
	  List<String> armamentLore = new ArrayList();
	  armamentLore.add(ChatColor.BLUE + "Level: "+ ChatColor.GREEN + getArmamentHakiLevel(p.getName()));
	  armamentLore.add(ChatColor.BLUE + "XP: " + ChatColor.GREEN + getArmamentHakiXP(p.getName()));
	  armamentLore.add(ChatColor.BLUE + "XP Needed: " + ChatColor.GREEN +  ((armamentHakiLevels[getArmamentHakiLevel(p.getName()) + 1])- (getArmamentHakiXP(p.getName()))));
	  
	  List<String> observationLore = new ArrayList();
	  observationLore.add(ChatColor.BLUE + "Level: "+ ChatColor.GREEN + getObservationHakiLevel(p.getName()));
	  observationLore.add(ChatColor.BLUE + "XP: " + ChatColor.GREEN + getObservationHakiXP(p.getName()));
	  observationLore.add(ChatColor.BLUE + "XP Needed: " + ChatColor.GREEN +  ((armamentHakiLevels[getObservationHakiLevel(p.getName()) + 1])- (getObservationHakiXP(p.getName()))));
	  
	  List<String> conquerorLore = new ArrayList();
	  conquerorLore.add(ChatColor.BLUE + "Level: "+ ChatColor.GREEN + getConquerorHakiLevel(p.getName()));
	  conquerorLore.add(ChatColor.BLUE + "XP: " + ChatColor.GREEN + getConquerorHakiXP(p.getName()));
	  conquerorLore.add(ChatColor.BLUE + "XP Needed: " + ChatColor.GREEN +  ((armamentHakiLevels[getConquerorHakiLevel(p.getName()) + 1])- (getConquerorHakiXP(p.getName()))));
	  
	  ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
	  ItemStack className = new ItemStack(Material.BOOK);
	  ItemMeta classNameMeta = className.getItemMeta();
	  ItemStack kills = new ItemStack(Material.IRON_SWORD);
	  ItemMeta killsMeta = kills.getItemMeta();
	  ItemStack deaths = new ItemStack(Material.BONE);
	  ItemMeta deathsMeta = deaths.getItemMeta();
	  ItemStack armament = new ItemStack(Material.IRON_INGOT);
	  ItemMeta armamentMeta = armament.getItemMeta();
	  ItemStack observation = new ItemStack(Material.NETHER_STAR);
	  ItemMeta observationMeta = observation.getItemMeta();
	  ItemStack conqueror = new ItemStack(Material.BLAZE_ROD);
	  ItemMeta conquerorMeta = observation.getItemMeta();

	  SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
	  skullMeta.setOwner(p.getName());
	  skullMeta.setDisplayName(ChatColor.GOLD + p.getName() + "'s Stats");
	  skull.setItemMeta(skullMeta);
	  
	  classNameMeta.setDisplayName(ChatColor.GOLD + "Class: " + ChatColor.BLUE + getClass(p.getName()));
	  className.setItemMeta(classNameMeta);
	  
	  killsMeta.setDisplayName(ChatColor.GOLD + "Kills: " + ChatColor.BLUE + getKills(p.getName()));
	  kills.setItemMeta(killsMeta);
	  
	  deathsMeta.setDisplayName(ChatColor.GOLD + "Deaths: " + ChatColor.BLUE + getDeaths(p.getName()));
	  deaths.setItemMeta(deathsMeta);
	  
	  
	  armamentMeta.setDisplayName(ChatColor.GOLD + "Armament Haki");
	  armamentMeta.setLore(armamentLore);
	  armament.setItemMeta(armamentMeta);
	  
	  observationMeta.setDisplayName(ChatColor.GOLD + "Observation Haki");
	  observationMeta.setLore(observationLore);
	  observation.setItemMeta(observationMeta);
	  
	  conquerorMeta.setDisplayName(ChatColor.GOLD + "Conqueror Haki");
	  conquerorMeta.setLore(conquerorLore);
	  conqueror.setItemMeta(conquerorMeta);
	  
	  inv.setItem(0, skull);
	  inv.setItem(9, className);
	  inv.setItem(4, kills);
	  inv.setItem(13, deaths);
	  inv.setItem(8, armament);
	  inv.setItem(17, observation);
	  inv.setItem(26, conqueror);

	  
	  p.openInventory(inv);
  }
  
  public void openStatsGUIOtherPlayer(Player p, Player openFor){
	  Inventory inv = Bukkit.createInventory(null, 27, ChatColor.DARK_GRAY + "Stats");
	  List<String> armamentLore = new ArrayList();
	  armamentLore.add(ChatColor.BLUE + "Level: "+ ChatColor.GREEN + getArmamentHakiLevel(p.getName()));
	  armamentLore.add(ChatColor.BLUE + "XP: " + ChatColor.GREEN + getArmamentHakiXP(p.getName()));
	  armamentLore.add(ChatColor.BLUE + "XP Needed: " + ChatColor.GREEN +  ((armamentHakiLevels[getArmamentHakiLevel(p.getName()) + 1])- (getArmamentHakiXP(p.getName()))));
	  
	  List<String> observationLore = new ArrayList();
	  observationLore.add(ChatColor.BLUE + "Level: "+ ChatColor.GREEN + getObservationHakiLevel(p.getName()));
	  observationLore.add(ChatColor.BLUE + "XP: " + ChatColor.GREEN + getObservationHakiXP(p.getName()));
	  observationLore.add(ChatColor.BLUE + "XP Needed: " + ChatColor.GREEN +  ((armamentHakiLevels[getObservationHakiLevel(p.getName()) + 1])- (getObservationHakiXP(p.getName()))));
	  
	  List<String> conquerorLore = new ArrayList();
	  conquerorLore.add(ChatColor.BLUE + "Level: "+ ChatColor.GREEN + getConquerorHakiLevel(p.getName()));
	  conquerorLore.add(ChatColor.BLUE + "XP: " + ChatColor.GREEN + getConquerorHakiXP(p.getName()));
	  conquerorLore.add(ChatColor.BLUE + "XP Needed: " + ChatColor.GREEN +  ((armamentHakiLevels[getConquerorHakiLevel(p.getName()) + 1])- (getConquerorHakiXP(p.getName()))));
	  
	  ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
	  ItemStack className = new ItemStack(Material.BOOK);
	  ItemMeta classNameMeta = className.getItemMeta();
	  ItemStack kills = new ItemStack(Material.IRON_SWORD);
	  ItemMeta killsMeta = kills.getItemMeta();
	  ItemStack deaths = new ItemStack(Material.BONE);
	  ItemMeta deathsMeta = deaths.getItemMeta();
	  ItemStack armament = new ItemStack(Material.IRON_INGOT);
	  ItemMeta armamentMeta = armament.getItemMeta();
	  ItemStack observation = new ItemStack(Material.NETHER_STAR);
	  ItemMeta observationMeta = observation.getItemMeta();
	  ItemStack conqueror = new ItemStack(Material.BLAZE_ROD);
	  ItemMeta conquerorMeta = observation.getItemMeta();

	  SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
	  skullMeta.setOwner(p.getName());
	  skullMeta.setDisplayName(ChatColor.GOLD + p.getName() + "'s Stats");
	  skull.setItemMeta(skullMeta);
	  
	  classNameMeta.setDisplayName(ChatColor.GOLD + "Class: " + ChatColor.BLUE + getClass(p.getName()));
	  className.setItemMeta(classNameMeta);
	  
	  killsMeta.setDisplayName(ChatColor.GOLD + "Kills: " + ChatColor.BLUE + getKills(p.getName()));
	  kills.setItemMeta(killsMeta);
	  
	  deathsMeta.setDisplayName(ChatColor.GOLD + "Deaths: " + ChatColor.BLUE + getDeaths(p.getName()));
	  deaths.setItemMeta(deathsMeta);
	  
	  
	  armamentMeta.setDisplayName(ChatColor.GOLD + "Armament Haki");
	  armamentMeta.setLore(armamentLore);
	  armament.setItemMeta(armamentMeta);
	  
	  observationMeta.setDisplayName(ChatColor.GOLD + "Observation Haki");
	  observationMeta.setLore(observationLore);
	  observation.setItemMeta(observationMeta);
	  
	  conquerorMeta.setDisplayName(ChatColor.GOLD + "Conqueror Haki");
	  conquerorMeta.setLore(conquerorLore);
	  conqueror.setItemMeta(conquerorMeta);
	  
	  inv.setItem(0, skull);
	  inv.setItem(9, className);
	  inv.setItem(4, kills);
	  inv.setItem(13, deaths);
	  inv.setItem(8, armament);
	  inv.setItem(17, observation);
	  inv.setItem(26, conqueror);

	  
	  openFor.openInventory(inv);
  }
  
  public void createFile(Player p) {
	  File pFileDir = new File(this.getDataFolder(), "Players");
	  if (!pFileDir.exists()) {
	  pFileDir.mkdir();
	  }
	  File pFile = new File(this.getDataFolder(), "Players/" + p.getName().toLowerCase() + ".yml");
	  if (!pFile.exists())
	  try {
	  pFile.createNewFile();
	  FileConfiguration pConfig = YamlConfiguration.loadConfiguration(pFile);
	  pConfig.set("User", p.getName());
	  pConfig.set("Class", "No Class");
	  pConfig.set("Kills", Integer.valueOf(0));
	  pConfig.set("Deaths", Integer.valueOf(0));
	  pConfig.set("ArmamentHakiLevel", Integer.valueOf(0));
	  pConfig.set("ArmamentHakiXP", Integer.valueOf(0));
	  pConfig.set("ObservationHakiLevel", Integer.valueOf(0));
	  pConfig.set("ObservationHakiXP", Integer.valueOf(0));
	  pConfig.set("ConquerorsHaki", Boolean.valueOf(false));
	  pConfig.set("ConquerorHakiLevel", Integer.valueOf(0));
	  pConfig.set("ConquerorHakiXP", Integer.valueOf(0));
	  pConfig.save(pFile);
	  }
	  catch (Exception e) {
	  }
	  }
  
  public String getClass(String p){
	  File pFile = new File(this.getDataFolder(), "Players/" + p.toLowerCase() + ".yml");
	  FileConfiguration pConfig = YamlConfiguration.loadConfiguration(pFile);
	  String className = pConfig.getString("Class");
	  return className;
  }
	   
	  //****************************//
	   
	  public String getPlayerName(String p) {
	  File pFile = new File(this.getDataFolder(), "Players/" + p.toLowerCase() + ".yml");
	  FileConfiguration pConfig = YamlConfiguration.loadConfiguration(pFile);
	  String name = pConfig.getString("User");
	  return name;
	  }
	   
	  //****************************//
	   
	  public boolean fileExists(String p) {
	  File pFile = new File(this.getDataFolder(), "Players/" + p.toLowerCase() + ".yml");
	  if (pFile.exists()) {
	  return true;
	  }
	  return false;
	  }
	   
	  //****************************//
	   
	  public Integer getKills(String p)
	  {
	  File pFile = new File(this.getDataFolder(), "Players/" + p.toLowerCase() + ".yml");
	  FileConfiguration pConfig = YamlConfiguration.loadConfiguration(pFile);
	  int kills = pConfig.getInt("Kills");
	  return Integer.valueOf(kills);
	  }
	   
	  //****************************//
	   
	  public void addKill(String p) {
	  File pFile = new File(this.getDataFolder(), "Players/" + p.toLowerCase() + ".yml");
	  FileConfiguration pConfig = YamlConfiguration.loadConfiguration(pFile);
	  pConfig.set("Kills", Integer.valueOf(pConfig.getInt("Kills") + 1));
	  try {
	  pConfig.save(pFile);
	  } catch (Exception e) {
	  }
	  }
	   
	  //****************************//
	   
	  public Integer getDeaths(String p) {
	  File pFile = new File(this.getDataFolder(), "Players/" + p.toLowerCase() + ".yml");
	  FileConfiguration pConfig = YamlConfiguration.loadConfiguration(pFile);
	  int deaths = pConfig.getInt("Deaths");
	  return Integer.valueOf(deaths);
	  }
	   
	  //****************************//
	   
	  public void addDeath(String p) {
	  File pFile = new File(this.getDataFolder(), "Players/" + p.toLowerCase() + ".yml");
	  FileConfiguration pConfig = YamlConfiguration.loadConfiguration(pFile);
	  pConfig.set("Deaths", Integer.valueOf(pConfig.getInt("Deaths") + 1));
	  try {
	  pConfig.save(pFile);
	  } catch (Exception e) {
	  }
	  }
	   
	  //****************************//
	   
	  public Integer getPoints(String p) {
	  File pFile = new File(this.getDataFolder(), "Players/" + p.toLowerCase() + ".yml");
	  FileConfiguration pConfig = YamlConfiguration.loadConfiguration(pFile);
	  int Points = pConfig.getInt("Points");
	  return Integer.valueOf(Points);
	  }
	   
	  //****************************//
	   
	  public void setPoints(String p, int newAmount) {
	  File pFile = new File(this.getDataFolder(), "Players/" + p.toLowerCase() + ".yml");
	  FileConfiguration pConfig = YamlConfiguration.loadConfiguration(pFile);
	  pConfig.set("Points", Integer.valueOf(newAmount));
	  try {
	  pConfig.save(pFile);
	  } catch (Exception e) {
	  }
	  }
	   
	  //****************************//
	   
	  public void addPoints(String p, int amountAdded) {
	  File pFile = new File(this.getDataFolder(), "Players/" + p.toLowerCase() + ".yml");
	  FileConfiguration pConfig = YamlConfiguration.loadConfiguration(pFile);
	  pConfig.set("Points", Integer.valueOf(pConfig.getInt("Points") + amountAdded));
	  try {
	  pConfig.save(pFile);
	  } catch (Exception e) {
	  }
	  }
	   
	  //****************************//
	   
	  public void takePoints(String p, int amountTaken) {
	  File pFile = new File(this.getDataFolder(), "Players/" + p.toLowerCase() + ".yml");
	  FileConfiguration pConfig = YamlConfiguration.loadConfiguration(pFile);
	  int PointsCurrent = pConfig.getInt("Points");
	  if (PointsCurrent - amountTaken >= 0) {
	  int newAmount = PointsCurrent - amountTaken;
	  pConfig.set("Points", Integer.valueOf(newAmount));
	  }
	  try {
	  pConfig.save(pFile);
	  } catch (Exception e) {
	  }
	  }
	  
	  public void setClass(String p, String className) {
	  File pFile = new File(this.getDataFolder(), "Players/" + p.toLowerCase() + ".yml");
	  FileConfiguration pConfig = YamlConfiguration.loadConfiguration(pFile);
	  pConfig.set("Class", className);
	  try {
	  pConfig.save(pFile);
	  } catch (Exception e) {
	  }
	  }
	  
	  
	  public int getObservationHakiLevel(String p){
		  File pFile = new File(this.getDataFolder(), "Players/" + p.toLowerCase() + ".yml");
		  FileConfiguration pConfig = YamlConfiguration.loadConfiguration(pFile);
		  int level = pConfig.getInt("ObservationHakiLevel");
		  return Integer.valueOf(level);
	  }
	  
	  public int getArmamentHakiLevel(String p){
		  File pFile = new File(this.getDataFolder(), "Players/" + p.toLowerCase() + ".yml");
		  FileConfiguration pConfig = YamlConfiguration.loadConfiguration(pFile);
		  int level = pConfig.getInt("ArmamentHakiLevel");
		  return Integer.valueOf(level);
	  }
	  
	  public int getConquerorHakiLevel(String p){
		  File pFile = new File(this.getDataFolder(), "Players/" + p.toLowerCase() + ".yml");
		  FileConfiguration pConfig = YamlConfiguration.loadConfiguration(pFile);
		  int level = pConfig.getInt("ConquerorHakiLevel");
		  return Integer.valueOf(level);
	  }
	  
	  public int getObservationHakiXP(String p){
		  File pFile = new File(this.getDataFolder(), "Players/" + p.toLowerCase() + ".yml");
		  FileConfiguration pConfig = YamlConfiguration.loadConfiguration(pFile);
		  int xp = pConfig.getInt("ObservationHakiXP");
		  return Integer.valueOf(xp);
	  }
	  
	  public int getArmamentHakiXP(String p){
		  File pFile = new File(this.getDataFolder(), "Players/" + p.toLowerCase() + ".yml");
		  FileConfiguration pConfig = YamlConfiguration.loadConfiguration(pFile);
		  int xp = pConfig.getInt("ArmamentHakiXP");
		  return Integer.valueOf(xp);
	  }
	  
	  public int getConquerorHakiXP(String p){
		  File pFile = new File(this.getDataFolder(), "Players/" + p.toLowerCase() + ".yml");
		  FileConfiguration pConfig = YamlConfiguration.loadConfiguration(pFile);
		  int xp = pConfig.getInt("ConquerorHakiXP");
		  return Integer.valueOf(xp);
	  }
	  
	  public void observationHakiLevelUp(String p) {
	  File pFile = new File(this.getDataFolder(), "Players/" + p.toLowerCase() + ".yml");
	  FileConfiguration pConfig = YamlConfiguration.loadConfiguration(pFile);
	  pConfig.set("ObservationHakiLevel", Integer.valueOf(pConfig.getInt("ObservationHakiLevel") + 1));
	  try {
	  pConfig.save(pFile);
	  } catch (Exception e) {
	  }
	  Bukkit.broadcastMessage(ChatColor.YELLOW + p + ChatColor.GREEN + " has achieved Level " + getObservationHakiLevel(p) + "Observation Haki!" );
	  }
	  
	  public void armamentHakiLevelUp(String p) {
	  File pFile = new File(this.getDataFolder(), "Players/" + p.toLowerCase() + ".yml");
	  FileConfiguration pConfig = YamlConfiguration.loadConfiguration(pFile);
	  pConfig.set("ArmamentHakiLevel", Integer.valueOf(pConfig.getInt("ArmamentHakiLevel") + 1));
	  try {
	  pConfig.save(pFile);
	  } catch (Exception e) {
	  }
	  Bukkit.broadcastMessage(ChatColor.YELLOW + p + ChatColor.GREEN + " has achieved Level " + getArmamentHakiLevel(p) + "Armament Haki!" );
	  }
	  
	  public void conquerorHakiLevelUp(String p) {
	  File pFile = new File(this.getDataFolder(), "Players/" + p.toLowerCase() + ".yml");
	  FileConfiguration pConfig = YamlConfiguration.loadConfiguration(pFile);
	  pConfig.set("ConquerorHakiLevel", Integer.valueOf(pConfig.getInt("ConquerorHakiLevel") + 1));
	  try {
	  pConfig.save(pFile);
	  } catch (Exception e) {
	  }
	  Bukkit.broadcastMessage(ChatColor.YELLOW + p + ChatColor.GREEN + " has achieved Level " + getConquerorHakiLevel(p) + "Conqueror Haki!" );
	  }
	  
	  public void addObservationHakiXP(String p, int xpBoost) {
	  File pFile = new File(this.getDataFolder(), "Players/" + p.toLowerCase() + ".yml");
	  FileConfiguration pConfig = YamlConfiguration.loadConfiguration(pFile);
	  pConfig.set("ObservationHakiXP", Integer.valueOf(pConfig.getInt("ObservationHakiXP") + xpBoost));
	  try {
	  pConfig.save(pFile);
	  } catch (Exception e) {
	  }
	  }
	  
	  public void addArmamentHakiXP(String p, int xpBoost) {
	  File pFile = new File(this.getDataFolder(), "Players/" + p.toLowerCase() + ".yml");
	  FileConfiguration pConfig = YamlConfiguration.loadConfiguration(pFile);
	  pConfig.set("ArmamentHakiXP", Integer.valueOf(pConfig.getInt("ArmamentHakiXP") + xpBoost));
	  try {
	  pConfig.save(pFile);
	  } catch (Exception e) {
	  }
	  }
	  
	  public void addConquerorHakiXP(String p, int xpBoost) {
	  File pFile = new File(this.getDataFolder(), "Players/" + p.toLowerCase() + ".yml");
	  FileConfiguration pConfig = YamlConfiguration.loadConfiguration(pFile);
	  pConfig.set("ConquerorHakiXP", Integer.valueOf(pConfig.getInt("ConquerorHakiXP") + xpBoost));
	  try {
	  pConfig.save(pFile);
	  } catch (Exception e) {
	  }
	  }
	  
	  public void resetObservationHakiXP(String p) {
	  File pFile = new File(this.getDataFolder(), "Players/" + p.toLowerCase() + ".yml");
	  FileConfiguration pConfig = YamlConfiguration.loadConfiguration(pFile);
	  pConfig.set("ObservationHakiXP", Integer.valueOf(pConfig.getInt("ObservationHakiXP")* 0));
	  try {
	  pConfig.save(pFile);
	  } catch (Exception e) {
	  }
	  }
	  
	  public void resetArmamentHakiXP(String p) {
	  File pFile = new File(this.getDataFolder(), "Players/" + p.toLowerCase() + ".yml");
	  FileConfiguration pConfig = YamlConfiguration.loadConfiguration(pFile);
	  pConfig.set("ArmamentHakiXP", Integer.valueOf(pConfig.getInt("ArmamentHakiXP")* 0));
	  try {
	  pConfig.save(pFile);
	  } catch (Exception e) {
	  }
	  }
	  
	  public void resetConquerorHakiXP(String p) {
	  File pFile = new File(this.getDataFolder(), "Players/" + p.toLowerCase() + ".yml");
	  FileConfiguration pConfig = YamlConfiguration.loadConfiguration(pFile);
	  pConfig.set("ConquerorHakiXP", Integer.valueOf(pConfig.getInt("ConquerorHakiXP")* 0));
	  try {
	  pConfig.save(pFile);
	  } catch (Exception e) {
	  }
	  }
	  
	  public void conquerorLevelUpManager(String p){
		  int level = (int) getArmamentHakiLevel(p);
		  int xp = (int) getArmamentHakiXP(p);
		  
		  switch(level){
		  case 0:
			  if(xp >= 500){
				  conquerorHakiLevelUp(p);
				  resetConquerorHakiXP(p);
			  }
			  break;
		  case 1:
			  if(xp >= 500){
				  conquerorHakiLevelUp(p);
				  resetConquerorHakiXP(p);
			  }
			  break;
		  case 2:
			  if(xp >= 500){
				  conquerorHakiLevelUp(p);
				  resetConquerorHakiXP(p);
			  }
			  break;
		  case 3:
			  if(xp >= 500){
				  conquerorHakiLevelUp(p);
				  resetConquerorHakiXP(p);
			  }
			  break;
		  case 4:
			  if(xp >= 500){
				  conquerorHakiLevelUp(p);
				  resetConquerorHakiXP(p);
			  }
			  break;
		  case 5:
			  if(xp >= 500){
				  conquerorHakiLevelUp(p);
				  resetConquerorHakiXP(p);
			  }
			  break;
		  case 6:
			  if(xp >= 500){
				  conquerorHakiLevelUp(p);
				  resetConquerorHakiXP(p);
			  }
			  break;
		  case 7:
			  if(xp >= 500){
				  conquerorHakiLevelUp(p);
				  resetConquerorHakiXP(p);
			  }
			  break;
		  case 8:
			  if(xp >= 500){
				  conquerorHakiLevelUp(p);
				  resetConquerorHakiXP(p);
			  }
			  break;
		  case 9:
			  if(xp >= 500){
				  conquerorHakiLevelUp(p);
				  resetConquerorHakiXP(p);
			  }
			  break;
			 
		  default:
			  break;
		  }
				  
		  }
		  
		  public void observationLevelUpManager(String p){
			  int level = (int) getArmamentHakiLevel(p);
			  int xp = (int) getArmamentHakiXP(p);
			  
			  switch(level){
			  case 0:
				  if(xp >= 500){
					  observationHakiLevelUp(p);
					  resetObservationHakiXP(p);
				  }
				  break;
			  case 1:
				  if(xp >= 500){
					  observationHakiLevelUp(p);
					  resetObservationHakiXP(p);
				  }
				  break;
			  case 2:
				  if(xp >= 500){
					  observationHakiLevelUp(p);
					  resetObservationHakiXP(p);
				  }
				  break;
			  case 3:
				  if(xp >= 500){
					  observationHakiLevelUp(p);
					  resetObservationHakiXP(p);
				  }
				  break;
			  case 4:
				  if(xp >= 500){
					  observationHakiLevelUp(p);
					  resetObservationHakiXP(p);
				  }
				  break;
			  case 5:
				  if(xp >= 500){
					  observationHakiLevelUp(p);
					  resetObservationHakiXP(p);
				  }
				  break;
			  case 6:
				  if(xp >= 500){
					  observationHakiLevelUp(p);
					  resetObservationHakiXP(p);
				  }
				  break;
			  case 7:
				  if(xp >= 500){
					  observationHakiLevelUp(p);
					  resetObservationHakiXP(p);
				  }
				  break;
			  case 8:
				  if(xp >= 500){
					  observationHakiLevelUp(p);
					  resetObservationHakiXP(p);
				  }
				  break;
			  case 9:
				  if(xp >= 500){
					  observationHakiLevelUp(p);
					  resetObservationHakiXP(p);
				  }
				  break;
				  default:
					  
					  break;
			  }
	  }
		  public void armamentLevelUpManager(String p){
			  int level = (int) getArmamentHakiLevel(p);
			  int xp = (int) getArmamentHakiXP(p);
			  
			  switch(level){
			  case 0:
				  if(xp >= armamentHakiLevels[0]){
					  observationHakiLevelUp(p);
					  resetObservationHakiXP(p);
				  }
				  break;
			  case 1:
				  if(xp >= armamentHakiLevels[1]){
					  observationHakiLevelUp(p);
					  resetObservationHakiXP(p);
				  }
				  break;
			  case 2:
				  if(xp >= armamentHakiLevels[2]){
					  observationHakiLevelUp(p);
					  resetObservationHakiXP(p);
				  }
				  break;
			  case 3:
				  if(xp >= armamentHakiLevels[3]){
					  observationHakiLevelUp(p);
					  resetObservationHakiXP(p);
				  }
				  break;
			  case 4:
				  if(xp >= armamentHakiLevels[4]){
					  observationHakiLevelUp(p);
					  resetObservationHakiXP(p);
				  }
				  break;
			  case 5:
				  if(xp >= armamentHakiLevels[5]){
					  observationHakiLevelUp(p);
					  resetObservationHakiXP(p);
				  }
				  break;
			  case 6:
				  if(xp >= armamentHakiLevels[6]){
					  observationHakiLevelUp(p);
					  resetObservationHakiXP(p);
				  }
				  break;
			  case 7:
				  if(xp >= armamentHakiLevels[7]){
					  observationHakiLevelUp(p);
					  resetObservationHakiXP(p);
				  }
				  break;
			  case 8:
				  if(xp >= armamentHakiLevels[8]){
					  observationHakiLevelUp(p);
					  resetObservationHakiXP(p);
				  }
				  break;
			  case 9:
				  if(xp >= armamentHakiLevels[9]){
					  observationHakiLevelUp(p);
					  resetObservationHakiXP(p);
				  }
				  break;
				  default:
					  break;
			  }
		  }
		  public int getObservationPercent(String p){
			  switch(getObservationHakiLevel(p)){
			  case 0:
				  return 0;
			case 1:
				  return 5;
			  case 2:
				  return 7;
			  case 3:
				  return 11;
			  case 4:
				  return 15;
			  case 5:
				  return 19;
			  case 6:
				  return 23;
			  case 7:
				  return 26;
			  case 8:
				  return 29;
			  case 9:
				  return 33;
			  default:
					
				return 0;
			  }
			  
		  }
}
