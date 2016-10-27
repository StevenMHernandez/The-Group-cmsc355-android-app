package thegroup.snakego.interfaces;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public interface Listenable {
    List<PropertyChangeListener> listeners = new ArrayList<>();

    void notifyListeners(Object object, String property, Object oldValue, Object newValue);

    void addChangeListener(PropertyChangeListener newListener);
}
