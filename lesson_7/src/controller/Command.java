package controller;

abstract class Command {

    protected Controller controller;

    protected Command(Controller controller) {
        this.controller = controller;
    }

    abstract void execute();
}
