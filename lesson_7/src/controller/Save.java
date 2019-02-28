package controller;

public class Save extends Command{

    Save(Controller controller) {
        super(controller);
    }

    @Override
    public void execute(){
        controller.unitOfWork.commit();
    }
}
