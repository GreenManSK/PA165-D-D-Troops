package cz.muni.fi.pa165.w2018.dndtroops.backend.dao;

import cz.muni.fi.pa165.w2018.dndtroops.backend.DndTroopsApplicationContext;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Group;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Hero;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Role;
import cz.muni.fi.pa165.w2018.dndtroops.backend.enums.Race;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

import java.util.List;

import static org.testng.Assert.*;


/**
 * Tests for the GroupDao
 *
 * @author Dusan Hetlerovic
 */
@ContextConfiguration(classes = DndTroopsApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class GroupDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private GroupDao groupDao;

    @PersistenceContext
    private EntityManager em;


    /*
        Tests for getting groups
     */

    @Test
    public void getGroupWithNonexistentId() {
        Group g = createDefaultGroup();
        em.persist(g);
        assertNull(groupDao.getById(g.getId() + 1));
    }

    @Test
    public void getAllGroups() {
        Group g1 = createDefaultGroup();
        Group g2 = createDefaultGroup();
        g1.setName("Shrek: I never thought I\'d die fighting alongside an elf.");
        g2.setName("Legoland: How about alongside a friend?");
        em.persist(g1);
        em.persist(g2);

        List<Group> groups = groupDao.getAll();

        assertEquals(groups.size(), 2);
        assertTrue(groups.contains(g1));
        assertTrue(groups.contains(g2));
    }

    @Test
    public void getAllWithNoGroups() {
        assertNotNull(groupDao.getAll());
        assertTrue(groupDao.getAll().isEmpty());
    }

    @Test
    public void getGroupByName() {
        Group g = createDefaultGroup();
        assertNull(groupDao.getByName(g.getName()));

        em.persist(g);
        assertNotNull(em.find(Group.class, g.getId()));

        assertEquals(g,groupDao.getByName(g.getName()));
        assertNull(groupDao.getByName(g.getName().concat(" Mr. Farquaad!")));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void getGroupByNullName() {
        groupDao.getByName(null);
    }




    /*
        Tests for create
     */

    @Test
    public void createGroup() {
        Group g = createDefaultGroup();
        groupDao.create(g);
        Assert.assertEquals(groupDao.getById(g.getId()), g);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void createNullGroup() {
        groupDao.create(null);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createNullNameGroup() {
        Group g = createDefaultGroup();
        g.setName(null);
        groupDao.create(g);
    }

    @Test
    public void createNoHeroGroup() {
        Group g = new Group();
        g.setName("Get out of my swamp");
        em.persist(g);
    }


    /*
        Tests for update
     */

    @Test
    public void updateGroup() {
        Group g1 = createDefaultGroup();
        em.persist(g1);
        assertNotNull(em.find(Group.class, g1.getId()));

        Group g2 = createDefaultGroup();
        g2.setId(g1.getId());
        g2.setName("Logolass");
        g2.removeHero(createShrek());

        groupDao.update(g2);
        assertEquals(g1, g2);
        assertEquals(g1.getHeroes().size(), g2.getHeroes().size());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void updateNullGroup() {
        groupDao.update(null);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void updateNullNameGroup() {
        Group g1 = createDefaultGroup();
        em.persist(g1);
        assertNotNull(em.find(Group.class, g1.getId()));

        Group g2 = createDefaultGroup();
        g2.setName(null);

        groupDao.update(g2);
        assertEquals(g1, g2);
    }


    /*
        Tests for remove
     */

    @Test
    public void removeGroup() {
        Group g = createDefaultGroup();
        em.persist(g);
        assertNotNull(em.find(Group.class, g.getId()));

        groupDao.remove(g);
        assertNull(em.find(Group.class, g.getId()));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void removeNullGroup() {
        groupDao.remove(null);
    }


    /*
        Helper methods
     */

    private Group createDefaultGroup() {
        Group g = new Group();
        g.addHero(createShrek());
        g.addHero(createLegoland());
        g.setName("Get out of my swamp");

        return g;
    }

    private Hero createShrek() {
        Hero h = new Hero();
        h.setName("Shrek");
        h.setRace(Race.ORC);
        h.setExperience(100L);
        return h;
    }

    private Hero createLegoland() {
        Hero h = new Hero();
        h.setName("Legoland");
        h.setRace(Race.ELF);
        h.setExperience(200L);
        Role role = new Role();
        role.setName("Archer");
        h.addRole(role);
        return h;
    }


}
