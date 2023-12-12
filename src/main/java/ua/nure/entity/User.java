package ua.nure.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ua.nure.entity.enums.Role;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User {
    private long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String phone;
    private Role role;
    private List<String> changesHistory = new ArrayList<>();
    private List<UserMemento> mementoList = new ArrayList<>();

    private User(Builder builder) {
        id = builder.id;
        name = builder.name;
        surname = builder.surname;
        email = builder.email;
        password = builder.password;
        phone = builder.phone;
        role = builder.role;
    }

    public static class Builder {
        private long id = 0;
        private final String name;
        private final String surname;
        private final String email;
        private final String password;
        private final String phone;
        private Role role = Role.USER;

        public Builder(String name, String surname, String email, String password, String phone) {
            this.name = name;
            this.surname = surname;
            this.email = email;
            this.password = password;
            this.phone = phone;
        }

        public Builder setId(Long userId) {
            this.id = userId;
            return this;
        }

        public Builder setRole(String role) {
            this.role = Role.valueOf(role);
            return this;
        }

        public User build() {
            if (name == null || surname == null || email == null || password == null || phone == null) {
                throw new IllegalStateException("Name, surname, email, phone and password are required to build a user");
            }
            return new User(this);
        }
    }

    public class UserMemento {
        private final long id;
        private final String name;
        private final String surname;
        private final String email;
        private final String password;
        private final String phone;
        private final Role role;

        private UserMemento(User user) {
            this.id = user.id;
            this.name = user.name;
            this.surname = user.surname;
            this.email = user.email;
            this.password = user.password;
            this.phone = user.phone;
            this.role = user.role;
        }

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getSurname() {
            return surname;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public String getPhone() {
            return phone;
        }

        public Role getRole() {
            return role;
        }
    }

    public UserMemento saveToMemento() {
        UserMemento memento = new UserMemento(this);
        mementoList.add(memento);
        return memento;
    }

    public void restoreFromMemento(UserMemento memento) {
        this.id = memento.id;
        this.name = memento.name;
        this.surname = memento.surname;
        this.email = memento.email;
        this.password = memento.password;
        this.phone = memento.phone;
        this.role = memento.role;
    }

    public UserMemento undo() {
        if (!mementoList.isEmpty() || mementoList.size() - 1 > 1) {
            UserMemento memento = mementoList.remove(mementoList.size() - 1);
            restoreFromMemento(memento);
        } else {
            System.out.println("It`s initial state. Can`t to rollback changes");
        }
        return getLastSavedState();
    }

    public UserMemento getLastSavedState() {
        if (!mementoList.isEmpty()) {
            return mementoList.get(mementoList.size() - 1);
        }
        return null;
    }

    public int getMementoSize() {
        return mementoList.size();
    }

    public static User fromMemento(UserMemento memento) {
        User user = new User.Builder(memento.name, memento.surname, memento.email, memento.password, memento.phone)
                .setId(memento.id)
                .setRole(memento.role.name())
                .build();

        return user;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", role=" + role +
                '}';
    }
}
