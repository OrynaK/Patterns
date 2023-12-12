package ua.nure.dao.Observer;

import ua.nure.entity.User;

import java.util.*;

public class EventManager {

    private Map<String, List<EventListener>> listeners = new HashMap<>();

    public void unsubscribe(String eventType, EventListener listener) {
        List<EventListener> eventListeners = listeners.get(eventType);
        if (eventListeners != null) {
            eventListeners.remove(listener);
        }
    }

    public void subscribe(String eventType, EventListener listener) {
        listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
    }

    public void notifyEntityAdded(String eventType, Object entity) {
        List<EventListener> eventListeners = listeners.get(eventType);
        if (eventListeners != null) {
            for (EventListener listener : eventListeners) {
                listener.entityAdded(entity);
            }
        }
    }

    public void notifyEntityRemoved(String eventType, long id) {
        List<EventListener> eventListeners = listeners.get(eventType);
        if (eventListeners != null) {
            for (EventListener listener : eventListeners) {
                listener.entityRemoved(id);
            }
        }
    }

    public void notifyEntityUpdated(String eventType, Object updatedEntity) {
        List<EventListener> eventListeners = listeners.get(eventType);
        if (eventListeners != null) {
            for (EventListener listener : eventListeners) {
                listener.entityUpdated(updatedEntity);
            }
        }
    }
}
