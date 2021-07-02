package github.clemens_mine.typecho_support;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author ClemensMine
 */
public final class Typecho_support extends JavaPlugin implements Listener {

    public static Typecho_support instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getConsoleSender().sendMessage("§6正在加载白名单插件");
        instance = this;
        saveDefaultConfig();
        function.loadConfig();
       getServer().getPluginCommand("tlist").setExecutor(new commands());
       getServer().getPluginManager().registerEvents(this,this);
        getServer().getConsoleSender().sendMessage("§a加载完毕");
    }

    @EventHandler
    public void playerLoginEvent(PlayerLoginEvent e){
        Player p = e.getPlayer();
        if(!function.enable ||
                function.whiteList.contains(p.getName().toLowerCase()) ||
                function.judge(p.getName(),function.group)) {
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for(String msg : function.kickmsg){
            stringBuilder.append(msg.replace("&","§")).append("\n");
        }
        e.setKickMessage(stringBuilder.toString());
        e.setResult(PlayerLoginEvent.Result.KICK_WHITELIST);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getConsoleSender().sendMessage("§c正在卸载白名单插件");
    }
}
