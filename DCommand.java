import java.util.List;

import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.craftbukkit.v1_9_R2.CraftServer;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * This class provide you to register a command without the plugin.yml and change the commandExecutor dynamically
 * @author pelt10
 * @version 1.1
 */
public class DCommand extends BukkitCommand {
    private CommandExecutor executor;

    /**
     * @param name command name
     * @param executor command executor
     * @param plugin {@link JavaPlugin} that own the command
     */
    public DCommand(String name, CommandExecutor executor, JavaPlugin plugin) {
	super(name);
	this.executor = executor;
	registerCommand(plugin);
    }
    
    /**
     * @param name command name
     * @param usage exemple usage
     * @param description command description
     * @param permission needed permission to start the command
     * @param aliases list of aliases
     * @param executor command executor
     * @param plugin {@link JavaPlugin} that own the command
     */
    public DCommand(String name, String usage, String description, String permission, List<String> aliases, CommandExecutor executor, JavaPlugin plugin) {
	super(name, description, usage, aliases);
	this.setPermission(permission);
	this.executor = executor;
	registerCommand(plugin);
    }

    private void registerCommand(JavaPlugin plugin) {
	((CraftServer) Bukkit.getServer()).getCommandMap().register(getName(), plugin.getName(), this);
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
	if (Objects.isNull(executor)) {
	    throw new IllegalStateException("The executor for command " + getName() + " is null !");
	}
	return executor.onCommand(sender, this, label, args);
    }
    
    /**
     * You can change the {@link CommandExecutor} to change the behavior of the command over time.
     * 
     * @param executor new command Executor
     */
    public void setExecutor(CommandExecutor executor) {
	this.executor = executor;
    }
}
