package io.ylab.intensive.lesson05.eventsourcing.db;

import io.ylab.intensive.lesson05.eventsourcing.Person;
import io.ylab.intensive.lesson05.eventsourcing.db.storage.StoragePersonImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DataProcessing {

    private final StoragePersonImpl storagePerson;

    @Autowired
    public DataProcessing(StoragePersonImpl storagePerson) {
        this.storagePerson = storagePerson;
    }

    public void messageProcessing(String message) {
        Objects.requireNonNull(message);
        String[] strings = message.split(";");
        if (strings[0].equals("delete")) {
            dataDelete(strings);
        } else if (strings[0].equals("save")) {
            dataSave(strings);
        } else {
            System.out.println(message + " - unidentified message");
        }
    }

    private void dataDelete(String[] strings) {
        if (strings.length != 2) {
            System.out.println("Data format error!");
        }
        Long personId = Long.parseLong(strings[1]);
        storagePerson.deletePerson(personId);
    }

    private void dataSave(String[] strings) {
        if (strings.length != 5) {
            System.out.println("Data format error!");
        }
        Person person = new Person(Long.parseLong(strings[1]),
                strings[2],
                strings[3],
                strings[4]);
        storagePerson.savePerson(person);
    }
}
