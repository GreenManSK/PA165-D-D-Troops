package cz.muni.fi.pa165.w2018.dndtroops.backend.dao;

import com.google.common.base.Preconditions;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Implementation of UserDao
 *
 * @author Marek Valko
 */
@Repository
public class UserDaoImpl implements UserDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User getById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> getAll() {
        return entityManager.createQuery("select u from users u", User.class).getResultList();
    }

    @Override
    public void create(User user) {
        checkNotNull(user);
        entityManager.persist(user);
    }

    @Override
    public User getByLogin(String login) {
        checkNotNull(login);
        try {
            return entityManager.createQuery("select u from users u where u.login = :login", User.class).setParameter("login", login).getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void update(User user) {
        checkNotNull(user);
        entityManager.merge(user);
    }

    @Override
    public void remove(User user) {
        checkNotNull(user);
        entityManager.remove(user);
    }
}
