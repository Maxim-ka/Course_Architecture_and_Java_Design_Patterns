package controller;

import data.Person;
import data.PersonMapper;
import data.UnitOfWork;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.SingleSelectionModel;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private ListView<Person> persons;

    @FXML
    private ListView<String> keywords;

    @FXML
    private ComboBox<Person> person;

    private Connection connection;
    final UnitOfWork unitOfWork = new UnitOfWork(new PersonMapper(connection));
    final ObservableList<Person> listPersons = FXCollections.observableArrayList();
    final ObservableList<String> listKeywords = FXCollections.observableArrayList();
    private SelectionModel<Person> personSelectionModel;
    private SingleSelectionModel<Person> singleSelectionModel;
    private SelectionModel<String> keywordsSelectionModel;

    @FXML
    private void addPerson(ActionEvent actionEvent) {
        executeCommand(new AddPerson(this));
    }

    @FXML
    private void delPerson(ActionEvent actionEvent) {
        executeCommand(new DelPerson(this, personSelectionModel.getSelectedItem()));
    }

    @FXML
    private void save(ActionEvent actionEvent) {
        executeCommand(new Save(this));
    }

    @FXML
    private void addKeywords(ActionEvent actionEvent) {
        executeCommand(new AddKeywords(this, singleSelectionModel.getSelectedItem()));
    }

    @FXML
    private void delKeywords(ActionEvent actionEvent) {
        executeCommand(new DelKeywords(this, keywordsSelectionModel.getSelectedItem(), singleSelectionModel.getSelectedItem()));
    }

    private void executeCommand(Command command){
        command.execute();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        persons.setItems(listPersons);
        keywords.setItems(listKeywords);
        person.setItems(listPersons);
        personSelectionModel = persons.getSelectionModel();
        singleSelectionModel = person.getSelectionModel();
        keywordsSelectionModel = keywords.getSelectionModel();
        personSelectionModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            singleSelectionModel.select(newValue);
        });
        singleSelectionModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            listKeywords.clear();
            if (newValue != null && newValue.getKeywords() != null) listKeywords.addAll(newValue.getKeywords());
        });
    }
}
