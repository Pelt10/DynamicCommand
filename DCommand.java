import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * This class provide you to register a command without the plugin.yml and change the commandExecutor dynamically
 * @author pelt10
 * @version 1.2
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
	String bukkitVersion = Bukkit.getServer().getClass().getPackage().getName();
	try {
	    Class<?> clazzCraftServer = Class.forName("org.bukkit.craftbukkit." + bukkitVersion.substring(bukkitVersion.lastIndexOf('.') + 1) + ".CraftServer");
	    Object craftServer = clazzCraftServer.cast(Bukkit.getServer());
	    Object commandMap = craftServer.getClass().getDeclaredMethod("getCommandMap").invoke(craftServer);
	    commandMap.getClass().getDeclaredMethod("register", String.class, String.class, Command.class).invoke(commandMap, getName(), plugin.getName(), this);
	} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
	    Bukkit.getLogger().log(Level.SEVERE, e.getMessage(), e);
	}
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
