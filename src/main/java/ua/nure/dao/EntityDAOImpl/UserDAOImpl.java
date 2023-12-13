package ua.nure.dao.EntityDAOImpl;

import ua.nure.dao.EntityDAO.UserDAO;
import ua.nure.dao.Observer.EventListener;
import ua.nure.dao.Observer.EventManager;
import ua.nure.entity.User;

import java.sql.*;
import java.util.*;

public class UserDAOImpl implements UserDAO {

    private final EventManager eventManager;
    Connection con;

    public UserDAOImpl(Connection connection, EventManager clothingEventManager) {
        con = connection;
        this.eventManager = clothingEventManager;
    }

    private static final String GET_USER_BY_ID = "SELECT * from user WHERE id=?";
    private static final String UPDATE = "UPDATE user SET name=?, surname=?, email=?, password=?, phone = ? WHERE id=?";
    private static final String GET_ALL_USERS = "SELECT * from user";
    private static final String ADD_USER = "INSERT INTO user (name, surname, password, email, phone) VALUES (?, ?, ?, ?, ?)";
    private static final String FIND_USER_IN_ORDER = "SELECT * FROM user_order WHERE user_id=?";
    private static final String DELETE = "DELETE FROM user WHERE id=?";
    private static final String FIND_USER_BY_EMAIL_AND_PASSWORD = "SELECT * from user WHERE email=? AND password=?";
    private static final String FIND_USER_BY_EMAIL = "SELECT * from user WHERE email=?";


    @Override
    public long add(User user) {
        User existingUser = findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new RuntimeException("User with this email already exists");
        }

        try (PreparedStatement ps = con.prepareStatement(ADD_USER, Statement.RETURN_GENERATED_KEYS)) {
            int k = 0;
            ps.setString(++k, user.getName());
            ps.setString(++k, user.getSurname());
            ps.setString(++k, user.getPassword());
            ps.setString(++k, user.getEmail());
            ps.setString(++k, user.getPhone());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    user.setId(keys.getLong(1));
                    eventManager.notifyEntityAdded("UserAdded", user);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return user.getId();
    }

    @Override
    public User update(User user) {
        try (PreparedStatement ps = con.prepareStatement(UPDATE)) {
            int k = 0;
            ps.setString(++k, user.getName());
            ps.setString(++k, user.getSurname());
            ps.setString(++k, user.getEmail());
            ps.setString(++k, user.getPassword());
            ps.setString(++k, user.getPhone());
            ps.setLong(++k, user.getId());
            ps.executeUpdate();
            eventManager.notifyEntityUpdated("UserUpdated", user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }


    @Override
    public void delete(long id) {
        try (PreparedStatement st = con.prepareStatement(FIND_USER_IN_ORDER)) {
            st.setLong(1, id);
            try (ResultSet resultSet = st.executeQuery()) {
                if (resultSet.next())
                    throw new SQLException("delete failed. " +
                            "To delete user, please, firstly delete order with this user");
                try (PreparedStatement statement = con.prepareStatement(DELETE)) {
                    statement.setLong(1, id);
                    statement.executeUpdate();
                    eventManager.notifyEntityRemoved("UserRemoved", id);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public User findById(long id) {
        User user = new User();
        try (PreparedStatement ps = con.prepareStatement(GET_USER_BY_ID)) {
            int k = 0;
            ps.setLong(++k, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    user = mapUsers(resultSet);
                }
                return user;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<User> findAll() {
        List<User> userList = new ArrayList<>();
        try (Statement st = con.createStatement()) {
            try (ResultSet rs = st.executeQuery(GET_ALL_USERS)) {
                while (rs.next()) {
                    userList.add(mapUsers(rs));
                }
                return userList;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private User mapUsers(ResultSet rs) throws SQLException {
        long id = rs.getInt("id");
        String name = rs.getString("name");
        String surname = rs.getString("surname");
        String password = rs.getString("password");
        String email = rs.getString("email");
        String role = rs.getString("role").toUpperCase();
        String phone = rs.getString("phone");
        return new User.Builder(name, surname, email, password, phone).setId(id).setRole(role).build();
    }

    public User findByEmailAndPassword(String email, String password) {
        try (PreparedStatement ps = con.prepareStatement(FIND_USER_BY_EMAIL_AND_PASSWORD)) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    return mapUsers(resultSet);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    private User findByEmail(String email) {
        try (PreparedStatement ps = con.prepareStatement(FIND_USER_BY_EMAIL)) {
            ps.setString(1, email);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    return mapUsers(resultSet);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }
}
