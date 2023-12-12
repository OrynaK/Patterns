package ua.nure;

import ua.nure.dao.EntityDAO.ClothingDAO;
import ua.nure.dao.EntityDAO.DeliveryDAO;
import ua.nure.dao.EntityDAO.OrderDAO;
import ua.nure.dao.EntityDAO.UserDAO;
import ua.nure.dao.EntityDAOImpl.ClothingDAOImpl;
import ua.nure.dao.Factory;
import ua.nure.dao.FactoryDAO;
import ua.nure.dao.Observer.DAOListeners.ClothingListener;
import ua.nure.dao.Observer.DAOListeners.DeliveryListener;
import ua.nure.dao.Observer.DAOListeners.OrderListener;
import ua.nure.dao.Observer.DAOListeners.UserListener;
import ua.nure.dao.Observer.EventListener;
import ua.nure.dao.Observer.EventManager;
import ua.nure.entity.*;
import ua.nure.entity.enums.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Factory factory = new FactoryDAO();

        DeliveryDAO deliveryDAO = factory.getDeliveryDAO();
        OrderDAO orderDAO = factory.getOrderDAO();

        //TEST FIRST PRACTICE

//
//        System.out.println("---TEST FIND BY ID---");
//        System.out.println("User with id=1: " + userDAO.findById(1));
//        System.out.println("Clothing with id=2: " + clothingDAO.findById(2));
//        System.out.println("Order with id=1: " + orderDAO.findById(1));
//        System.out.println("Delivery with order_id=1: " + deliveryDAO.findById(1));
//
//        System.out.println("---TEST ADD---");
//        User newUser = new User.Builder("тест", "тест", "тест", "тест", "+тест")
//                .build();
//        System.out.println("Вставлений користувач: " + userDAO.findById(userDAO.add(newUser)));
//
//        Clothing newClothing = new Clothing.Builder("тест", Size.XS, "тест", Season.WINTER, 1, new BigDecimal(17), Sex.MALE)
//                .build();
//        System.out.println("Вставлена одежа: " + clothingDAO.findById(clothingDAO.add(newClothing)));
//
//        ClothingOrder clothingOrder = new ClothingOrder.Builder(newClothing.getId(), newClothing.getActualPrice(), 1)
//                .setName(newClothing.getName())
//                .setSize(newClothing.getSize())
//                .setColor(newClothing.getColor())
//                .build();
//        UserOrder userOrder = new UserOrder.Builder(1)
//                .build();
//        Order newOrder = new Order.Builder()
//                .addClothing(clothingOrder)
//                .putUser(Role.USER, userOrder)
//                .build();
//        long orderId = orderDAO.add(newOrder);
//        System.out.println("Вставлене замовлення: " + orderDAO.findById(orderId));
//
//        Delivery newDelivery = new Delivery.Builder( "тест", "тест", "тест")
//                .setApartmentNumber(1)
//                .setEntrance(1)
//                .setOrder_id(orderId)
//                .build();
//        System.out.println("Вставлена доставка: " + deliveryDAO.findById(deliveryDAO.add(newDelivery)));
//
//        System.out.println("---TEST DELETE---");
//        System.out.println("-----------------");
//        List<User> users = userDAO.findAll();
//        System.out.println("Users before delete:");
//        for (User user : users) {
//            System.out.println(user);
//        }
//        userDAO.delete(3);
//        users = userDAO.findAll();
//        System.out.println("Users after delete:");
//        for (User user : users) {
//            System.out.println(user);
//        }
//        System.out.println("-----------------");
//        List<Clothing> clothes = clothingDAO.findAll();
//        System.out.println("Clothing before delete:");
//        for (Clothing clothing : clothes) {
//            System.out.println(clothing);
//        }
//        clothingDAO.delete(5);
//        clothes = clothingDAO.findAll();
//        System.out.println("Clothing after delete:");
//        for (Clothing clothing : clothes) {
//            System.out.println(clothing);
//        }
//        List<Order> orders = orderDAO.findAll();
//        System.out.println("-----------------");
//        System.out.println("Orders before delete:");
//        for (Order order : orders) {
//            System.out.println(order);
//        }
//        orderDAO.delete(3);
//        orders = orderDAO.findAll();
//        System.out.println("-----------------");
//        System.out.println("Orders after delete:");
//        for (Order order : orders) {
//            System.out.println(order);
//        }
//        System.out.println("---TEST UPDATE---");
//        System.out.println("User before update: ");
//        System.out.println(userDAO.findById(1));
//        User updatedUser = new User.Builder("апдейт", "Петров", "ivan@example.com", "пароль123", "+380123456789")
//                .setId(1L)
//                .setRole(String.valueOf(Role.USER))
//                .build();
//        userDAO.update(updatedUser);
//        System.out.println("User after update: ");
//        System.out.println(userDAO.findById(1));
//
//        System.out.println("Clothing before update amount: ");
//        System.out.println(clothingDAO.findById(1));
//        clothingDAO.updateClothingAmount(clothingDAO.findById(1).getId(), 2365);
//        System.out.println("Clothing after update amount: ");
//        System.out.println(clothingDAO.findById(1));
//
//        System.out.println("Order before update status: ");
//        System.out.println(orderDAO.findById(1));
//        orderDAO.updateStatus(orderDAO.findById(1).getId(), Status.DELIVERED);
//        System.out.println("Order after update status: ");
//        System.out.println(orderDAO.findById(1));
//        System.out.println("Delivery before update: ");
//        System.out.println(deliveryDAO.findById(1));
//        Delivery updatedDelivery = new Delivery.Builder("тест", "тест", "тест")
//                .build();
//        deliveryDAO.updateByOrderId(updatedDelivery, 1);
//        System.out.println("Delivery after update: ");
//        System.out.println(deliveryDAO.findById(1));

        //TEST SECOND PRACTICE

        EventManager<Clothing> clothingEventManager = new EventManager<>();
        EventManager<User> userEventManager = new EventManager<>();

        ClothingListener clothingListener = new ClothingListener();
        UserListener userListener = new UserListener();

        clothingEventManager.subscribe("UserAdded", clothingListener);
//        userEventManager.subscribe("UserAdded", userListener);

        UserDAO userDAO = factory.getUserDAO(userEventManager);
        ClothingDAO clothingDAO = factory.getClothingDAO(clothingEventManager);

        Clothing clothing = new Clothing.Builder("T-Shirt", Size.M, "Red", Season.SUMMER, 10, new BigDecimal("19.99"), Sex.MALE).build();
        User user = new User.Builder("John", "Doe", "john.doe@example.com", "password123", "+123456789").build();

        clothingDAO.add(clothing);
        clothingEventManager.notifyEntityAdded("ClothingAdded", clothing);
        userDAO.add(user);
    }
}
