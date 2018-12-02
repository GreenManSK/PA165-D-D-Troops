package cz.muni.fi.pa165.w2018.dndtroops.service;

import cz.muni.fi.pa165.w2018.dndtroops.backend.dao.UserDao;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Hero;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.User;
import cz.muni.fi.pa165.w2018.dndtroops.backend.enums.Race;
import cz.muni.fi.pa165.w2018.dndtroops.backend.enums.UserType;
import cz.muni.fi.pa165.w2018.dndtroops.service.configuration.ServiceConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.validation.constraints.Null;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

/**
 * Tests for UserService implementation
 *
 * @author Marek Valko
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class UserServiceImplTest extends AbstractTestNGSpringContextTests {
    @Mock
    private UserDao userDao;

    @Inject
    @InjectMocks
    private UserService userService;

    private User user;
    private User admin;
    private Hero hero;
    private User user2;

    private static final String password = "password";


    @BeforeMethod
    public void setUp() {
        setUpUsers();
        MockitoAnnotations.initMocks(this);
    }

    private void setUpUsers() {
        hero = new Hero();
        hero.setId(1L);
        hero.setName("Adam");
        hero.setExperience(20L);
        hero.setRace(Race.HUMAN);

        user = new User();
        user.setId(1L);
        user.setLogin("user");
        user.setType(UserType.USER);
        user.setHero(hero);

        admin = new User();
        admin.setId(2L);
        admin.setLogin("admin");
        admin.setType(UserType.ADMIN);

        user2 = new User();
        user2.setLogin("user2");
        user2.setType(UserType.USER);

    }

    @Test
    public void createUser() {
        doAnswer(invocationOnMock -> {
            User usr = (User) invocationOnMock.getArguments()[0];
            usr.setId(20L);
            return null;
        }).when(userDao).create(user2);
        userService.createUser(user2,password);
        verify(userDao).create(user2);
        assertNotNull(user2.getId());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void createNull() {
        doThrow(new NullPointerException()).when(userDao).create(null);
        userService.createUser(null,password);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void createNullPassword() {
        userService.createUser(user, null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void createEmptyPassword() {
        userService.createUser(user,"");
    }

    @Test
    public void update() {
        doNothing().when(userDao).update(user);
        userService.update(user);
        verify(userDao).update(user);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void updateNull() {
        doThrow(new NullPointerException()).when(userDao).update(null);
        userService.update(null);
    }

    @Test
    public void delete() {
        when(userDao.getById(user.getId())).thenReturn(user);
        doNothing().when(userDao).remove(user);
        userService.delete(user.getId());
        verify(userDao).remove(user);
    }

    @Test
    public void deleteNonExistent() {
        long id = 10L;
        when(userDao.getById(id)).thenReturn(null);
        userService.delete(id);
        verify(userDao,never()).remove(any());
    }

    @Test
    public void getById() {
        when(userDao.getById(user.getId())).thenReturn(user);
        User usr = userService.getById(user.getId());
        verify(userDao).getById(usr.getId());
        assertEquals(usr, user);
    }

    @Test
    public void getByIdNonExistent() {
        long id = 10L;
        when(userDao.getById(id)).thenReturn(null);
        assertNull(userService.getById(id));
        verify(userDao).getById(id);
    }

    @Test
    public void getAll() {
        List<User> users = Arrays.asList(user,admin);
        when(userDao.getAll()).thenReturn(new ArrayList<>(users));
        List<User> allUsers = userService.getAll();
        verify(userDao).getAll();
        assertEquals(users, allUsers);
    }

    @Test
    public void getAllEmpty() {
        when(userDao.getAll()).thenReturn(Collections.emptyList());
        List<User> allUsers = userService.getAll();
        verify(userDao).getAll();
        assertEquals(0, allUsers.size());
    }

    @Test
    public void authenticateSuccess() {
        userService.createUser(user, password);
        assertTrue(userService.authenticate(user, password));
    }

    @Test
    public void authenticateFailure() {
        userService.createUser(user, password);
        assertFalse(userService.authenticate(user,password + "something"));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void authenticateNull() {
        userService.authenticate(null, password);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void authenticateNullPassword() {
        userService.authenticate(user, null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void authenticateEmptyPassword() {
        userService.authenticate(user, "");
    }

    @Test
    public void isAdmin() {
        assertTrue(userService.isAdmin(admin));
        assertFalse(userService.isAdmin(user));
    }

    @Test
    public void isUser() {
        assertTrue(userService.isUser(user));
        assertFalse(userService.isUser(admin));
    }

    @Test
    public void getByLogin() {
        when(userDao.getByLogin(user.getLogin())).thenReturn(user);
        User usr = userService.getByLogin(user.getLogin());
        verify(userDao).getByLogin(user.getLogin());
        assertEquals(usr, user);
    }

    @Test
    public void getByLoginNonExistent() {
        String login = "nonexistent";
        when(userDao.getByLogin(login)).thenReturn(null);
        assertNull(userService.getByLogin(login));
        verify(userDao).getByLogin(login);
    }





}
