package ua.nure.dao.Observer.DAOListeners;

import ua.nure.dao.Observer.EventListener;
import ua.nure.entity.Delivery;
import ua.nure.entity.Order;

public class DeliveryListener implements EventListener<Delivery> {
    @Override
    public void entityAdded(Delivery entity) {
    }

    @Override
    public void entityRemoved(long id) {
    }

    @Override
    public void entityUpdated(Delivery updatedEntity) {
    }

    @Override
    public void subscribeToEventType(String eventType) {

    }
}
