package thegroup.snakego.Utils;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public interface Listenable {
    List<PropertyChangeListener> listener = new ArrayList<>();

    void notifyListeners(Object object, String property, Object oldValue, Object newValue);

    void addChangeListener(PropertyChangeListener newListener);
}
