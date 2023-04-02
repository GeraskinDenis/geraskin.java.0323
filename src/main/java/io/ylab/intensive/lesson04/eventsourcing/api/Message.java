package io.ylab.intensive.lesson04.eventsourcing.api;

import io.ylab.intensive.lesson04.eventsourcing.Person;
import io.ylab.intensive.lesson04.eventsourcing.api.command.Command;

public class Message {
    private final Person person;
    private final Command command;

    public Message(Person person, Command command) {
        this.person = person;
        this.command = command;
    }

    @Override
    public String toString() {
        String result = "";
        if (command == Command.DELETE) {
            result = String.format("%s;%s", command, person.getId());
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
