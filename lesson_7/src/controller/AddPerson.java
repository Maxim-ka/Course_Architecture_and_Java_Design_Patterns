package controller;

import data.Person;
import javafx.scene.control.TextInputDialog;

import java.util.ArrayList;
import java.util.Optional;

public class AddPerson extends Command{

    private Person person;

    AddPerson(Controller controller) {
        super(controller);
        //вывод диалогового окна с текстовым полем ввода
        TextInputDialog inputDialog = new TextInputDialog();
        Optional<String> result = inputDialog.showAndWait();
        if (result.isPresent()){
            String name = result.get();
            person = new Person();
            person.setName(name);
            int id = (controller.listPersons.isEmpty()) ? 1 :
                controller.listPersons.get(controller.listPersons.size() - 1).getId() + 1;
            person.setId(id);
            person.setKeywords(new ArrayList<>());
        }
    }

    @Override
    public void execute(){
        if (person == null) return;
        controller.listPersons.add(person);
        controller.unitOfWork.registerNew(person);
    }
}
