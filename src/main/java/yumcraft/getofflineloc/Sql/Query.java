package yumcraft.getofflineloc.Sql;

/**
 * @author: Ayolk
 * @create: 2023-03-28 23:20
 * @url: github.com/AyolK0327/GetOfflineLoc
 */
 public class Query {

    //新增
    static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `GetOfflineLoc` (" +
            "uuid VARCHAR(255) NOT NULL PRIMARY KEY NOT NULL ," +
            "LocValue VARCHAR(255))";
    //新增
    static final String INSERT_USER = "INSERT IGNORE INTO `GetOfflineLoc` (uuid, LocValue) VALUES(?, ?)";

    static final String EXIST_CHECK = "SELECT uuid from `GetOfflineLoc` WHERE uuid=?";
    //更新
    static final String UPDATE_LOCATION = "UPDATE `GetOfflineLoc` set LocValue=? WHERE uuid=?";

    static final String GET_LOCATION = "SELECT `LocValue` FROM `GetOfflineLoc` WHERE uuid=?";

}