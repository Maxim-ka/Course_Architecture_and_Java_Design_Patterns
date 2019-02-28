package controller;

import data.Person;

class DelKeywords extends Command {

    private String string;
    private Person person;

    DelKeywords(Controller controller, String string, Person person) {
        super(controller);
        this.string = string;
        this.person = person;
    }

    @Override
    void execute() {
        person.getKeywords().remove(string);
        controller.listKeywords.remove(string);
        controller.unitOfWork.registerChanges(person);
    }
}
