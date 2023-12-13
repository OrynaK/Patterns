package ua.nure.dao.proxy;

import ua.nure.dao.EntityDAO.OrderDAO;
import ua.nure.entity.Delivery;
import ua.nure.entity.Order;
import ua.nure.entity.enums.Role;
import ua.nure.entity.enums.Status;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ProxyOrderDAO {
    private OrderDAO orderDAO;

    public ProxyOrderDAO(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public long add(Order order, Role role) {
        //User може додавати замовлення,
        // тому перевірку на алміна не робимо
        return orderDAO.add(order);
    }

    public Order update(Order updatedOrder, Role role) {
        if (ProxyManager.isAdmin(role)) {
            return orderDAO.update(updatedOrder);
        }
        return null;
    }

    public void delete(long id, Role role) {
        if (ProxyManager.isAdmin(role)) {
            orderDAO.delete(id);
        }
    }

    public Order findById(long id) {
        return orderDAO.findById(id);
    }

    public List<Order> findAll() {
        return orderDAO.findAll();
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderDAO.getOrdersByUserId(userId);
    }

    public void updateStatus(Long orderId, Status status, Role role) {
        //User може тільки відхилити замовлення,
        // тобто встановити статус "Denied",
        // тому перевіримо спочатку який статус намагаються встановити
        if (status != Status.DENIED) {
            if (ProxyManager.isAdmin(role)) {
                orderDAO.updateStatus(orderId, status);
            }
        } else {
            orderDAO.updateStatus(orderId, status);
        }
    }
}
