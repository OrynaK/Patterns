package ua.nure.entity.enums;

public enum Status {
    PROCESSING("Processing"),
    ACCEPTED("Accepted"),
    SENT("Sent"),
    DELIVERED("Delivered"),
    DENIED("Denied");
    String status;

    Status(String status) {
        this.status = status;
    }
    public String getStatus(){
        return status;
    }
}
