package geraskindenis.hw4.eventsourcing.api;

import geraskindenis.hw4.eventsourcing.Person;
import geraskindenis.hw4.eventsourcing.api.command.Command;

public class Message {
    private final Person person;
    private final Command command;

    public Message(Person person, Command command) {
        this.person = person;
        this.command = command;
    }

    @Override
    public String toString() {
        if (command == Command.DELETE)
            return String.format("%s;%s", command, person.getId());
        else if (command == Command.SAVE) {
            return String.format("%s;%s;%s;%s;%s",
                    command,
                    person.getId(),
                    person.getFirstName(),
                    person.getLastName(),
                    person.getMiddleName());
        }
        return "";
    }

    public byte[] getBytes() {
        return toString().getBytes();
    }
}
