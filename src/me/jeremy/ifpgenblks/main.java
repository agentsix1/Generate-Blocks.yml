package me.jeremy.ifpgenblks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;



public class main extends JavaPlugin {
	@Override
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(new listener(this), this);
		loadConfiguration();
		System.out.println("[ItemFilterPickupGenerateBlocksYML] Plugin is fully loaded and ready to go! Good luck!");
		
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public void loadConfiguration(){
		getConfig().options().copyDefaults(true);
	    saveConfig();
	    reloadConfig();
	    getBlocks().options().copyDefaults(true);
	    saveBlocks();
	    reloadBlocks();					
	}
	
	public void pSend(Player p, String msg) {
		 p.sendMessage(colorChatb(msg));
	 }
	
	public String colorChatb(String message) {
		 return message.replace("&0", ChatColor.BLACK + "").replace("&1", ChatColor.DARK_BLUE + "").replace("&2", ChatColor.DARK_GREEN + "").replace("&3", ChatColor.DARK_AQUA + "").replace("&4", ChatColor.DARK_RED + "").replace("&5", ChatColor.DARK_PURPLE + "").replace("&6", ChatColor.GOLD + "").replace("&7", ChatColor.GRAY + "").replace("&8", ChatColor.DARK_GRAY + "").replace("&9", ChatColor.BLUE + "").replace("&a", ChatColor.GREEN + "").replace("&b", ChatColor.AQUA + "").replace("&c", ChatColor.RED + "").replace("&d", ChatColor.LIGHT_PURPLE + "").replace("&e", ChatColor.YELLOW + "").replace("&f", ChatColor.WHITE + "").replace("&l", ChatColor.BOLD + "").replace("&m", ChatColor.STRIKETHROUGH + "").replace("&n", ChatColor.UNDERLINE + "").replace("&o", ChatColor.ITALIC + "").replace("&r", ChatColor.RESET + "");
	 }
	
	public Boolean enableAdd = false;
	public String category = getBlocks().getStringList("Menus").get(0);
	
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			
			if (cmdLabel.equalsIgnoreCase("genblocks")) {
				if (args.length == 1) {
					
					if (enableAdd) {
						enableAdd = false;
						pSend(p, "Block Add Disabled");
					} else {
						enableAdd = true;
						category = getBlocks().getStringList("Menus").get(Integer.parseInt(args[0]));
						pSend(p, "Block Add Enabled - Category: " + category);
						
					}
					return true;
				} else if (args.length == 2){
					if (args[0].equalsIgnoreCase("item")) {
						getBlocks().set(category + ".Block", p.getItemInHand().getType().toString() + ":" + p.getItemInHand().getData().toString().split("\\(")[1].split("\\)")[0] + ":1:none:" + args[1].replace("%SPACE%", " ") + ":none");
						saveBlocks();
						reloadBlocks();
						return false;
					}
				}
			}
		}
		return false;
	}
	
	
	
    private FileConfiguration blocksConfig = null; //customConfig 
    private File blocks = null; //customConfigFile
    
    
    public void reloadBlocks() {
        if (blocks == null) {
        	blocks = new File(getDataFolder(), "blocks.yml");
        }
        blocksConfig = YamlConfiguration.loadConfiguration(blocks);
        // Look for defaults in the jar
        Reader defConfigStream;
		try {
			defConfigStream = new InputStreamReader(this.getResource("blocks.yml"), "UTF8");
		
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            blocksConfig.setDefaults(defConfig);
        }
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public FileConfiguration getBlocks() {
        if (blocksConfig == null) {
            reloadBlocks();
        }
        return blocksConfig;
    }
    
    public void saveBlocks() {
        if (blocksConfig == null || blocksConfig == null) {
            return;
        }
        try {
        	getBlocks().save(blocks);
        } catch (IOException ex) {
            getLogger().log(Level.SEVERE, "Could not save config to " + blocks, ex);
        }
    }
    
    public void saveDefaultBlocks() {
        if (blocks == null) {
        	blocks = new File(getDataFolder(), "blocks.yml");
        }
        if (!blocks.exists()) {            
             this.saveResource("blocks.yml", false);
         }
    }
}
