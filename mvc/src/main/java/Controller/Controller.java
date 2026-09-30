package Controller;
import Model.Model;

public abstract class Controller {
    protected Model model;

    public Controller(Model model) {
        this.model = model;

    }

    public abstract void handdleEvent(Object event);

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = this.model;
    }

}
