package cz.muni.fi.pa165.w2018.dndtroops.dao;

import cz.muni.fi.pa165.w2018.dndtroops.DndTroopsApplicationContext;
import cz.muni.fi.pa165.w2018.dndtroops.entity.Group;
import cz.muni.fi.pa165.w2018.dndtroops.entity.Hero;
import cz.muni.fi.pa165.w2018.dndtroops.enums.Race;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

import java.util.List;

import static org.testng.Assert.*;

/**
 * Tests for HeroDao
 *
 * @author Daniel Fecko 445539
 */
@ContextConfiguration(classes = DndTroopsApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class HeroDaoTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private HeroDao heroDao;

	@PersistenceContext
	private EntityManager em;

	@BeforeMethod
	public void setUp(){
		em.persist(createDefaultGroup());
	}

	@Test
	public void getHeroNotExistingId(){
		Hero hero = createDefaultHero();
		em.persist(hero);
		assertNull(heroDao.getById(hero.getId() + 1));
	}

	@Test
	public void getHero(){
		Hero hero = createDefaultHero();
		em.persist(hero);
		assertEquals(hero, heroDao.getById(hero.getId()));
	}

	@Test
	public void getAllEmpty(){
		assertTrue(heroDao.getAll().isEmpty());
	}

	@Test
	public void getAll(){
		Hero hero1 = createDefaultHero();
		Hero hero2 = createDefaultHero();
		hero2.setName("Milo");

		em.persist(hero1);
		em.persist(hero2);

		Hero expect1 = createDefaultHero();
		Hero expect2 = createDefaultHero();
		expect2.setName("Milo");

		List<Hero> result = heroDao.getAll();
		assertEquals(2, result.size());
		assertTrue(result.contains(expect1));
		assertTrue(result.contains(expect2));
	}

	@Test
	public void createHero() {
		Hero hero = createDefaultHero();
		heroDao.create(hero);
		assertEquals(hero, em.find(Hero.class, hero.getId()));
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void createHeroNull() {
		heroDao.create(null);
	}

	@Test(expectedExceptions = ConstraintViolationException.class)
	public void createHeroNullName() {
		Hero hero = createDefaultHero();
		hero.setName(null);
		heroDao.create(hero);
	}

	@Test(expectedExceptions = ConstraintViolationException.class)
	public void createHeroNullExperience() {
		Hero hero = createDefaultHero();
		hero.setExperience(null);
		heroDao.create(hero);
	}

	@Test(expectedExceptions = ConstraintViolationException.class)
	public void createHeroNullRace() {
		Hero hero = createDefaultHero();
		hero.setRace(null);
		heroDao.create(hero);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void createHeroNullGroup() {
		Hero hero = createDefaultHero();
		hero.setGroup(null);
		heroDao.create(hero);
	}

	@Test
	public void createHeroNoGroup() {
		Hero hero = new Hero();
		hero.setName("Jordan");
		hero.setExperience(666L);
		hero.setRace(Race.ELF);
		heroDao.create(hero);
	}

	@Test
	public void updateHero() {
		Hero hero = createDefaultHero();
		em.persist(hero);
		assertNotNull(em.find(Hero.class, hero.getId()));

		Hero modified = createDefaultHero();
		modified.setId(hero.getId());
		modified.setExperience(1000L);
		modified.setRace(Race.ELF);
		modified.setName("Milo");

		heroDao.update(modified);
		assertEquals(hero, modified);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void updateHeroNull() {
		heroDao.update(null);
	}

	@Test(expectedExceptions = ConstraintViolationException.class)
	public void updateHeroNameNull() {
		Hero hero = createDefaultHero();
		em.persist(hero);
		assertNotNull(em.find(Hero.class, hero.getId()));

		Hero modified = createDefaultHero();
		modified.setName(null);

		heroDao.update(modified);
		assertEquals(hero, modified);
	}

	@Test(expectedExceptions = ConstraintViolationException.class)
	public void updateHeroExperienceNull() {
		Hero hero = createDefaultHero();
		em.persist(hero);
		assertNotNull(em.find(Hero.class, hero.getId()));

		Hero modified = createDefaultHero();
		modified.setExperience(null);

		heroDao.update(modified);
		assertEquals(hero, modified);
	}


	@Test(expectedExceptions = ConstraintViolationException.class)
	public void updateHeroRaceNull() {
		Hero hero = createDefaultHero();
		em.persist(hero);
		assertNotNull(em.find(Hero.class, hero.getId()));

		Hero modified = createDefaultHero();
		modified.setRace(null);

		heroDao.update(modified);
		assertEquals(hero, modified);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void updateHeroGroupNull() {
		Hero hero = createDefaultHero();
		em.persist(hero);
		assertNotNull(em.find(Hero.class, hero.getId()));

		Hero modified = createDefaultHero();
		modified.setGroup(null);

		heroDao.update(modified);
		assertEquals(hero, modified);
	}

	@Test
	public void removeHero() {
		Hero hero = createDefaultHero();
		em.persist(hero);
		assertNotNull(em.find(Hero.class, hero.getId()));

		heroDao.remove(hero);
		assertNull(em.find(Hero.class, hero.getId()));
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void removeNullHero() {
		heroDao.remove(null);
	}

	private Hero createDefaultHero() {
		Hero hero = new Hero();
		hero.setName("Ben Shapiro");
		hero.setExperience(9000L);
		hero.setRace(Race.HUMAN);
		Group g = em.createQuery("SELECT g FROM groups g WHERE g.name='SJW slayers'", Group.class).getSingleResult();
		hero.setGroup(g);
		return hero;
	}

	private Group createDefaultGroup() {
		Group group = new Group();
		group.setName("SJW slayers");
		return group;
	}
}
