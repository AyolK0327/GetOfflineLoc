package yumcraft.getofflineloc.Sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
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
    private static HikariDataSource hikari;

    public HikariDataSource getHikari(){
        HikariConfig config = new HikariConfig();
        config.setConnectionTimeout(30000);
        config.setMinimumIdle(10);
        config.setMaximumPoolSize(10);

        config.setJdbcUrl("jdbc:mysql://localhost:3306/test?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
        config.setUsername("root");
        config.setPassword("1234567890");

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

