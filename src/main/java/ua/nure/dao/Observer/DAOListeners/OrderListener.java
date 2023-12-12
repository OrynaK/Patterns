package ua.nure.dao.Observer.DAOListeners;

import ua.nure.dao.Observer.EventListener;
import ua.nure.entity.Order;
import ua.nure.entity.User;

import java.util.ArrayList;
import java.util.List;

public class OrderListener implements EventListener<Order> {

    @Override
    public void entityAdded(Order entity) {
        System.out.println("It's your subscription! Added: " + entity);
    }

    @Override
    public void entityRemoved(long id) {
        System.out.println("It's your subscription! Order removed with ID: " + id);
    }

    @Override
    public void entityUpdated(Order updatedEntity) {
        System.out.println("It's your subscription! Updated: " + updatedEntity);
    }
}

