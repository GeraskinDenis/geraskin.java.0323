package io.ylab.intensive.lesson03.datedmap;

public class Demo {
    public static void main(String[] args) {
        DateMap dateMap = new DateMapImpl();
        dateMap.put("firstName", "Denis");
        dateMap.put("secondName", "Geraskin");
        System.out.println(dateMap.get("firstName"));
        System.out.println(dateMap.getKeyLastLastInsertionDate("firstName"));
        System.out.println(dateMap.get("notExist"));
    }
}
