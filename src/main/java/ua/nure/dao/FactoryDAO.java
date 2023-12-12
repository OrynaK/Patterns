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
    public UserDAO getUserDAO(EventManager eventManager) {
        Connection connection = connectionManager.getConnection();
        return new UserDAOImpl(connection, eventManager);
    }

    @Override
    public ClothingDAO getClothingDAO(EventManager eventManager) {
        Connection connection = connectionManager.getConnection();
        return new ClothingDAOImpl(connection, eventManager);
    }

    @Override
    public OrderDAO getOrderDAO(EventManager eventManager) {
        Connection connection = connectionManager.getConnection();
        return new OrderDAOImpl(connection, eventManager);
    }

    @Override
    public DeliveryDAO getDeliveryDAO(EventManager eventManager) {
        Connection connection = connectionManager.getConnection();
        return new DeliveryDAOImpl(connection, eventManager);
    }
}
