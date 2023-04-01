package yumcraft.getofflineloc.Sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author: Ayolk
 * @create: 2023-03-28 20:46
 * @url: github.com/AyolK0327/GetOfflineLoc
 */
public class sql {
    public sql(Plugin plugin){
        this.plugin = plugin;
    }
    private Plugin plugin;
    private static HikariDataSource hikari;

    public YamlConfiguration config;

    private void configuration(){
        File file = new File(plugin.getDataFolder() + "\\datasource.yml");
        this.config = YamlConfiguration.loadConfiguration(file);
        if(!file.exists()){
            plugin.saveResource("datasource.yml",false);
        }
    };
    private YamlConfiguration getConfig(){
        return this.config;
    }
    public HikariDataSource getHikari(){
        configuration();
        YamlConfiguration ConfigDatasource = getConfig();
        HikariConfig config = new HikariConfig();
        plugin.getLogger().info(ConfigDatasource.getString("DefaultSettings.AutoCommit"));
        config.setDriverClassName(ConfigDatasource.getString("DefaultSettings.DriverClassName"));
        config.setAutoCommit(ConfigDatasource.getBoolean("DefaultSettings.AutoCommit"));
        config.setMinimumIdle(ConfigDatasource.getInt("DefaultSettings.MinimumIdle"));
        config.setMaximumPoolSize(ConfigDatasource.getInt("DefaultSettings.MaximumPoolSize"));
        config.setValidationTimeout(ConfigDatasource.getInt("DefaultSettings.ValidationTimeout"));
        config.setConnectionTimeout(ConfigDatasource.getInt("DefaultSettings.ConnectionTimeout"));
        config.setIdleTimeout(ConfigDatasource.getInt("DefaultSettings.IdleTimeout"));
        config.setMaxLifetime(ConfigDatasource.getInt("DefaultSettings.MaxLifetime"));
        config.setConnectionTestQuery(ConfigDatasource.getString("DefaultSettings.ConnectionTestQuery"));
        config.addDataSourceProperty("prepStmtCacheSize",ConfigDatasource.getInt("DefaultSettings.  DataSourceProperty.prepStmtCacheSize"));
        config.addDataSourceProperty("prepStmtCacheSqlLimit",ConfigDatasource.getInt("DefaultSettings.DataSourceProperty.prepStmtCacheSqlLimit"));
        config.addDataSourceProperty("cachePrepStmts",ConfigDatasource.getBoolean("DefaultSettings.  DataSourceProperty.cachePrepStmts"));
        config.addDataSourceProperty("useServerPrepStmts",ConfigDatasource.getBoolean("DefaultSettings.  DataSourceProperty.useServerPrepStmts"));

        String host = plugin.getConfig().getString("Mysql.host");
        String port = plugin.getConfig().getString("Mysql.port");
        String   database = plugin.getConfig().getString("Mysql.database");

        config.setJdbcUrl("jdbc:mysql://"+host+":"+port+"/"+database+"?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
        config.setUsername(plugin.getConfig().getString("Mysql.username"));
        config.setPassword(plugin.getConfig().getString("Mysql.password"));

        config.setAutoCommit(true);

        hikari = new HikariDataSource(config);
        execute(Query.CREATE_TABLE);
        return hikari;
    }

    //增加
    public void Insert(Object... parameters) {
        try {
            execute(Query.INSERT_USER,parameters);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void Update(Object... parameters) {
        try {
            execute(Query.UPDATE_LOCATION,parameters);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public String getLocation(Object... parameters) throws SQLException {
        ResultSet resultSet = executeQuery(Query.GET_LOCATION,parameters);
        resultSet.next();
        String a = resultSet.getString("LocValue");
        plugin.getLogger().info(a);
        return a;
    }

    //查询
    public boolean hasValue(Object... parameters) {
        try {
            ResultSet resultSet = executeQuery(Query.GET_LOCATION,parameters);
            if (resultSet != null) {
                return resultSet.next();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    //删除 may not

    //抄来的,记得写
    private static void execute(String query, Object... parameters) {

        try (Connection connection = hikari
                .getConnection(); PreparedStatement statement = connection
                .prepareStatement(query)) {

            if (parameters != null) {
                for (int i = 0; i < parameters.length; i++) {
                    statement.setObject(i + 1, parameters[i]);
                }
            }
            statement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    private ResultSet executeQuery(String query, Object... parameters) {
        try (Connection connection = hikari
                .getConnection(); PreparedStatement statement = connection
                .prepareStatement(query)) {
            if (parameters != null) {
                for (int i = 0; i < parameters.length; i++) {
                    statement.setObject(i + 1, parameters[i]);
                }
            }
            //修改原来的方法,自己去找来看吧
            CachedRowSet resultCached = RowSetProvider.newFactory().createCachedRowSet();

            ResultSet resultSet = statement.executeQuery();
            resultCached.populate(resultSet);
            resultSet.close();

            return resultCached;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}

