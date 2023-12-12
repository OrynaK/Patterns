package ua.nure.dao.EntityDAOImpl;

import ua.nure.dao.ConnectionManager;
import ua.nure.dao.EntityDAO.OrderDAO;
import ua.nure.dao.Observer.EventListener;
import ua.nure.entity.ClothingOrder;
import ua.nure.entity.Order;
import ua.nure.entity.UserOrder;
import ua.nure.entity.enums.Role;
import ua.nure.entity.enums.Size;
import ua.nure.entity.enums.Status;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    Connection con;

    public OrderDAOImpl(Connection connection) {
        con = connection;
    }

    private static final String INSERT_ORDER = "INSERT INTO `order` (datetime, status) VALUES (DEFAULT,DEFAULT)";
    private static final String UPDATE_STATUS = "UPDATE `order` SET status=? WHERE id=?";
    private static final String INSERT_CLOTHING_ORDER = "INSERT INTO `clothing_order` (clothing_id, order_id, amount, current_price) VALUES (?, ?, ?, ?)";
    private static final String INSERT_ORDER_USER = "INSERT INTO `user_order` (order_id, user_id, description, datetime) VALUES (?, ?, ?, DEFAULT)";
    private static final String GET_ORDERS = "SELECT * from `order`";
    private static final String GET_ORDER_BY_ID = "SELECT * from `order` WHERE id=?";
    private static final String GET_CLOTHING_ORDER = "SELECT * FROM `clothing_order` WHERE order_id=?";
    private static final String GET_USER_ORDER = "SELECT * FROM `user_order` WHERE order_id=?";
    private static final String GET_ORDER_FROM_USER_ORDER = "SELECT order_id FROM `user_order` WHERE user_id=? ORDER BY order_id DESC";
    private static final String GET_ROLE = "SELECT role FROM `user` WHERE id=?";
    private static final String DELETE = "DELETE FROM `order` WHERE id=?";
    private static final String DELETE_DELIVERY = "DELETE FROM delivery WHERE order_id=?";

    @Override
    public void delete(long orderId) {
        try {
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM clothing_order WHERE order_id=?")) {
                ps.setLong(1, orderId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        throw new SQLException("delete failed." +
                                "To delete order, please, firstly delete clothing_order with this order");
                    } else {
                        try (PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM user_order WHERE order_id=?")) {
                            preparedStatement.setLong(1, orderId);
                            try (ResultSet rst = ps.executeQuery()) {
                                if (rst.next()) {
                                    throw new SQLException("delete failed." +
                                            "To delete order, please, firstly delete user_order with this order");
                                } else {
                                    try (PreparedStatement statement = con.prepareStatement(DELETE)) {
                                        statement.setLong(1, orderId);
                                        if (statement.executeUpdate() > 0) {
                                            try (PreparedStatement pst = con.prepareStatement(DELETE_DELIVERY)) {
                                                pst.setLong(1, orderId);
                                                pst.executeUpdate();
                                            }
                                            con.commit();
                                        } else {
                                            con.rollback();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }


    @Override
    public long add(Order order) {
        try {
            con.setAutoCommit(false);
            try (PreparedStatement ps = con.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS)) {
                ps.executeUpdate();
                ResultSet generatedKeys = ps.getGeneratedKeys();
                try (PreparedStatement s = con.prepareStatement(INSERT_CLOTHING_ORDER)) {
                    if (generatedKeys.next()) {
                        con.commit();
                        order.setId(generatedKeys.getLong(1));
                        for (ClothingOrder clothingOrder : order.getClothesInOrder()) {
                            int k = 0;
                            s.setLong(++k, clothingOrder.getClothingId());
                            s.setLong(++k, order.getId());
                            s.setInt(++k, clothingOrder.getAmount());
                            s.setBigDecimal(++k, clothingOrder.getCurrentPrice());
                            s.addBatch();
                        }
                        s.executeBatch();
                        try (PreparedStatement prs = con.prepareStatement(INSERT_ORDER_USER)) {
                            prs.setLong(1, order.getId());
                            prs.setLong(2, order.getUsersInOrder().get(Role.USER).getUserId());
                            prs.setString(3, "");
                            if (prs.executeUpdate() != 0) {
                                con.commit();
                            } else {
                                con.rollback();
                            }
                        }
                    } else {
                        con.rollback();
                    }
                }
            }
            return order.getId();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Order findById(long orderId) {
        Order.Builder orderBuilder = new Order.Builder();
        try (PreparedStatement ps = con.prepareStatement(GET_ORDER_BY_ID)) {
            ps.setLong(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    orderBuilder.setId(rs.getInt("id"))
                            .setDateTime(rs.getTimestamp("datetime").toLocalDateTime())
                            .setStatus(Status.valueOf(rs.getString("status").toUpperCase()));
                    loadUserOrders(orderBuilder, con);
                    loadClothingOrders(orderBuilder, con);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return orderBuilder.build();
    }

    private void loadUserOrders(Order.Builder orderBuilder, Connection con) throws SQLException {
        try (PreparedStatement prs = con.prepareStatement(GET_USER_ORDER)) {
            prs.setLong(1, orderBuilder.getId());
            try (ResultSet resultSet = prs.executeQuery()) {
                while (resultSet.next()) {
                    UserOrder userOrder = mapUserOrder(resultSet);
                    Role role = getRole(userOrder.getUserId());
                    orderBuilder.putUser(role, userOrder);
                }
            }
        }
    }

    private void loadClothingOrders(Order.Builder orderBuilder, Connection con) throws SQLException {
        addClothingOrder(orderBuilder.build(), con);
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        try (Statement ps = con.createStatement()) {
            try (ResultSet rs = ps.executeQuery(GET_ORDERS)) {
                while (rs.next()) {
                    Order.Builder orderBuilder = new Order.Builder();
                    orderBuilder.setId(rs.getInt("id"))
                            .setDateTime(rs.getTimestamp("datetime").toLocalDateTime())
                            .setStatus(Status.valueOf(rs.getString("status").toUpperCase()));
                    loadUserOrders(orderBuilder, con);
                    loadClothingOrders(orderBuilder, con);
                    orders.add(orderBuilder.build());
                }
                return orders;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Order mapOrder(ResultSet rs) throws SQLException {
        return new Order.Builder()
                .setId(rs.getInt("id"))
                .setDateTime(rs.getTimestamp("datetime").toLocalDateTime())
                .setStatus(Status.valueOf(rs.getString("status").toUpperCase()))
                .build();
    }


    private UserOrder mapUserOrder(ResultSet rs) throws SQLException {
        return new UserOrder.Builder(rs.getLong("user_id"))
                .setDescription(rs.getString("description"))
                .setDateTime(rs.getTimestamp("datetime").toLocalDateTime())
                .build();
    }

    private ClothingOrder mapClothingOrder(ResultSet resultSet, ResultSet rs) throws SQLException {
        return new ClothingOrder.Builder(resultSet.getLong("clothing_id"), resultSet.getBigDecimal("current_price"), resultSet.getInt("amount"))
                .setName(rs.getString("name"))
                .setSize(Size.valueOf(rs.getString("size")))
                .setColor(rs.getString("color"))
                .build();
    }


    private void addClothingOrder(Order order, Connection con) {
        try (PreparedStatement prs = con.prepareStatement(GET_CLOTHING_ORDER)) {
            int l = 0;
            prs.setLong(++l, order.getId());
            try (ResultSet resultSet = prs.executeQuery()) {
                while (resultSet.next()) {
                    try (PreparedStatement prst = con.prepareStatement("SELECT name, size, color FROM clothing WHERE id=?")) {
                        int b = 0;
                        prst.setLong(++b, resultSet.getLong("clothing_id"));
                        try (ResultSet rs = prst.executeQuery()) {
                            while (rs.next()) {
                                order.addClothing(mapClothingOrder(resultSet, rs));
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private Role getRole(Long id) {
        Role role = null;
        try (PreparedStatement ps = con.prepareStatement(GET_ROLE)) {
            int k = 0;
            ps.setLong(++k, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    role = Role.valueOf(resultSet.getString("role").toUpperCase());
                }
                return role;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(GET_ORDER_FROM_USER_ORDER)) {
            int k = 0;
            ps.setLong(++k, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    orders.add(findById(rs.getLong(1)));
                }
                return orders;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Order update(Order entity) {
        return null;
    }

    @Override
    public void updateStatus(Long orderId, Status status) {
        try (PreparedStatement ps = con.prepareStatement(UPDATE_STATUS)) {
            int k = 0;
            ps.setString(++k, String.valueOf(status));
            ps.setLong(++k, orderId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}

