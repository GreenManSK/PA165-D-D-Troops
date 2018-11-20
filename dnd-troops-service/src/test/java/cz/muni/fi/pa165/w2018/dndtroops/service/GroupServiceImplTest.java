package cz.muni.fi.pa165.w2018.dndtroops.service;

import cz.muni.fi.pa165.w2018.dndtroops.backend.dao.GroupDao;
import cz.muni.fi.pa165.w2018.dndtroops.backend.dao.HeroDao;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Group;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Hero;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Role;
import cz.muni.fi.pa165.w2018.dndtroops.backend.enums.Race;
import cz.muni.fi.pa165.w2018.dndtroops.service.configuration.ServiceConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

/**
 * Tests for GroupServiceImpl
 *
 * @author Daniel Fecko 445539
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class GroupServiceImplTest extends AbstractTestNGSpringContextTests {

	@Mock
	private GroupDao groupDao;

	@Mock
	private HeroDao heroDao;

	@Inject
	@InjectMocks
	private GroupService groupService;

	private Group group1;
	private Group group2;
	private Group group3;

	private Role role1;
	private Role role2;

	private Hero hero1;
	private Hero hero2;
	private Hero hero3;

	@BeforeMethod
	public void setUp() {
		setUpEntities();
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getById() {
		when(groupDao.getById(group1.getId())).thenReturn(group1);
		Group group = groupService.getById(group1.getId());
		verify(groupDao).getById(group1.getId());
		assertEquals(group1, group);
	}

	@Test
	public void getByIdNonexistent() {
		long id = 9999L;
		when(groupDao.getById(id)).thenReturn(null);
		assertNull(groupService.getById(id));
		verify(groupDao).getById(id);
	}

	@Test
	public void getAll() {
		List<Group> groups = Arrays.asList(group1, group2, group3);
		when(groupDao.getAll()).thenReturn(new ArrayList<>(groups));
		List<Group> allGroups = groupService.getAll();
		verify(groupDao).getAll();
		assertEquals(groups, allGroups);
	}

	@Test
	public void getAllEmpty() {
		when(groupDao.getAll()).thenReturn(Collections.emptyList());
		List<Group> allGroups = groupService.getAll();
		verify(groupDao).getAll();
		assertEquals(0, allGroups.size());
	}

	@Test
	public void getByName() {
		when(groupDao.getByName(group2.getName())).thenReturn(group2);
		Group group = groupService.getByName(group2.getName());
		verify(groupDao).getByName(group2.getName());
		assertEquals(group2, group);
	}

	@Test
	public void getByNameNonexistent() {
		String name = "Nonexistent Group Name";
		when(groupDao.getByName(name)).thenReturn(null);
		assertNull(groupService.getByName(name));
		verify(groupDao).getByName(name);
	}

	@Test
	public void create() {
		doAnswer(invocationOnMock -> {
			Group group = (Group) invocationOnMock.getArguments()[0];
			group.setId(0L);
			return null;
		}).when(groupDao).create(group3);
		groupService.create(group3);
		verify(groupDao).create(group3);
		assertNotNull(group3.getId());
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void createNull() {
		doThrow(new NullPointerException()).when(groupDao).create(null);
		groupService.create(null);
	}

	@Test(expectedExceptions = DataIntegrityViolationException.class)
	public void createInvalidGroup() {
		Group group = new Group();
		doThrow(new DataIntegrityViolationException("")).when(groupDao).create(group);
		groupService.create(group);
	}

	@Test
	public void update() {
		doNothing().when(groupDao).update(group2);
		groupService.update(group2);
		verify(groupDao).update(group2);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void updateNull() {
		doThrow(new NullPointerException()).when(groupDao).update(null);
		groupService.update(null);
	}

	@Test(expectedExceptions = DataIntegrityViolationException.class)
	public void updateInvalidGroup() {
		group2.setName(null);
		doThrow(new DataIntegrityViolationException("")).when(groupDao).update(group2);
		groupService.update(group2);
	}

	@Test
	public void delete() {
		when(groupDao.getById(group1.getId())).thenReturn(group1);
		doNothing().when(groupDao).remove(group1);
		groupService.delete(group1.getId());
		verify(groupDao).remove(group1);
	}

	@Test
	public void deleteNonexistent() {
		long id = 9999L;
		when(groupDao.getById(id)).thenReturn(null);
		groupService.delete(id);
		verify(groupDao, never()).remove(any());
	}

	@Test
	public void getHeroesFromGroup() {
		when(groupDao.getById(group1.getId())).thenReturn(group1);
		List<Hero> heroes = groupService.getHeroesFromGroup(group1.getId());
		assertEquals(heroes.size(), 2);
		assertTrue(heroes.contains(hero1));
		assertTrue(heroes.contains(hero2));
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void getHeroesFromGroupsWithGroupNonExistent() {
		when(groupDao.getById(999L)).thenThrow(new NullPointerException());
		List<Hero> heroes = groupService.getHeroesFromGroup(999L);
		assertEquals(heroes.size(), 0);
	}

	@Test
	public void getHeroesFromGroupsGroupWithNoHeroes() {
		when(groupDao.getById(group3.getId())).thenReturn(group3);
		List<Hero> heroes = groupService.getHeroesFromGroup(group3.getId());
		assertEquals(0, heroes.size());
	}

	@Test
	public void getGroupsWithoutRoles() {
		when(groupDao.getAll()).thenReturn(Arrays.asList(group1, group2, group3));
		List<Group> groups = groupService.getGroupsWithoutRoles(Collections.singletonList(role1.getId()));
		verify(groupDao).getAll();
		List<Group> expected = new ArrayList<>(Arrays.asList(group2, group3));
		assertEquals(groups, expected);
	}

	@Test
	public void getGroupsWithoutRolesNoSuchGroups() {
		when(groupDao.getAll()).thenReturn(Collections.singletonList(group1));
		List<Group> groups = groupService.getGroupsWithoutRoles(Collections.singletonList(role1.getId()));
		verify(groupDao).getAll();
		assertEquals(groups.size(), 0);

	}

	@Test
	public void getGroupsWithoutRolesNoGivenRoles() {
		when(groupDao.getAll()).thenReturn(Arrays.asList(group1, group2, group3));
		List<Group> groups = groupService.getGroupsWithoutRoles(Collections.emptyList());
		verify(groupDao).getAll();
		List<Group> expected = new ArrayList<>(Arrays.asList(group1, group2, group3));
		assertEquals(groups, expected);
	}

	@Test
	public void getGroupsWithoutRolesGivenNonExistentRoles() {
		when(groupDao.getAll()).thenReturn(Arrays.asList(group1, group2, group3));
		List<Group> groups = groupService.getGroupsWithoutRoles(Collections.singletonList(555L));
		verify(groupDao).getAll();
		List<Group> expected = new ArrayList<>(Arrays.asList(group1, group2, group3));
		assertEquals(groups, expected);

	}

	@Test
	public void getGroupsWithoutRoles0GroupsExists() {
		when(groupDao.getAll()).thenReturn(Collections.emptyList());
		List<Group> groups = groupService.getGroupsWithoutRoles(Collections.singletonList(role2.getId()));
		verify(groupDao).getAll();
		List<Group> expect = new ArrayList<>();
		assertEquals(0, groups.size());
		assertEquals(groups, expect);

	}

	@Test
	public void addHero() {
		when(groupDao.getById(group3.getId())).thenReturn(group3);
		when(heroDao.getById(hero3.getId())).thenReturn(hero3);
		groupService.addHero(group3.getId(), hero3.getId());
		verify(groupDao).getById(group3.getId());
		verify(heroDao).getById(hero3.getId());
		Set<Hero> heroes = group3.getHeroes();
		assertEquals(heroes.size(), 1);
		assertTrue(heroes.contains(hero3));
	}

	@Test
	public void addHeroNonExistentGroup() {
		when(groupDao.getById(456L)).thenReturn(null);
		when(heroDao.getById(hero3.getId())).thenReturn(hero3);
		groupService.addHero(456L, hero3.getId());
		verify(groupDao).getById(456L);
		verify(heroDao).getById(hero3.getId());
		assertEquals(hero3.getGroup(), group2);
	}

	@Test
	public void addHeroNonExistentHero() {
		when(groupDao.getById(group3.getId())).thenReturn(group3);
		when(heroDao.getById(6000L)).thenReturn(null);
		groupService.addHero(group3.getId(), 6000L);
		verify(groupDao).getById(group3.getId());
		verify(heroDao).getById(6000L);
		Set<Hero> heroes = group3.getHeroes();
		assertEquals(heroes.size(), 0);
	}

	@Test
	public void addHeroAlreadyIsIn() {
		when(groupDao.getById(group1.getId())).thenReturn(group1);
		when(heroDao.getById(hero1.getId())).thenReturn(hero1);
		groupService.addHero(group1.getId(), hero1.getId());
		verify(groupDao).getById(group1.getId());
		verify(heroDao).getById(hero1.getId());
		Set<Hero> heroes = group1.getHeroes();
		assertEquals(heroes.size(), 2);
		assertTrue(heroes.contains(hero1));
		assertTrue(heroes.contains(hero2));
	}

	@Test
	public void removeHero() {
		when(groupDao.getById(group1.getId())).thenReturn(group1);
		when(heroDao.getById(hero1.getId())).thenReturn(hero1);
		groupService.removeHero(group1.getId(), hero1.getId());
		verify(groupDao).getById(group1.getId());
		verify(heroDao).getById(hero1.getId());
		Set<Hero> heroes = group1.getHeroes();
		assertEquals(heroes.size(), 1);
		assertTrue(heroes.contains(hero2));
	}

	@Test
	public void removeHeroNonExistentGroup() {
		when(groupDao.getById(654L)).thenReturn(null);
		when(heroDao.getById(hero1.getId())).thenReturn(hero1);
		groupService.removeHero(654L, hero1.getId());
		verify(groupDao).getById(654L);
		verify(heroDao).getById(hero1.getId());
		assertEquals(hero1.getGroup(), group1);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void removeHeroNonExistentHero() {
		when(groupDao.getById(group1.getId())).thenReturn(group1);
		when(heroDao.getById(6000L)).thenReturn(null);
		groupService.removeHero(group1.getId(), 6000L);
		verify(groupDao).getById(group1.getId());
		verify(heroDao).getById(6000L);
	}

	@Test
	public void removeHeroIsNotIn() {
		when(groupDao.getById(group3.getId())).thenReturn(group3);
		when(heroDao.getById(hero3.getId())).thenReturn(hero3);
		groupService.removeHero(group3.getId(), hero3.getId());
		verify(groupDao).getById(group3.getId());
		verify(heroDao).getById(hero3.getId());
		Set<Hero> heroes = group3.getHeroes();
		assertEquals(heroes.size(), 0);
	}

	private void setUpEntities() {
		group1 = new Group();
		group1.setId(666L);
		group1.setName("Devils");


		group2 = new Group();
		group2.setId(1L);
		group2.setName("GroupOfReallyFineHeroes");

		group3 = new Group();
		group3.setId(420L);
		group3.setName("TrailerParkBoys");

		role1 = new Role();
		role1.setId(10L);
		role1.setName("Warrior");
		role1.setDescription("Strong and proud");

		role2 = new Role();
		role2.setId(11L);
		role2.setName("Mage");
		role2.setDescription("Doing some magic");

		hero1 = new Hero();
		hero1.setName("WarriorKing");
		hero1.setExperience(9000L);
		hero1.addRole(role1);
		hero1.setId(9000L);
		hero1.setRace(Race.HUMAN);

		hero2 = new Hero();
		hero2.setName("MageKing");
		hero2.setExperience(8000L);
		hero2.addRole(role2);
		hero2.setId(8000L);
		hero2.setRace(Race.HUMAN);

		hero3 = new Hero();
		hero3.setName("FineMage");
		hero3.addRole(role2);
		hero3.setRace(Race.ELF);
		hero3.setId(300L);
		hero3.setExperience(7000L);

		group1.addHero(hero1);
		group1.addHero(hero2);
		group2.addHero(hero3);

	}
}
