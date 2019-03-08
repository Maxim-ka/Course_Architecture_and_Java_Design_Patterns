package controller;

import data.Person;

public class DelPerson extends Command{

    private Person person;

    DelPerson(Controller controller, Person person) {
        super(controller);
        this.person = person;
    }

    @Override
    public void execute(){
        controller.listPersons.remove(person);
        controller.unitOfWork.registerRemoved(person);
    }
}
