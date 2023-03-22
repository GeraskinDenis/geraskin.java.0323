package geraskindenis.hw3.datedmap;

import java.util.Date;
import java.util.Set;

public interface DateMap {
    void put(String key, String value);

    String get(String kay);

    boolean containsKey(String key);

    void remove(String key);

    Set<String> keySet();

    Date getKeyLastLastInsertionDate(String key);
}
