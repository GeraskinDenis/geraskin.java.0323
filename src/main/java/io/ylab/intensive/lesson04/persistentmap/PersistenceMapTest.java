package io.ylab.intensive.lesson04.persistentmap;

import io.ylab.intensive.lesson04.DbUtil;

import javax.sql.DataSource;
import java.sql.SQLException;

public class PersistenceMapTest {
    public static void main(String[] args) throws SQLException {
        DataSource dataSource = initDb();
        PersistentMap persistentMap = new PersistentMapImpl(dataSource);
        persistentMap.init("aa");
        persistentMap.put("KeyString1", "StringVolume1");
        persistentMap.put("KeyString2", "StringVolume2");
        persistentMap.put("KeyString3", "StringVolume3");

        System.out.println("=== getKeys() ===");
        persistentMap.getKeys().forEach(System.out::println);

        System.out.println("=== containsKey(\"KeyString2\") ===");
        System.out.println(persistentMap.containsKey("KeyString2"));

        System.out.println("=== containsKey(\"KeyString200\") ===");
        System.out.println(persistentMap.containsKey("KeyString200"));

        System.out.println("=== get(\"KeyString2\") ===");
        System.out.println(persistentMap.get("KeyString2"));

        persistentMap.remove("KeyString2");
        System.out.println("=== getKeys() after remove(\"KeyString2\") ===");
        persistentMap.getKeys().forEach(System.out::println);

        persistentMap.clear();
        System.out.println("=== getKeys() after clear() ===");
        persistentMap.getKeys().forEach(System.out::println);

        persistentMap.init("aa");
        persistentMap.put("KeyString1", "StringVolume1");
        persistentMap.put("KeyString2", "StringVolume2");
        persistentMap.put("KeyString3", "StringVolume3");
    }

    public static DataSource initDb() throws SQLException {
        String createMapTable = ""
                + "drop table if exists persistent_map; "
                + "CREATE TABLE if not exists persistent_map (\n"
                + "   map_name varchar,\n"
                + "   KEY varchar,\n"
                + "   value varchar\n"
                + ");";
        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(createMapTable, dataSource);
        return dataSource;
    }
}
