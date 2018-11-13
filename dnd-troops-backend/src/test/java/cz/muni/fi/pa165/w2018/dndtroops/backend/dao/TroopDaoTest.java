package cz.muni.fi.pa165.w2018.dndtroops.backend.dao;

import cz.muni.fi.pa165.w2018.dndtroops.backend.DndTroopsApplicationContext;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Troop;
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
 * Tests for TroopDao implementation
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@ContextConfiguration(classes = DndTroopsApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class TroopDaoTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private TroopDao troopDao;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void getTroopNotExistingId() {
        Troop troop = createDefaultTroop();
        em.persist(troop);
        assertNull(troopDao.getById(troop.getId() + 1));
    }

    @Test
    public void getTroop() {
        Troop troop = createDefaultTroop();
        em.persist(troop);
        Assert.assertEquals(troop, troopDao.getById(troop.getId()));
    }

    @Test
    public void getAllEmpty() {
        assertTrue(troopDao.getAll().isEmpty());
    }

    @Test
    public void getAll() {
        Troop troop1 = createDefaultTroop();
        Troop troop2 = createDefaultTroop();
        troop2.setName("Goblin");

        em.persist(troop1);
        em.persist(troop2);

        Troop expect1 = createDefaultTroop();
        Troop expect2 = createDefaultTroop();
        expect2.setName("Goblin");

        List<Troop> result = troopDao.getAll();
        assertEquals(2, result.size());
        assertTrue(result.contains(expect1));
        assertTrue(result.contains(expect2));
    }

    @Test
    public void getAllByNameEmpty() {
        assertTrue(troopDao.getAllByName("n").isEmpty());
    }

    @Test
    public void getAllByNameNull(){
        assertTrue(troopDao.getAllByName(null).isEmpty());
    }

    @Test
    public void getAllByName(){
        Troop troop1 = createDefaultTroop();
        Troop troop2 = createDefaultTroop();
        troop2.setName("Goblin");

        em.persist(troop1);
        em.persist(troop2);

        Troop expect2 = createDefaultTroop();
        expect2.setName("Goblin");

        List<Troop> result = troopDao.getAllByName("Goblin");
        assertEquals(1, result.size());
        assertTrue(result.contains(expect2));
    }

    @Test
    public void getAllByMissionEmpty() {
        assertTrue(troopDao.getAllByMission("m").isEmpty());
    }

    @Test
    public void getAllByMissionNull(){
        assertTrue(troopDao.getAllByMission(null).isEmpty());
    }

    @Test
    public void getAllByMission(){
        Troop troop1 = createDefaultTroop();
        Troop troop2 = createDefaultTroop();
        troop2.setName("Goblin");
        troop2.setMission("Kick ass");

        em.persist(troop1);
        em.persist(troop2);

        Troop expect2 = createDefaultTroop();
        expect2.setName("Goblin");
        expect2.setMission("Kick ass");

        List<Troop> result = troopDao.getAllByMission("Kick ass");
        assertEquals(1, result.size());
        assertTrue(result.contains(expect2));
    }

    @Test
    public void createTroop() {
        Troop troop = createDefaultTroop();
        troopDao.create(troop);
        assertEquals(troop, em.find(Troop.class, troop.getId()));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void createTroopNull() {
        troopDao.create(null);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createTroopNullName() {
        Troop troop = createDefaultTroop();
        troop.setName(null);
        troopDao.create(troop);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createTroopNullMission() {
        Troop troop = createDefaultTroop();
        troop.setMission(null);
        troopDao.create(troop);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createTroopNegativeGold() {
        Troop troop = createDefaultTroop();
        troop.setGold(-50L);
        troopDao.create(troop);
    }

    @Test
    public void updateTroop() {
        Troop troop = createDefaultTroop();
        em.persist(troop);
        assertNotNull(em.find(Troop.class, troop.getId()));

        Troop modified = createDefaultTroop();
        modified.setId(troop.getId());
        modified.setGold(1000L);
        modified.setMission("Kill");
        modified.setName("Dragon");

        troopDao.update(modified);
        assertEquals(troop, modified);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void updateTroopNull() {
        troopDao.update(null);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void updateTroopNameNull() {
        Troop troop = createDefaultTroop();
        em.persist(troop);
        assertNotNull(em.find(Troop.class, troop.getId()));

        Troop modified = createDefaultTroop();
        modified.setName(null);

        troopDao.update(modified);
        assertEquals(troop, modified);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void updateTroopMissionNull() {
        Troop troop = createDefaultTroop();
        em.persist(troop);
        assertNotNull(em.find(Troop.class, troop.getId()));

        Troop modified = createDefaultTroop();
        modified.setMission(null);

        troopDao.update(modified);
        assertEquals(troop, modified);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void updateTroopNegativeGold() {
        Troop troop = createDefaultTroop();
        em.persist(troop);
        assertNotNull(em.find(Troop.class, troop.getId()));

        Troop modified = createDefaultTroop();
        modified.setGold(-500L);

        troopDao.update(modified);
        assertEquals(troop, modified);
    }

    @Test
    public void removeTroop() {
        Troop troop = createDefaultTroop();
        em.persist(troop);
        assertNotNull(em.find(Troop.class, troop.getId()));

        troopDao.remove(troop);
        assertNull(em.find(Troop.class, troop.getId()));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void removeNullTroop() {
        troopDao.remove(null);
    }

    private Troop createDefaultTroop() {
        Troop troop = new Troop();
        troop.setName("Cat Dragon");
        troop.setMission("Be cute and kill");
        troop.setGold(500L);
        return troop;
    }
}