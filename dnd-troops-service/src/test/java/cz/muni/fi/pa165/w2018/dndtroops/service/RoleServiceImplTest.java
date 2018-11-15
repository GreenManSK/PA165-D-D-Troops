package cz.muni.fi.pa165.w2018.dndtroops.service;

import cz.muni.fi.pa165.w2018.dndtroops.backend.dao.RoleDao;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Role;
import cz.muni.fi.pa165.w2018.dndtroops.service.configuration.ServiceConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

/**
 * Tests for RoleService implementation
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class RoleServiceImplTest extends AbstractTestNGSpringContextTests {
    @Mock
    private RoleDao roleDao;

    @Inject
    @InjectMocks
    private RoleService roleService;

    private Role role1;
    private Role role2;
    private Role role3;


    @BeforeMethod
    public void setUp() {
        setUpRoles();
        MockitoAnnotations.initMocks(this);
    }

    private void setUpRoles() {
        role1 = new Role();
        role1.setId(10L);
        role1.setName("Warrior");
        role1.setDescription("Strong and proud");

        role2 = new Role();
        role2.setId(20L);
        role2.setName("Mage");
        role2.setDescription("Not so strong bud prouder");

        role3 = new Role();
        role3.setName("Hunter");
        role3.setDescription("Not strong or proud");
    }

    @Test
    public void getById() {
        when(roleDao.getById(role1.getId())).thenReturn(role1);
        Role role = roleService.getById(role1.getId());
        verify(roleDao).getById(role1.getId());
        assertEquals(role1, role);
    }

    @Test
    public void getByIdNonexistent() {
        long id = 9999L;
        when(roleDao.getById(id)).thenReturn(null);
        assertNull(roleService.getById(id));
        verify(roleDao).getById(id);
    }

    @Test
    public void getAll() {
        List<Role> roles = Arrays.asList(role1, role2);
        when(roleDao.getAll()).thenReturn(new ArrayList<>(roles));
        List<Role> allRoles = roleService.getAll();
        verify(roleDao).getAll();
        assertEquals(roles, allRoles);
    }

    @Test
    public void getAllEmpty() {
        when(roleDao.getAll()).thenReturn(Collections.emptyList());
        List<Role> allRoles = roleService.getAll();
        verify(roleDao).getAll();
        assertEquals(0, allRoles.size());
    }

    @Test
    public void getByName() {
        when(roleDao.getByName(role2.getName())).thenReturn(role2);
        Role role = roleService.getByName(role2.getName());
        verify(roleDao).getByName(role2.getName());
        assertEquals(role2, role);
    }

    @Test
    public void getByNameNonexistent() {
        String name = "Nonexistent Role Name";
        when(roleDao.getByName(name)).thenReturn(null);
        assertNull(roleService.getByName(name));
        verify(roleDao).getByName(name);
    }

    @Test
    public void create() {
        doAnswer(invocationOnMock -> {
            Role role = (Role) invocationOnMock.getArguments()[0];
            role.setId(64L);
            return null;
        }).when(roleDao).create(role3);
        roleService.create(role3);
        verify(roleDao).create(role3);
        assertNotNull(role3.getId());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void createNull() {
        doThrow(new NullPointerException()).when(roleDao).create(null);
        roleService.create(null);
    }

    @Test(expectedExceptions = DataIntegrityViolationException.class)
    public void createInvalidRole() {
        Role role = new Role();
        doThrow(new DataIntegrityViolationException("")).when(roleDao).create(role);
        roleService.create(role);
    }

    @Test
    public void update() {
        doNothing().when(roleDao).update(role2);
        roleService.update(role2);
        verify(roleDao).update(role2);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void updateNull() {
        doThrow(new NullPointerException()).when(roleDao).update(null);
        roleService.update(null);
    }

    @Test(expectedExceptions = DataIntegrityViolationException.class)
    public void updateInvalidRole() {
        role2.setName(null);
        doThrow(new DataIntegrityViolationException("")).when(roleDao).update(role2);
        roleService.update(role2);
    }

    @Test
    public void delete() {
        when(roleDao.getById(role1.getId())).thenReturn(role1);
        doNothing().when(roleDao).delete(role1);
        roleService.delete(role1.getId());
        verify(roleDao).delete(role1);
    }

    @Test
    public void deleteNonexistent() {
        long id = 9999L;
        when(roleDao.getById(id)).thenReturn(null);
        roleService.delete(id);
        verify(roleDao, never()).delete(any());
    }
}