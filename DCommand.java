/*
 * DynamicCommand
 * @author Pelt10
 * @version 1.0
 */

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * @param name String with the command name
 * @param usage String with the command syntax
 * @param description String with the command description
 * @param permission String with the command permission
 * @param aliases An ArrayList with all alliases 
 * @param executor The CommandExecutor of this command
 * @param plugin The JavaPlugin of the Plugin who register this command
 */
public class DCommand extends BukkitCommand{
	protected CommandExecutor executor;
	
	protected DCommand(String name, String usage, String description,String permission, ArrayList<String> aliases, CommandExecutor executor, JavaPlugin plugin) {
            super(name, description, usage, aliases);
            this.setPermission(permission);
            this.executor = executor;
        
            ((CraftServer) Bukkit.getServer()).getCommandMap().register(name, plugin.getName(), this);
	}
	
	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if(executor != null)
	            return executor.onCommand(sender, this, label, args);
	}
	
	

}
