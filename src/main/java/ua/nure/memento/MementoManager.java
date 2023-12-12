package ua.nure.memento;

import ua.nure.dao.CRUDRepository;
import ua.nure.dao.Factory;

public interface MementoManager<T>{
    public void add(T entity, CRUDRepository<T> dao);
    public void update(T entity, CRUDRepository<T> dao);
    public void undoUpdate(T entity, CRUDRepository<T> dao);

}
