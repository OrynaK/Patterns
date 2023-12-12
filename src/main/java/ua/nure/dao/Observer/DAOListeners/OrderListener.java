package ua.nure.dao.Observer.DAOListeners;

import ua.nure.dao.Observer.EventListener;
import ua.nure.entity.Order;

public class OrderListener implements EventListener<Order> {
    @Override
    public void entityAdded(Order entity) {
    }

    @Override
    public void entityRemoved(long id) {
    }

    @Override
    public void entityUpdated(Order updatedEntity) {
    }

    @Override
    public void subscribeToEventType(String eventType) {

    }
}

