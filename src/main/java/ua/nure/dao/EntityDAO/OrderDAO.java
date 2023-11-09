package ua.nure.dao.EntityDAO;

import ua.nure.dao.CRUDRepository;
import ua.nure.entity.Order;
import ua.nure.entity.enums.Status;

import java.util.List;

public interface OrderDAO extends CRUDRepository<Order> {
    List<Order> getOrdersByUserId(Long userId);
    void updateStatus(Long orderId, Status status);

}