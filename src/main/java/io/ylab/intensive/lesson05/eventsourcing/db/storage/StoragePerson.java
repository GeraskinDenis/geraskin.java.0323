package io.ylab.intensive.lesson05.eventsourcing.db.storage;

import io.ylab.intensive.lesson05.eventsourcing.Person;

import java.util.List;

public interface StoragePerson {

    void deletePerson(Long personId);

    List<Person> findAll();

    Person findPerson(Long personId);

    void savePerson(Person person);


}
