package ua.nure.entity.enums;

public enum Sex {
    MALE("Male"),
    FEMALE("Female"),
    UNISEX("Unisex");
    String sex;

    Sex(String sex) {
        this.sex = sex;
    }
    public String getSex(){
        return sex;
    }
}
