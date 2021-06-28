package github.clemens_mine.typecho_support;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ClemensMine
 */
public class MySQLAPI {

    String url;
    String username;
    String pwd;
    String database;
    String SqlTable;
    Boolean SSL;

    Connection conn = null;
    Statement statement = null;
    ResultSet rs = null;
    PreparedStatement ps = null;

    public MySQLAPI(String url, String name, String password, String base, Boolean useSSL, String table){
        this.url = url;
        this.username = name;
        this.pwd = password;
        this.database = base;
        this.SqlTable = table;
        this.SSL = useSSL;
    }

    public void connect(){
        try{
            this.conn = DriverManager.getConnection("jdbc:mysql://" + this.url + "/" + this.database + "?useUnicode=true&characterEncoding=utf-8&useSSL=" + this.SSL,this.username,this.pwd);
            this.statement = this.conn.createStatement();
            this.ps = this.conn.prepareStatement("");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void close(){
        try{
            if(this.rs != null){
                this.rs.close();
                this.rs = null;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            if(this.statement != null){
                try {
                    this.statement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                this.statement = null;
            }
            if(this.conn != null){
                try {
                    this.conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                this.conn = null;
            }
        }
    }

    public Object getData(String key, String keydata, String value) {
        try {
            this.rs = this.statement.executeQuery("select " + value + " from " + this.SqlTable + " where " + key + "='" + keydata + "'");
            if (this.rs.next()) {
                return this.rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}

