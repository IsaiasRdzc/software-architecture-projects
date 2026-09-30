package Model;

import java.util.ArrayList;
import java.util.List;
import View.View;
public abstract class Model {

    private List<View> views = new ArrayList<>();


    public void addView(View view) {
        views.add(view);
    }

    protected void notifyChange(Object newData) {
        for (View view : views) {
            view.update(newData);
        }
    }

}
