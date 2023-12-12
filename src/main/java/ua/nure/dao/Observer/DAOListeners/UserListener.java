package ua.nure.dao.Observer.DAOListeners;

import ua.nure.dao.Observer.EventListener;
import ua.nure.entity.Clothing;
import ua.nure.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserListener implements EventListener<User> {

    private List<String> subscribedEventTypes = new ArrayList<>();

    @Override
    public void entityAdded(User entity) {
        System.out.println("It's your User subscription! Added: " + entity);
    }

    @Override
    public void entityRemoved(long id) {
        System.out.println("It's your User subscription! Removed with ID: " + id);
    }

    @Override
    public void entityUpdated(User updatedEntity) {
        System.out.println("It's your User subscription! Updated: " + updatedEntity);
    }
    @Override
    public void subscribeToEventType(String eventType) {
        subscribedEventTypes.add(eventType);
    }
}

