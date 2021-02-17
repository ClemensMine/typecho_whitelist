package github.clemens_mine.typecho_support;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class function {
    static String database;
    static String prefix;
    static String address;
    static String username;
    static String pwd;
    static String group;
    static Boolean enable;
    static List<String> kickmsg;
    static Boolean result = false;
    static int permission;
    static List<String> whiteList;

    static File file = new File(Typecho_support.instance.getDataFolder(),"whitelist.yml");
    static FileConfiguration configuration;

    // 判断该用户是否为白名单
    // 关注着0 贡献者1 编辑2 管理3
    public static Boolean judge(String name, String gp){

        MySQLAPI api = new MySQLAPI(address,username,pwd,database,prefix + "users");
        permission = getWeight(gp);
        int cache;

        api.connect();
        if(api.getData("name",name, "name") == null){
            return result;
        }
        cache = getWeight(api.getData("name",name,"`group`").toString());
        if(cache >= permission){
            return true;
        }
        api.close();

        return result;
    }

    // 获得权重
    public static Integer getWeight(String group){
        switch (group){
            case "subscriber":
                return 0;
            case "contributor":
                return 1;
            case "editor":
                return 2;
            case "administrator":
                return 3;
            default:
                return -1;
        }
    }

    // 加载配置文件文件内容
    public static void loadConfig(){

        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        configuration = YamlConfiguration.loadConfiguration(file);

        whiteList = configuration.getStringList("Lists");

        database = Typecho_support.instance.getConfig().getString("Mysql.database");
        prefix = Typecho_support.instance.getConfig().getString("Mysql.prefix");
        address = Typecho_support.instance.getConfig().getString("Mysql.address");
        username = Typecho_support.instance.getConfig().getString("Mysql.username");
        pwd = Typecho_support.instance.getConfig().getString("Mysql.password");
        group = Typecho_support.instance.getConfig().getString("Settings.group");
        enable = Typecho_support.instance.getConfig().getBoolean("Settings.enable");
        kickmsg = Typecho_support.instance.getConfig().getStringList("Messages.not-in-list");
    }
}