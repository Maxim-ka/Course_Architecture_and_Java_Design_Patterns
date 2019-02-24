import java.awt.event.ActionEvent;
import java.io.File;

interface Request {
    void sendRequestToServer(Message message);
}

class Message{
    private String command;
    private Message(String command) {
        this.command = command;
    }
    public static class Builder {
        private Message message;

        public Builder(String command) {
            message = new Message(command);
        }

        public Message build() {
            return message;
        }
    }
}

abstract class Command{
    protected Client client;
    protected Controller controller;
    protected FileSendingHandler fileSendingHandler;

    Command(Controller controller, Client client) {
        this.controller = controller;
        this.client = client;
    }

    Message createMessageToServer(String command, File[] catalog) {
        Message.Builder builder = new Message.Builder(command)
                .addNameCatalog(controller.directoryOnServer.getText())
                .addCatalogFile(catalog);
        return builder.build();
    }

    abstract void send();
}

class Copy extends Command{

    Copy(Controller controller, Client client) {
        super(controller, client);
    }

    void send(){
        Message message;
        if (controller.toServer){
            message = fileSendingHandler.actionWithFile(SCM.COPY, controller.currentDirectory.getText(), controller.directoryOnServer.getText(), controller.getSelected());
        }else {
            message = createMessageToServer(SCM.COPY, controller.getSelected());
        }
       client.sendRequestToServer(message);
    }
}

class Relocate extends Command{

    Relocate(Controller controller, Client client) {
        super(controller, client);
    }

    void send(){
        new Copy(controller, client).send();
        new Delete(controller, client).send();
    }
}

class Delete extends Command{

    Delete(Controller controller, Client client) {
        super(controller, client);
    }

    void send(){
        Message message;
        if (controller.toServer){
            message = fileSendingHandler.actionWithFile(SCM.DELETE, controller.currentDirectory.getText(), controller.directoryOnServer.getText(), controller.getSelected());
        }else {
            message = createMessageToServer(SCM.DELETE, controller.getSelected());
        }
        client.sendRequestToServer(message);
    }
}

class Controller {

    Client client = new Client("localhost", 8080);

    public void copy(ActionEvent actionEvent) {
        new Copy(this, client).send();
    }

    public void relocate(ActionEvent actionEvent) {
        new Relocate(this, client).send();
    }

    public void delete(ActionEvent actionEvent) {
        new Delete(this, client).send();
    }
}

class Client implements Request {

    private String host;
    private  int port;

    Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    void connect(){}

    @Override
    public void sendRequestToServer(Message message) {
        f.channel().writeAndFlush(message);
    }
}
