package data;

import java.util.ArrayList;
import java.util.List;

public class UnitOfWork {

    private final List<Person> newPerson = new ArrayList<>();
    private final List<Person> modifiedPerson = new ArrayList<>();
    private final List <Person> removedPerson = new ArrayList<>();
    private final PersonMapper personMapper;

    public UnitOfWork(PersonMapper personMapper) {
        this.personMapper = personMapper;
    }

    public void registerNew(Person person){
        if (!removedPerson.isEmpty() && removedPerson.contains(person)) removedPerson.remove(person);
        newPerson.add(person);
    }

    public void registerChanges(Person person){
        if (!newPerson.isEmpty() && newPerson.contains(person)) newPerson.remove(person);
        if (modifiedPerson.isEmpty()){
            modifiedPerson.add(person);
            return;
        }
        for (int i = 0; i < modifiedPerson.size(); i++) {
            if (modifiedPerson.get(i).getId() == person.getId()){
                modifiedPerson.remove(i);
                modifiedPerson.add(i, person);
                return;
            }
        }
    }

    public void registerRemoved(Person person){
        if (!newPerson.isEmpty() && newPerson.contains(person)) newPerson.remove(person);
        for (int i = 0; i < modifiedPerson.size(); i++) {
            if (modifiedPerson.get(i).getId() == person.getId()){
                modifiedPerson.remove(i);
            }
        }
        removedPerson.add(person);
    }

    private void insertNew(){
        newPerson.forEach(personMapper::save);
    }

    private void updatePerson(){
        modifiedPerson.forEach(personMapper::update);
    }

    private void deleteRemoved(){
        removedPerson.forEach(personMapper::delete);
    }

    public void commit(){
        if (!newPerson.isEmpty()) insertNew();
        if (!modifiedPerson.isEmpty())updatePerson();
        if (!removedPerson.isEmpty())deleteRemoved();
        clear();
    }

    private void clear(){
        newPerson.clear();
        modifiedPerson.clear();
        removedPerson.clear();
    }
}
