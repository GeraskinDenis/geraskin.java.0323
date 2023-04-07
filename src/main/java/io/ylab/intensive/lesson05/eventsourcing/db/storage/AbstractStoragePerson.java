package io.ylab.intensive.lesson05.eventsourcing.db.storage;

import io.ylab.intensive.lesson05.eventsourcing.Person;

import java.util.List;

public abstract class AbstractStoragePerson implements StoragePerson {

    @Override
    public void deletePerson(Long personId) {
        doDeletePerson(personId);
    }

    @Override
    public List<Person> findAll() {
        return doFindAll();
    }

    @Override
    public Person findPerson(Long personId) {
        return doFindPerson(personId);
    }

    @Override
    public void savePerson(Person person) {
        if (isExistPerson(person.getId())) {
            updatePerson(person);
        } else {
            doSavePerson(person);
        }
    }

    private void updatePerson(Person person) {
        doUpdatePerson(person);
    }

    protected abstract void doDeletePerson(Long personId);

    protected abstract List<Person> doFindAll();

    protected abstract Person doFindPerson(Long personId);

    protected abstract void doSavePerson(Person person);

    protected abstract void doUpdatePerson(Person person);

    protected abstract boolean isExistPerson(Long personId);
}
