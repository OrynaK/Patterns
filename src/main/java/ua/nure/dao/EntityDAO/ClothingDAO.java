package ua.nure.dao.EntityDAO;

import ua.nure.dao.CRUDRepository;
import ua.nure.entity.Clothing;
import ua.nure.entity.enums.Size;

import java.util.List;

public interface ClothingDAO extends CRUDRepository<Clothing> {
    List<Clothing> getClothingBySize(Size size);
    void updateClothingAmount(long shoeId, int amount);

}