package github.clemens_mine.typecho_support;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

/**
 * @author ClemensMine
 */
public class commands implements CommandExecutor {

    static File file = new File(Typecho_support.instance.getDataFolder(),"whitelist.yml");
    static FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(cmd.getName().equalsIgnoreCase("tlist") && args.length == 0){
            sender.sendMessage("§e===============================");
            sender.sendMessage("§b/tlist add [玩家ID] §b手动添加白名单");
            sender.sendMessage("§b/tlist del [玩家ID] §b手动删除白名单");
            sender.sendMessage("§b/tlist reload §a重载本插件");
            sender.sendMessage("§e===============================");
            return true;
        }

        if(cmd.getName().equalsIgnoreCase("tlist") && args[0].equalsIgnoreCase("add") && args.length == 2){
            if(!sender.hasPermission("tlists.add")){
                sender.sendMessage("§c您没有权限使用此命令");
                return true;
            }
            if(function.whiteList.contains(args[1].toLowerCase(Locale.ROOT))){
                sender.sendMessage("§c该玩家已存在");
                return true;
            }
            function.whiteList.add(args[1].toLowerCase(Locale.ROOT));
            configuration.set("Lists",function.whiteList);
            try {
                configuration.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            sender.sendMessage("§a添加成功");
        }

        if(cmd.getName().equalsIgnoreCase("tlist") && args[0].equalsIgnoreCase("del") && args.length == 2){
            if(!sender.hasPermission("tlists.del")){
                sender.sendMessage("§c您没有权限使用此命令");
                return true;
            }
            if(!function.whiteList.contains(args[1].toLowerCase(Locale.ROOT))){
                sender.sendMessage("§c该玩家不存在");
                return true;
            }
            function.whiteList.remove(args[1].toLowerCase(Locale.ROOT));
            configuration.set("Lists",function.whiteList);
            try {
                configuration.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            sender.sendMessage("§a删除成功");
        }

        if(cmd.getName().equalsIgnoreCase("tlist") && args[0].equalsIgnoreCase("reload") && args.length == 1){
            if(!sender.hasPermission("tlists.reload")){
                sender.sendMessage("§c您没有权限使用此命令");
                return true;
            }
            function.loadConfig();
            sender.sendMessage("§a插件重载成功.");
            return true;
        }
        return true;
    }
}
