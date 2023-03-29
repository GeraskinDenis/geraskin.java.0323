package geraskindenis.hw4.eventsourcing.api;

import geraskindenis.hw4.eventsourcing.Person;
import geraskindenis.hw4.eventsourcing.api.command.Command;
import geraskindenis.hw4.eventsourcing.db.DbApp;

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
