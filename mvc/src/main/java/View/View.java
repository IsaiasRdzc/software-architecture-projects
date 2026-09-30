package View;
import Controller.Controller;
public abstract class View {

    protected Controller controller;

    public View(Controller controller) {
        this.controller = controller;
    }

    public abstract void update(Object newData);

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Controller getController() {
        return controller;
    }
}
