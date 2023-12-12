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
import ua.nure.memento.MementoUser;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Factory factory = new FactoryDAO();

        /* TEST FIRST PRACTICE
         */
//        DeliveryDAO deliveryDAO = factory.getDeliveryDAO();
//        UserDAO userDAO = factory.getUserDAO();
//        ClothingDAO clothingDAO = factory.getClothingDAO();
//        OrderDAO orderDAO = factory.getOrderDAO();
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


        /*TEST SECOND PRACTICE
         */
//        EventManager eventManager = new EventManager();
//        UserDAO userDAO = factory.getUserDAO(eventManager);
//        ClothingDAO clothingDAO = factory.getClothingDAO(eventManager);
//        DeliveryDAO deliveryDAO = factory.getDeliveryDAO(eventManager);
//        OrderDAO orderDAO = factory.getOrderDAO(eventManager);
//
//        ClothingListener userAddClothingListener = new ClothingListener();
//        OrderListener userUpdatedOrderListener = new OrderListener();
//        DeliveryListener userAddDeliveryListener = new DeliveryListener();
//        UserListener userDeletedUserListener = new UserListener();
//
//        eventManager.subscribe("ClothingAdded", userAddClothingListener);
//        eventManager.subscribe("OrderUpdated", userUpdatedOrderListener);
//        eventManager.subscribe("UserRemoved", userDeletedUserListener);
//        eventManager.subscribe("DeliveryAdded", userAddDeliveryListener);
//
//        Clothing clothing = new Clothing.Builder("T-Shirt", Size.M, "Red", Season.SUMMER, 10, new BigDecimal("19.99"), Sex.MALE).build();
//        User user = new User.Builder("John", "Doe", "john.doe@example.com", "password123", "+123456789").build();
//        Delivery newDelivery = new Delivery.Builder( "тест", "тест", "тест")
//                .setApartmentNumber(1)
//                .setEntrance(1)
//                .setOrder_id(4)
//                .build();
//        clothingDAO.add(clothing);
//        orderDAO.updateStatus(1L,Status.DELIVERED);
//        userDAO.delete(4);
//        userDAO.add(user);
//        clothingDAO.update(clothing);
//        deliveryDAO.add(newDelivery);

        /*TEST THIRD PRACTICE
         */
        EventManager eventManager = new EventManager();
        UserDAO userDAO = factory.getUserDAO(eventManager);

        User user = new User.Builder("John", "Doe", "john.doe@example.com", "password", "123456")
                .setRole("USER")
                .build();

        MementoUser mementoUser = new MementoUser();
        mementoUser.add(user, userDAO);
        System.out.println("-- Initial state");
        System.out.println(userDAO.findAll());

        user.setName("Jane");
        user.setEmail("jane.doe@example.com");

        mementoUser.update(user, userDAO);
        System.out.println("—1 update");
        System.out.println(userDAO.findAll());
        user.setName("тест");
        mementoUser.update(user, userDAO);
        System.out.println("—2 update");
        System.out.println(userDAO.findAll());
        mementoUser.undoUpdate(user, userDAO);
        System.out.println("—1 undo update");
        System.out.println(userDAO.findAll());
        System.out.println("--2 undo update ");
        mementoUser.undoUpdate(user, userDAO);
        System.out.println(userDAO.findAll());
        userDAO.delete(user.getId());
        System.out.println("--3 undo update after delete object");
        mementoUser.undoUpdate(user, userDAO);
    }
}

