import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

interface Observer{
    void add(String string);
    void delete(String string);
}

interface Observable{
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
}

class SheetReference extends JPanel implements Observable{
    private DefaultListModel<String> dataSheet = new DefaultListModel<>();
    private JList<String> list = new JList<>(dataSheet);
    private List<Observer> observerList = new ArrayList<>();

    @Override
    public void addObserver(Observer observer) {
        observerList.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observerList.remove(observer);
    }

    public void toAdd(String string){
        dataSheet.addElement(string);
        observerList.forEach(observer -> observer.add(string));
    }

    public void toRemove(String stringSelect){
        dataSheet.removeElement(stringSelect);
        observerList.forEach(observer -> observer.delete(stringSelect));
    }
}

class SheetReferenceKeywords implements Observer{

    DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
    JComboBox<String> nameOfPerson = new JComboBox<>(comboBoxModel);


    @Override
    public void add(String string) {
        comboBoxModel.addElement(string);
    }

    @Override
    public void delete(String string) {
        comboBoxModel.removeElement(string);
    }

    void output(){
        for (int i = 0; i < comboBoxModel.getSize(); i++) {
            System.out.println(comboBoxModel.getElementAt(i));
        }
    }
}

class Main{

    public static void main(String[] args) {
        SheetReference sheetReference = new SheetReference();
        SheetReferenceKeywords keywords = new SheetReferenceKeywords();
        sheetReference.addObserver(keywords);
        sheetReference.toAdd("1");
        sheetReference.toAdd("2");
        sheetReference.toAdd("3");
        sheetReference.toRemove("2");
        keywords.output();
    }
}