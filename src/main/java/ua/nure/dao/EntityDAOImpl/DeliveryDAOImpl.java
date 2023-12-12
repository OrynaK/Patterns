package ua.nure.dao.EntityDAOImpl;

import ua.nure.dao.ConnectionManager;
import ua.nure.dao.EntityDAO.DeliveryDAO;
import ua.nure.dao.Observer.EventListener;
import ua.nure.dao.Observer.EventManager;
import ua.nure.entity.Delivery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DeliveryDAOImpl implements DeliveryDAO {
    private static final String ADD_DELIVERY = "INSERT INTO delivery (order_id, city, street, house_number, entrance, apartment_number) values (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE delivery SET city=?, street=?, house_number=?, entrance=?, apartment_number=? WHERE order_id=?";
    private static final String GET_DELIVERY_BY_ORDER = "SELECT * FROM delivery WHERE order_id=?";
    Connection con;
    private final EventManager eventManager;

    public DeliveryDAOImpl(Connection connection,EventManager eventManager) {
        con = connection;
        this.eventManager = eventManager;
    }

    @Override
    public long add(Delivery delivery) {
        try {
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement(ADD_DELIVERY);
            int k = 0;
            ps.setLong(++k, delivery.getOrder_id());
            ps.setString(++k, delivery.getCity());
            ps.setString(++k, delivery.getStreet());
            ps.setString(++k, delivery.getHouseNumber());
            ps.setInt(++k, delivery.getEntrance());
            ps.setInt(++k, delivery.getApartmentNumber());
            ps.executeUpdate();
            con.commit();
            eventManager.notifyEntityAdded("DeliveryAdded", delivery);
            return delivery.getOrder_id();
        } catch (Exception e) {
            try {
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public Delivery update(Delivery entity) {
        return null;
    }

    @Override
    public Delivery updateByOrderId(Delivery delivery, long order_id) {
        try (PreparedStatement ps = con.prepareStatement(UPDATE)) {
            int k = 0;
            ps.setString(++k, delivery.getCity());
            ps.setString(++k, delivery.getStreet());
            ps.setString(++k, delivery.getHouseNumber());
            ps.setInt(++k, delivery.getEntrance());
            ps.setInt(++k, delivery.getApartmentNumber());
            ps.setLong(++k, order_id);
            ps.executeUpdate();
            eventManager.notifyEntityUpdated("DekiveryUpdated", delivery);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return findById(delivery.getOrder_id());
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public Delivery findById(long orderId) {
        Delivery delivery = new Delivery();
        try (PreparedStatement ps = con.prepareStatement(GET_DELIVERY_BY_ORDER)) {
            int k = 0;
            ps.setLong(++k, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    delivery = mapDelivery(rs);
                }
                return delivery;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Delivery> findAll() {
        return null;
    }


    public Delivery mapDelivery(ResultSet rs) throws SQLException {
        long orderId = rs.getLong("order_id");
        String city = rs.getString("city");
        String street = rs.getString("street");
        String houseNumber = rs.getString("house_number");
        int entrance = rs.getInt("entrance");
        int number = rs.getInt("apartment_number");
        return new Delivery.Builder(city, street, houseNumber).setEntrance(entrance).setApartmentNumber(number).setOrder_id(orderId).build();
    }
}

