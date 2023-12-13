package ua.nure.dao.proxy;

import ua.nure.entity.enums.Role;

public class ProxyManager {
    public static boolean isAdmin(Role userRole) {
        if (userRole != Role.ADMIN) {
            System.out.println("Access denied. Only ADMIN can perform this operation.");
        }
        return userRole == Role.ADMIN;
    }
}
