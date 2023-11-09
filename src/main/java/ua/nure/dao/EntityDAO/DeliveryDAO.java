package ua.nure.dao.EntityDAO;

import ua.nure.dao.CRUDRepository;
import ua.nure.entity.Delivery;

public interface DeliveryDAO extends CRUDRepository<Delivery> {

    Delivery updateByOrderId(Delivery delivery, long order_id);
}
