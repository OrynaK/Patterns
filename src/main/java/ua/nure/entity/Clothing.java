package ua.nure.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.nure.entity.enums.Season;
import ua.nure.entity.enums.Sex;
import ua.nure.entity.enums.Size;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Clothing {
    private long id;
    private String name;
    private Size size;
    private String color;
    private Season season;
    private int amount;
    private BigDecimal actualPrice;
    private Sex sex;

    public Clothing(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.size = builder.size;
        this.color = builder.color;
        this.season = builder.season;
        this.amount = builder.amount;
        this.actualPrice = builder.actualPrice;
        this.sex = builder.sex;
    }

    public static class Builder {
        private long id = 0;
        private final String name;
        private final Size size;
        private final String color;
        private final Season season;
        private final int amount;
        private final BigDecimal actualPrice;
        private final Sex sex;

        public Builder(String name, Size size, String color, Season season, int amount, BigDecimal actualPrice, Sex sex) {
            this.name = name;
            this.size = size;
            this.color = color;
            this.season = season;
            this.amount = amount;
            this.actualPrice = actualPrice;
            this.sex = sex;
        }

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Clothing build() {
            if (name == null || size == null || color == null || season == null || amount < 0 || actualPrice == null || sex == null) {
                throw new IllegalStateException("Can`t create Clothing without enough parameters");
            }
            return new Clothing(this);
        }
    }
}
