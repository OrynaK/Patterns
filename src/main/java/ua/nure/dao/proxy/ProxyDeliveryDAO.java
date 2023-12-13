package ua.nure.dao.proxy;

import ua.nure.dao.EntityDAO.ClothingDAO;
import ua.nure.dao.EntityDAO.DeliveryDAO;
import ua.nure.entity.Clothing;
import ua.nure.entity.Delivery;
import ua.nure.entity.enums.Role;
import ua.nure.entity.enums.Size;

import java.util.List;

public class ProxyDeliveryDAO {
    private DeliveryDAO deliveryDAO;

    public ProxyDeliveryDAO(DeliveryDAO clothingDAO) {
        this.deliveryDAO = clothingDAO;
    }

    public long add(Delivery delivery, Role role) {
        if (ProxyManager.isAdmin(role)) {
            return deliveryDAO.add(delivery);
        }
        return 0;
    }

    public Delivery update(Delivery updatedDelivery, Role role) {
        if (ProxyManager.isAdmin(role)) {
            return deliveryDAO.update(updatedDelivery);
        }
        return null;
    }

    public void delete(long id, Role role) {
        if (ProxyManager.isAdmin(role)) {
            deliveryDAO.delete(id);
        }
    }

    public Delivery findById(long id) {
        return deliveryDAO.findById(id);
    }

    public List<Delivery> findAll() {
        return deliveryDAO.findAll();
    }
    public Delivery updateByOrderId(Delivery delivery, long orderId, Role role){
        if (ProxyManager.isAdmin(role)) {
            return deliveryDAO.updateByOrderId(delivery, orderId);
        }
        return null;
    }
}
