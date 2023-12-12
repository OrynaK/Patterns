package ua.nure.dao.Observer.DAOListeners;

import ua.nure.dao.Observer.EventListener;
import ua.nure.entity.Delivery;
import ua.nure.entity.Order;
import ua.nure.entity.User;

import java.util.ArrayList;
import java.util.List;

public class DeliveryListener implements EventListener<Delivery> {

    @Override
    public void entityAdded(Delivery entity) {
        System.out.println("It's your subscription! Added: " + entity);
    }

    @Override
    public void entityRemoved(long id) {
        System.out.println("It's your subscription! Delivery removed with ID: " + id);
    }

    @Override
    public void entityUpdated(Delivery updatedEntity) {
        System.out.println("It's your subscription! Updated: " + updatedEntity);
    }
}
