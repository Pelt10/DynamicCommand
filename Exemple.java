import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;


public class Exemple extends JavaPlugin {
    
    @Override
    public void onEnable() {
	//Simple usage
	new DCommand("hello", new HelloExecutor(), this);
	
	//Usage with all arguments
	new DCommand("hello", "/hello", "Just send \"Hello World !\" to sender", null, Arrays.asList("hi", "helloWorld"), new HelloExecutor(), this);
	
	
	//You may use lambda, but is not very readable...
	new DCommand("hello", (sender, command, label, args) -> {
	    sender.sendMessage("Hello World !");
	    return true;
	}, this);
	super.onEnable();
    }

    //I just put the CommandExecutor here for the example, don't put here !
    private class HelloExecutor implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
	    sender.sendMessage("Hello World !");
	    return true;
	}
    }
}
