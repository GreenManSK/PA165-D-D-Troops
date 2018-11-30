package cz.muni.fi.pa165.w2018.dndtroops.backend.dao;

import cz.muni.fi.pa165.w2018.dndtroops.backend.DndTroopsApplicationContext;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Hero;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.User;
import cz.muni.fi.pa165.w2018.dndtroops.backend.enums.Race;
import cz.muni.fi.pa165.w2018.dndtroops.backend.enums.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Null;

import java.util.List;

import static org.testng.Assert.*;


/**
 * Tests for UserDao
 *
 * @author Marek Valko
 */
@ContextConfiguration(classes = DndTroopsApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class UserDaoTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private UserDao userDao;

    @PersistenceContext
    private EntityManager em;

    @BeforeMethod
    public void setUp() {
        em.persist(createDefaultHero());
    }


    @Test
    public void getUserNotExistingId() {
        User user = createUserUser();
        em.persist(user);
        assertNull(userDao.getById(user.getId() + 1));
    }

    @Test
    public void getUser() {
        User user = createUserUser();
        em.persist(user);
        assertEquals(user, userDao.getById(user.getId()));
    }

    @Test
    public void getAll() {
        User user1 = createAdminUser();
        User user2 = createUserUser();
        em.persist(user1);
        em.persist(user2);
        User expect1 = createAdminUser();
        User expect2 = createUserUser();
        List<User> result = userDao.getAll();
        assertEquals(2, result.size());
        assertTrue(result.contains(expect1));
        assertTrue(result.contains(expect2));
    }

    @Test
    public void getAllEmpty() {
        assertTrue(userDao.getAll().isEmpty());
    }

    @Test
    public void createUser() {
        User user = createUserUser();
        userDao.create(user);
        assertEquals(user, em.find(User.class,user.getId()));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void createUserNull() {
        userDao.create(null);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createUserNullLogin() {
        User user = createUserUser();
        user.setLogin(null);
        userDao.create(user);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createUserNullPassword() {
        User user = createUserUser();
        user.setPasswordHash(null);
        userDao.create(user);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createUserNullType() {
        User user = createUserUser();
        user.setType(null);
        userDao.create(user);
    }

    @Test
    public void updateUser() {
        User user = createUserUser();
        em.persist(user);
        assertNotNull(em.find(User.class,user.getId()));

        User modified = createUserUser();
        modified.setId(user.getId());
        modified.setLogin("newlogin");
        modified.setPasswordHash("newpassword");
        userDao.update(modified);
        assertEquals(user,modified);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void updateUserNull() {
        userDao.update(null);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void updateUserNullLogin() {
        User user = createUserUser();
        user.setLogin(null);
        userDao.update(user);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void updateUserNullPassword() {
        User user = createUserUser();
        user.setPasswordHash(null);
        userDao.update(user);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void updateUserNullType() {
        User user = createUserUser();
        user.setType(null);
        userDao.update(user);
    }

    @Test
    void removeUser() {
        User user = createUserUser();
        em.persist(user);
        assertNotNull(em.find(User.class, user.getId()));

        userDao.remove(user);
        assertNull(em.find(User.class, user.getId()));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void removeNullUser() {
        userDao.remove(null);
    }

    @Test
    public void getUserByLogin() {
        User user = createUserUser();
        em.persist(user);
        assertNotNull(em.find(User.class, user.getId()));

        User find = userDao.getByLogin("mylogin");
        assertEquals(find,user);
    }

    @Test
    public void getUserByLoginFromEmpty() {
        assertNull(userDao.getByLogin("random"));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void getUserByNullLogin() {
        userDao.getByLogin(null);
    }

    @Test
    public void createUserWithHeroTest() {
        User user = createUserWithHero();
        userDao.create(user);
        assertEquals(user, em.find(User.class,user.getId()));
    }


    private User createUserUser() {
        User user = new User();
        user.setLogin("mylogin");
        user.setPasswordHash("passwordhash");
        user.setType(UserType.USER);
        return user;
    }


    private User createUserWithHero() {
        User user = new User();
        user.setLogin("myherologin");
        user.setPasswordHash("passwordhash");
        user.setType(UserType.USER);
        user.setHero(createDefaultHero());
        return user;
    }

    private User createAdminUser() {
        User user = new User();
        user.setType(UserType.ADMIN);
        user.setLogin("admin");
        user.setPasswordHash("adminpassword");
        return user;
    }


    public Hero createDefaultHero() {
        Hero hero = new Hero();
        hero.setName("Adam");
        hero.setExperience(100L);
        hero.setRace(Race.HUMAN);
        return hero;
    }
}