package cz.muni.fi.pa165.w2018.dndtroops.service.facade;

import cz.muni.fi.pa165.w2018.dndtroops.api.dto.RoleChangeDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.dto.RoleDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.facade.RoleFacade;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Role;
import cz.muni.fi.pa165.w2018.dndtroops.service.BeanMappingService;
import cz.muni.fi.pa165.w2018.dndtroops.service.RoleService;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

/**
 * Tests for RoleFacade implementation
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class RoleFacadeImplTest extends AbstractTestNGSpringContextTests {
    @Mock
    private RoleService roleService;

    @Spy
    @Inject
    private BeanMappingService mappingService;

    @InjectMocks
    private RoleFacade roleFacade = new RoleFacadeImpl();

    private Role role1;
    private Role role2;
    private Role role3;

    @BeforeMethod
    public void setUp() {
        setUpRoles();
        MockitoAnnotations.initMocks(this);
        when(roleService.getById(role1.getId())).thenReturn(role1);
        when(roleService.getAll()).thenReturn(Arrays.asList(role1, role2));
        when(roleService.getByName(role2.getName())).thenReturn(role2);
    }

    @Test
    public void getById() {
        RoleDTO roleDTO = roleFacade.getById(role1.getId());
        verify(roleService).getById(role1.getId());
        assertEquals(role1, mappingService.mapTo(roleDTO, Role.class));
    }

    @Test
    public void getAll() {
        List<RoleDTO> roleDTOs = roleFacade.getAll();
        verify(roleService).getAll();
        assertEquals(2, roleDTOs.size());
        List<Role> roles = mappingService.mapTo(roleDTOs, Role.class);
        assertTrue(roles.contains(role1));
        assertTrue(roles.contains(role2));
    }

    @Test
    public void create() {
        RoleChangeDTO roleChangeDTO = mappingService.mapTo(role3, RoleChangeDTO.class);
        roleFacade.create(roleChangeDTO);
        verify(roleService).create(any(Role.class));
    }

    @Test
    public void update() {
        RoleChangeDTO roleChangeDTO = mappingService.mapTo(role3, RoleChangeDTO.class);
        roleChangeDTO.setName("Rouge");
        roleFacade.update(roleChangeDTO);
        verify(roleService).update(any(Role.class));
    }

    @Test
    public void delete() {
        roleFacade.delete(role1.getId());
        verify(roleService).delete(role1.getId());
    }

    @Test
    public void getByName() {
        RoleDTO roleDTO = roleFacade.getByName(role2.getName());
        verify(roleService).getByName(role2.getName());
        assertEquals(role2, mappingService.mapTo(roleDTO, Role.class));
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
        role3.setId(30L);
        role3.setName("Hunter");
        role3.setDescription("Not strong or proud");
    }
}