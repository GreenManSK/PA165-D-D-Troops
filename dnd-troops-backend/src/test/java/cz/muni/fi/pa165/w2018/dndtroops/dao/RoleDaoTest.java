package cz.muni.fi.pa165.w2018.dndtroops.dao;

import cz.muni.fi.pa165.w2018.dndtroops.DndTroopsApplicationContext;
import cz.muni.fi.pa165.w2018.dndtroops.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

import java.util.List;

import static org.testng.Assert.*;
/**
 * Tests for RoleDao
 *
 * @ author Marek Valko <valko.marek@gmail.com>
 */
@ContextConfiguration(classes = DndTroopsApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class RoleDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private RoleDao roleDao;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void getRoleNotExistingId() {
        Role role = createDefaultRole();
        em.persist(role);
        assertNull(roleDao.getById(role.getId() + 1));
    }

    @Test
    public void getRole() {
        Role role = createDefaultRole();
        em.persist(role);
        assertEquals(role,roleDao.getById(role.getId()));
    }

    public void getAll() {
        Role role1 = createDefaultRole();
        Role role2 = createDefaultRole();
        role2.setName("Fighter");

        em.persist(role1);
        em.persist(role2);

        Role expect1 = createDefaultRole();
        Role expect2 = createDefaultRole();
        expect2.setName("Fighter");

        List<Role> result = roleDao.getAll();
        assertEquals(2, result.size());
        assertTrue(result.contains(expect1));
        assertTrue(result.contains(expect2));
    }

    @Test
    private void getAllEmpty() {
        assertTrue(roleDao.getAll().isEmpty());
    }

    @Test
    public void createRole() {
        Role role = createDefaultRole();
        roleDao.create(role);
        assertEquals(role, em.find(Role.class, role.getId()));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void createRoleNull() {
        roleDao.create(null);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createRoleNullName() {
        Role role = createDefaultRole();
        role.setName(null);
        roleDao.create(role);
    }

    @Test
    public void updateRole() {
        Role role = createDefaultRole();
        em.persist(role);
        assertNotNull(em.find(Role.class, role.getId()));

        Role modified = createDefaultRole();
        modified.setId(role.getId());
        modified.setName("Druid");
        modified.setDescription("Modified description of role Druid");
        roleDao.update(modified);
        assertEquals(role,modified);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void updateRoleNull() {
        roleDao.update(null);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void updateRoleNameNull() {
        Role role = createDefaultRole();
        em.persist(role);
        assertNotNull(em.find(Role.class, role.getId()));

        Role modified = createDefaultRole();
        modified.setName(null);

        roleDao.update(modified);
        assertEquals(role, modified);
    }

    @Test
    public void removeRole() {
        Role role = createDefaultRole();
        em.persist(role);
        assertNotNull(em.find(Role.class, role.getId()));

        roleDao.delete(role);
        assertNull(em.find(Role.class, role.getId()));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void removeNullRole() {
        roleDao.delete(null);
    }


    private Role createDefaultRole() {
        Role role = new Role();
        role.setName("magician");
        role.setDescription("example of role");
        return role;
    }

}
