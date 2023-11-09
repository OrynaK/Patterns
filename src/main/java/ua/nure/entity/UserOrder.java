package ua.nure.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOrder {
    private long userId;
    private String description;
    private LocalDateTime dateTime;

    public UserOrder(long userId) {
        this.userId = userId;
    }

    public UserOrder(Builder builder) {
        this.userId = builder.userId;
        this.description = builder.description;
        this.dateTime = builder.dateTime;
    }

    public static class Builder {
        private final long userId;
        private String description;
        private LocalDateTime dateTime;


        public Builder(long userId) {
            this.userId = userId;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setDateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public UserOrder build() {
            if (userId <= 0) {
                throw new IllegalStateException("Can`t create UserOrder with invalid userId");
            }
            return new UserOrder(this);
        }
    }
}
