package ua.nure.dao.Observer;

import ua.nure.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager<T> {

    private Map<String, List<EventListener<T>>> listeners = new HashMap<>();

    public void unsubscribe(String eventType, EventListener<T> listener) {
        List<EventListener<T>> eventListeners = listeners.get(eventType);
        if (eventListeners != null) {
            eventListeners.remove(listener);
        }
    }

    public void subscribe(String eventType, EventListener<T> listener) {
        listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
    }

    public void notifyEntityAdded(String eventType, T entity) {
        List<EventListener<T>> eventListeners = listeners.get(eventType);
        if (eventListeners != null) {
            for (EventListener<T> listener : eventListeners) {
                listener.entityAdded(entity);
            }
        }
    }

    public void notifyEntityRemoved(String eventType, long id) {
        List<EventListener<T>> eventListeners = listeners.get(eventType);
        if (eventListeners != null) {
            for (EventListener<T> listener : eventListeners) {
                listener.entityRemoved(id);
            }
        }
    }

    public void notifyEntityUpdated(String eventType, T updatedEntity) {
        List<EventListener<T>> eventListeners = listeners.get(eventType);
        if (eventListeners != null) {
            for (EventListener<T> listener : eventListeners) {
                listener.entityUpdated(updatedEntity);
            }
        }
    }
}
