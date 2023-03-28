package yumcraft.getofflineloc.Sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author: Ayolk
 * @create: 2023-03-28 20:46
 * @url: github.com/AyolK0327/GetOfflineLoc
 */
public class sql {
    private HikariDataSource ds;
    public void ConnectSql() throws SQLException {
        HikariConfig config = new HikariConfig();
        config.setConnectionTimeout(30000);
        config.setMinimumIdle(10);
        config.setMaximumPoolSize(10);

        config.setJdbcUrl("jdbc:mysql://localhost:3306/test?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
        config.setUsername("root");
        config.setPassword("1234567890");

        config.setAutoCommit(true);

        ds = new HikariDataSource(config);
        Connection connection = ds.getConnection();
    }
}
