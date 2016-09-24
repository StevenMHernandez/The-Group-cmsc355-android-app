package thegroup.snakego.Utils;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class DefaultListenableActions {
    public List<PropertyChangeListener> listeners = new ArrayList<>();

    public void notifyListeners(Object object, String property, Object oldValue, Object newValue) {
        for (PropertyChangeListener name : listeners) {
            name.propertyChange(new PropertyChangeEvent(object, property, oldValue, newValue));
        }
    }

    public void addChangeListener(PropertyChangeListener newListener) {
        listeners.add(newListener);
    }
}
