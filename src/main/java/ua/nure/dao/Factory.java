package ua.nure.dao;

import ua.nure.dao.EntityDAO.ClothingDAO;
import ua.nure.dao.EntityDAO.DeliveryDAO;
import ua.nure.dao.EntityDAO.OrderDAO;
import ua.nure.dao.EntityDAO.UserDAO;
import ua.nure.dao.Observer.EventManager;
import ua.nure.entity.Clothing;
import ua.nure.entity.User;

public interface Factory {
    UserDAO getUserDAO(EventManager<User> clothingEventManager);

    ClothingDAO getClothingDAO(EventManager<Clothing> userEventManager);

    OrderDAO getOrderDAO();

    DeliveryDAO getDeliveryDAO();
}
