package controller;

import data.Person;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class AddKeywords extends Command{

    private Person person;
    private String string;

    AddKeywords(Controller controller, Person person) {
        super(controller);
        this.person = person;
        //вывод диалогового окна с текстовым полем ввода
        if(person != null){
            TextInputDialog inputDialog = new TextInputDialog();
            Optional<String> result = inputDialog.showAndWait();
            result.ifPresent(s -> string = s);
        }
    }

    @Override
    public void execute(){
       if (person == null || string == null) return;
       person.getKeywords().add(string);
       controller.listKeywords.add(string);
       controller.unitOfWork.registerChanges(person);
    }
}
