package ua.nure.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import ua.nure.entity.enums.Role;
import ua.nure.entity.enums.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class Order {
    private long id;
    private LocalDateTime dateTime;
    private Status status;
    private List<ClothingOrder> clothesInOrder;
    private Map<Role, UserOrder> usersInOrder;

    public Order() {
        clothesInOrder = new ArrayList<>();
        usersInOrder = new EnumMap<>(Role.class);
    }

    public Order(long userId, List<ClothingOrder> clothesInOrder) {
        this.clothesInOrder = clothesInOrder;
        this.usersInOrder = new EnumMap<>(Role.class);
        usersInOrder.put(Role.USER, new UserOrder(userId));
    }

    public Order(Builder builder) {
        this.id = builder.id;
        this.dateTime = builder.dateTime;
        this.status = builder.status;
        this.usersInOrder = builder.usersInOrder;
        this.clothesInOrder = builder.clothesInOrder;
    }

    public void addClothing(ClothingOrder clothingOrder) {
        clothesInOrder.add(clothingOrder);
    }

    public void putUser(Role role, UserOrder userOrder) {
        usersInOrder.put(role, userOrder);
    }

    public static class Builder {
        private long id;
        private LocalDateTime dateTime;
        private Status status;
        private List<ClothingOrder> clothesInOrder = new ArrayList<>();
        private Map<Role, UserOrder> usersInOrder = new EnumMap<>(Role.class);

        public Builder() {
        }

        public Builder addClothing(ClothingOrder clothingOrder) {
            clothesInOrder.add(clothingOrder);
            return this;
        }

        public Builder putUser(Role role, UserOrder userOrder) {
            usersInOrder.put(role, userOrder);
            return this;
        }

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setDateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public Builder setStatus(Status status) {
            this.status = status;
            return this;
        }

        public Builder setClothesInOrder(List<ClothingOrder> clothesInOrder) {
            this.clothesInOrder = clothesInOrder;
            return this;
        }

        public Builder setUsersInOrder(Map<Role, UserOrder> usersInOrder) {
            this.usersInOrder = usersInOrder;
            return this;
        }

        public Order build() {
            return new Order(this);
        }

        public long getId() {
            return id;
        }
    }

}
