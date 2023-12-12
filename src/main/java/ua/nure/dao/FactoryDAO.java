package ua.nure.dao;

import ua.nure.dao.EntityDAO.ClothingDAO;
import ua.nure.dao.EntityDAO.DeliveryDAO;
import ua.nure.dao.EntityDAO.OrderDAO;
import ua.nure.dao.EntityDAO.UserDAO;
import ua.nure.dao.EntityDAOImpl.ClothingDAOImpl;
import ua.nure.dao.EntityDAOImpl.DeliveryDAOImpl;
import ua.nure.dao.EntityDAOImpl.OrderDAOImpl;
import ua.nure.dao.EntityDAOImpl.UserDAOImpl;
import ua.nure.dao.Observer.EventManager;
import ua.nure.entity.Clothing;
import ua.nure.entity.User;

import java.sql.Connection;

public class FactoryDAO implements Factory {
    private ConnectionManager connectionManager;

    public FactoryDAO() {
        ConnectionProperties connectionProperties = new ConnectionProperties();
        connectionManager = ConnectionManager.getInstance(connectionProperties);
    }

    @Override
    public UserDAO getUserDAO(EventManager userEventManager) {
        Connection connection = connectionManager.getConnection();
        return new UserDAOImpl(connection, userEventManager);
    }

    @Override
    public ClothingDAO getClothingDAO(EventManager clothingEventManager) {
        Connection connection = connectionManager.getConnection();
        return new ClothingDAOImpl(connection, clothingEventManager);
    }

    @Override
    public OrderDAO getOrderDAO() {
        Connection connection = connectionManager.getConnection();
        return new OrderDAOImpl(connection);
    }

    @Override
    public DeliveryDAO getDeliveryDAO() {
        Connection connection = connectionManager.getConnection();
        return new DeliveryDAOImpl(connection);
    }
}
