package yumcraft.getofflineloc.Sql;

import com.sun.rowset.CachedRowSetImpl;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.rowset.CachedRowSet;
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
    private HikariDataSource hikari;
    public void ConnectSql() throws SQLException {
        HikariConfig config = new HikariConfig();
        config.setConnectionTimeout(30000);
        config.setMinimumIdle(10);
        config.setMaximumPoolSize(10);

        config.setJdbcUrl("jdbc:mysql://localhost:3306/test?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
        config.setUsername("root");
        config.setPassword("1234567890");

        config.setAutoCommit(true);

        hikari = new HikariDataSource(config);
        Connection connection = hikari.getConnection();
    }

    public void test(){

    }

    //抄来的,记得写
    private void execute(String query, Object... parameters) {

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

            CachedRowSet resultCached = new CachedRowSetImpl();
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

