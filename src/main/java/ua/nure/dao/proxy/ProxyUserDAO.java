package ua.nure.dao.proxy;

import ua.nure.dao.EntityDAO.UserDAO;
import ua.nure.entity.User;
import ua.nure.entity.enums.Role;

import java.util.List;

public class ProxyUserDAO {
    private UserDAO userDAOImpl;

    public ProxyUserDAO(UserDAO userDAOImpl) {
        this.userDAOImpl = userDAOImpl;
    }

    public long add(User newUser, Role role) {
        if (ProxyManager.isAdmin(role)) {
            return userDAOImpl.add(newUser);
        }
        return 0;
    }

    public User update(User updatedUser, Role role) {
        if (ProxyManager.isAdmin(role)) {
            return userDAOImpl.update(updatedUser);
        }
        return null;
    }

    public void delete(long id, Role role) {
        if (ProxyManager.isAdmin(role)) {
            userDAOImpl.delete(id);
        }
    }

    public User findById(long id) {
        return userDAOImpl.findById(id);
    }

    public List<User> findAll() {
        return userDAOImpl.findAll();
    }

    public long register(User user) {
        return userDAOImpl.add(user);
    }

    public User authorization(String email, String password) {
        return userDAOImpl.findByEmailAndPassword(email,password);
    }
}
