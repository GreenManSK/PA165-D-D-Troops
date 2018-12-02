package cz.muni.fi.pa165.w2018.dndtroops.service.facade;

import cz.muni.fi.pa165.w2018.dndtroops.api.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.dto.UserDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.facade.UserFacade;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.User;
import cz.muni.fi.pa165.w2018.dndtroops.backend.enums.UserType;
import cz.muni.fi.pa165.w2018.dndtroops.service.BeanMappingService;
import cz.muni.fi.pa165.w2018.dndtroops.service.UserService;
import cz.muni.fi.pa165.w2018.dndtroops.service.configuration.ServiceConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import static org.testng.Assert.*;

/**
 * Tests for UserFacade implementation
 *
 * @author Marek Valko
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class UserFacadeImplTest extends AbstractTestNGSpringContextTests {
    @Mock
    private UserService userService;

    @Spy
    @Inject
    private BeanMappingService beanMappingService;

    @InjectMocks
    private UserFacade userFacade = new UserFacadeImpl();

    private User user;
    private User admin;
    private User user2;

    public static final String password = "password";

    @BeforeMethod
    public void setUp() {
        setUpUsers();
        MockitoAnnotations.initMocks(this);
    }

    private void setUpUsers() {
        user = new User();
        user.setId(1L);
        user.setLogin("user");
        user.setType(UserType.USER);

        admin = new User();
        admin.setId(2L);
        admin.setLogin("admin");
        admin.setType(UserType.ADMIN);

        user2 = new User();
        user2.setLogin("user2");
        user2.setType(UserType.USER);

    }

    @Test
    public void getById() {
        when(userService.getById(user.getId())).thenReturn(user);
        UserDTO userDTO = userFacade.getById(user.getId());
        verify(userService).getById(user.getId());
        assertEquals(user, beanMappingService.mapTo(userDTO, User.class));
    }

    @Test
    public void getByLogin() {
        when(userService.getByLogin(user.getLogin())).thenReturn(user);
        UserDTO userDTO = userFacade.getByLogin(user.getLogin());
        verify(userService).getByLogin(user.getLogin());
        assertEquals(user, beanMappingService.mapTo(userDTO, User.class));
    }

    @Test
    public void getAll() {
        when(userService.getAll()).thenReturn(Arrays.asList(user, admin));
        List<UserDTO> userDTOs = userFacade.getAll();
        verify(userService).getAll();
        List<User> users = beanMappingService.mapTo(userDTOs, User.class);
        assertEquals(2, users.size());
        assertTrue(users.contains(user));
        assertTrue(users.contains(admin));
    }

    @Test
    public void create() {
        when(userService.getByLogin(user.getLogin())).thenReturn(user);
        UserDTO userDTO = beanMappingService.mapTo(user2, UserDTO.class);
        userFacade.createUser(userDTO, password);
        verify(userService).createUser(any(User.class),password);
    }

    @Test
    public void isUser() {
        when(userService.getById(user.getId())).thenReturn(user);
        when(userService.getById(admin.getId())).thenReturn(admin);
        when(userService.isUser(user)).thenReturn(true);
        when(userService.isUser(admin)).thenReturn(false);
        assertTrue(userFacade.isUser(user.getId()));
        assertFalse(userFacade.isUser(admin.getId()));
        verify(userService).getById(user.getId());
        verify(userService).getById(admin.getId());
        verify(userService).isUser(user);
        verify(userService).isUser(admin);
    }

    @Test
    public void isAdmin() {
        when(userService.getById(user.getId())).thenReturn(user);
        when(userService.getById(admin.getId())).thenReturn(admin);
        when(userService.isUser(user)).thenReturn(false);
        when(userService.isUser(admin)).thenReturn(true);
        assertFalse(userFacade.isUser(user.getId()));
        assertTrue(userFacade.isUser(admin.getId()));
        verify(userService).getById(user.getId());
        verify(userService).getById(admin.getId());
        verify(userService).isUser(user);
        verify(userService).isUser(admin);
    }

    @Test
    public void authenticateSuccess() {
        UserAuthenticateDTO ua = new UserAuthenticateDTO();
        ua.setLogin("user");
        when(userService.getByLogin(user.getLogin())).thenReturn(user);
        when(userService.authenticate(user,password)).thenReturn(true);
        assertTrue(userFacade.authenticateUser(ua,password));
        verify(userService).getByLogin(user.getLogin());
        verify(userService).authenticate(user, password);
    }

    @Test
    public void authenticateFailure() {
        UserAuthenticateDTO ua = new UserAuthenticateDTO();
        ua.setLogin("user");
        String wrongPassword = password + "something";
        when(userService.getByLogin(user.getLogin())).thenReturn(user);
        when(userService.authenticate(user, wrongPassword)).thenReturn(false);
        assertFalse(userFacade.authenticateUser(ua, wrongPassword));
        verify(userService).getByLogin(user.getLogin());
        verify(userService).authenticate(user, wrongPassword);

    }



}
