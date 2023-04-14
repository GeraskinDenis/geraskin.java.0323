package io.ylab.intensive.lesson03.datedmap;

import java.util.*;

public class DateMapImpl implements DateMap {
    private final Map<String, Object[]> dataMap = new HashMap<>();

    @Override
    public void put(String key, String value) {
        dataMap.put(key, new Object[]{value, new Date()});
    }

    @Override
    public String get(String key) {
        Object[] result = dataMap.get(key);
        return Objects.isNull(result) ? null : (String) result[0];
    }

    @Override
    public boolean containsKey(String key) {
        return dataMap.containsKey(key);
    }

    @Override
    public void remove(String key) {
        dataMap.remove(key);
    }

    @Override
    public Set<String> keySet() {
        return dataMap.keySet();
    }

    @Override
    public Date getKeyLastLastInsertionDate(String key) {
        Object[] result = dataMap.get(key);
        return Objects.isNull(result) ? null : (Date) result[1];
    }
}
