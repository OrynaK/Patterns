package ua.nure.dao;

import ua.nure.dao.EntityDAO.ClothingDAO;
import ua.nure.dao.EntityDAO.DeliveryDAO;
import ua.nure.dao.EntityDAO.OrderDAO;
import ua.nure.dao.EntityDAO.UserDAO;
import ua.nure.dao.EntityDAOImpl.ClothingDAOImpl;
import ua.nure.dao.EntityDAOImpl.DeliveryDAOImpl;
import ua.nure.dao.EntityDAOImpl.OrderDAOImpl;
import ua.nure.dao.EntityDAOImpl.UserDAOImpl;

import java.sql.Connection;

public class FactoryDAO implements Factory {
    private ConnectionManager connectionManager;

    public FactoryDAO() {
        ConnectionProperties connectionProperties = new ConnectionProperties();
        connectionManager = ConnectionManager.getInstance(connectionProperties);
    }

    @Override
    public UserDAO getUserDAO() {
        Connection connection = connectionManager.getConnection();
        return new UserDAOImpl(connection);
    }

    @Override
    public ClothingDAO getClothingDAO() {
        Connection connection = connectionManager.getConnection();
        return new ClothingDAOImpl(connection);
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
