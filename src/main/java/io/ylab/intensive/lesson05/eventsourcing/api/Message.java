package io.ylab.intensive.lesson05.eventsourcing.api;

import io.ylab.intensive.lesson05.eventsourcing.Person;
import io.ylab.intensive.lesson05.eventsourcing.api.command.Command;

public class Message {
    private final Command command;
    private final Long personId;
    private final Person person;


    public Message(Command command, Long personId) {
        this.command = command;
        this.personId = personId;
        this.person = null;
    }

    public Message(Command command, Person person) {
        this.command = command;
        this.personId = person.getId();
        this.person = person;
    }

    @Override
    public String toString() {
        String result = "";
        if (command == Command.DELETE) {
            result = String.format("%s;%s", command, personId);
        } else if (command == Command.SAVE) {
            result = String.format("%s;%s;%s;%s;%s",
                    command,
                    person.getId(),
                    person.getFirstName(),
                    person.getLastName(),
                    person.getMiddleName());
        }
        return result;
    }

    public byte[] getBytes() {
        return toString().getBytes();
    }
}
