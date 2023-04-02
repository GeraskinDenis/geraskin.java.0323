package io.ylab.intensive.lesson04.eventsourcing.api;

import io.ylab.intensive.lesson04.eventsourcing.Person;
import io.ylab.intensive.lesson04.eventsourcing.api.command.Command;
import io.ylab.intensive.lesson04.eventsourcing.db.DbApp;

import java.util.List;

/**
 * Тут пишем реализацию
 */
public class PersonApiImpl implements PersonApi {

    @Override
    public void deletePerson(Long personId) {
        ApiApp.sendMessage(new Message(new Person(personId), Command.DELETE));

    }

    @Override
    public void savePerson(Long personId, String firstName, String lastName, String middleName) {
        Person p = new Person(personId, firstName, lastName, middleName);
        ApiApp.sendMessage(new Message(p, Command.SAVE));
    }

    @Override
    public Person findPerson(Long personId) {
        return DbApp.findPerson(personId);
    }

    @Override
    public List<Person> findAll() {
        return DbApp.findAll();
    }
}
