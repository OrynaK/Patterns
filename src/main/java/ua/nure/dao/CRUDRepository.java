package ua.nure.dao;

import ua.nure.dao.Observer.EventListener;

import java.util.List;

public interface CRUDRepository<T> {
    long add(T entity);

    T update(T entity);

    void delete(long id);

    T findById(long id);

    List<T> findAll();

}
