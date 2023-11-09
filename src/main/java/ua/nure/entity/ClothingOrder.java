package ua.nure.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.nure.entity.enums.Size;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClothingOrder {
    private long clothingId;
    private BigDecimal currentPrice;
    private int amount;
    private String name;
    private Size size;
    private String color;

    public ClothingOrder(Builder builder) {
        this.clothingId = builder.clothingId;
        this.currentPrice = builder.currentPrice;
        this.amount = builder.amount;
        this.name = builder.name;
        this.size = builder.size;
        this.color = builder.color;
    }

    public static class Builder {
        private final long clothingId;
        private final BigDecimal currentPrice;
        private final int amount;
        private String name;
        private Size size;
        private String color;

        public Builder(long clothingId, BigDecimal currentPrice, int amount) {
            this.clothingId = clothingId;
            this.currentPrice = currentPrice;
            this.amount = amount;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setSize(Size size) {
            this.size = size;
            return this;
        }

        public Builder setColor(String color) {
            this.color = color;
            return this;
        }

        public ClothingOrder build() {
            if (clothingId <= 0 || currentPrice == null || amount <= 0) {
                throw new IllegalStateException("Can`t create ClothingOrder with invalid values");
            }
            return new ClothingOrder(this);
        }
    }

}
