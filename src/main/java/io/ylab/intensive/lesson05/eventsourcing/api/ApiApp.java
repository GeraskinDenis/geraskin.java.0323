package io.ylab.intensive.lesson05.eventsourcing.api;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApiApp {
    public static void main(String[] args) throws Exception {
        // Тут пишем создание PersonApi, запуск и демонстрацию работы
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();
        PersonApi personApi = applicationContext.getBean(PersonApi.class);

        // пишем взаимодействие с PersonApi
        personApi.savePerson(1L, "FristName1", "LastName1", "MiddleName1");
        personApi.savePerson(2L, "FristName2", "LastName2", "MiddleName2");
        personApi.savePerson(3L, "FristName3", "LastName3", "MiddleName3");
        personApi.savePerson(4L, "FristName4", "LastName4", "MiddleName4");

        personApi.findAll().forEach(System.out::println);
//
//        System.out.println("personApi.deletePerson(4L);");
//        personApi.deletePerson(4L);
//
//        personApi.findAll().forEach(System.out::println);
//
//        System.out.println("personApi.findPerson(1L)");
//        System.out.println(personApi.findPerson(1L));
//
//        personApi.findAll().forEach(System.out::println);
    }
}
