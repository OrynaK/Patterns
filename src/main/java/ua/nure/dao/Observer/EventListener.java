package ua.nure.dao.Observer;

public interface EventListener<T> {
    void entityAdded(T entity);

    void entityRemoved(long id);

    void entityUpdated(T updatedEntity);

    void subscribeToEventType(String eventType);
}
