package ua.nure.dao.proxy;

import ua.nure.dao.EntityDAO.ClothingDAO;
import ua.nure.dao.EntityDAO.UserDAO;
import ua.nure.entity.Clothing;
import ua.nure.entity.User;
import ua.nure.entity.enums.Role;
import ua.nure.entity.enums.Size;

import java.util.List;

public class ProxyClothingDAO {
    private ClothingDAO clothingDAO;

    public ProxyClothingDAO(ClothingDAO clothingDAO) {
        this.clothingDAO = clothingDAO;
    }

    public long add(Clothing newClothing, Role role) {
        if (ProxyManager.isAdmin(role)) {
            return clothingDAO.add(newClothing);
        }
        return 0;
    }

    public Clothing update(Clothing updatedClothing, Role role) {
        if (ProxyManager.isAdmin(role)) {
            return clothingDAO.update(updatedClothing);
        }
        return null;
    }

    public void delete(long id, Role role) {
        if (ProxyManager.isAdmin(role)) {
            clothingDAO.delete(id);
        }
    }

    public Clothing findById(long id) {
        return clothingDAO.findById(id);
    }

    public List<Clothing> findAll() {
        return clothingDAO.findAll();
    }

    public List<Clothing> getClothingBySize(Size size, Role role) {
        return clothingDAO.getClothingBySize(size);
    }
    public void updateClothingAmount(long clothingId, int amount, Role role){
        if (ProxyManager.isAdmin(role)) {
            clothingDAO.updateClothingAmount(clothingId, amount);
        }
    }
}
