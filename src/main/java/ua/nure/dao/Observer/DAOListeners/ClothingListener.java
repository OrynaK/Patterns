package ua.nure.dao.Observer.DAOListeners;

import ua.nure.dao.Observer.EventListener;
import ua.nure.entity.Clothing;
import ua.nure.entity.User;

import java.util.ArrayList;
import java.util.List;

public class ClothingListener implements EventListener<Clothing> {
    private List<String> subscribedEventTypes = new ArrayList<>();

    @Override
    public void entityAdded(Clothing entity) {
        System.out.println("It's your subscription! added: " + entity);
    }

    @Override
    public void entityRemoved(long id) {
        System.out.println("It's your subscription!  removed with ID: " + id);
    }

    @Override
    public void entityUpdated(Clothing updatedEntity) {
        System.out.println("It's your subscription! updated: " + updatedEntity);
    }
    @Override
    public void subscribeToEventType(String eventType) {
        subscribedEventTypes.add(eventType);
    }
}
