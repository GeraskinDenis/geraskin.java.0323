package geraskindenis.hw4.eventsourcing.api.command;

public enum Command {
    SAVE("save"),

    DELETE("delete");

    private final String commandName;

    Command(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }

    @Override
    public String toString() {
        return commandName;
    }
}
