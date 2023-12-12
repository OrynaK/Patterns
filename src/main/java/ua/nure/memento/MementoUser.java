package ua.nure.memento;

import ua.nure.dao.CRUDRepository;
import ua.nure.entity.User;

public class MementoUser implements MementoManager<User> {
    @Override
    public void add(User user, CRUDRepository<User> userDAO) {
        user.setId(userDAO.add(user));
        user.saveToMemento();
    }

    @Override
    public void update(User user, CRUDRepository<User> userDAO) {
        userDAO.update(user);
        user.saveToMemento();
    }

    @Override
    public void undoUpdate(User user, CRUDRepository<User> dao) {
        int mementoSize = user.getMementoSize();
        if (!isDeleted(user, dao)) {
            if (mementoSize > 1) {
                User.UserMemento secondToLastSavedState = user.getMementoList().get(mementoSize - 2);
                User userToRestore = User.fromMemento(secondToLastSavedState);
                dao.update(userToRestore);
                user.undo();

            } else {
                System.out.println("Error! It`s initial state. No actions to undo.");
            }

        }
        else{
            System.out.println("The object is deleted. Calling the undo change operation ignored");
        }
    }

    private boolean isDeleted(User user, CRUDRepository<User> dao) {
        return dao.findById(user.getId()).getId()==0;
    }

}
