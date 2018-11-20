package cz.muni.fi.pa165.w2018.dndtroops.service.facade;

import cz.muni.fi.pa165.w2018.dndtroops.api.dto.GroupDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.dto.GroupChangeDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.dto.HeroDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.facade.GroupFacade;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Group;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Hero;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Role;
import cz.muni.fi.pa165.w2018.dndtroops.backend.enums.Race;
import cz.muni.fi.pa165.w2018.dndtroops.service.BeanMappingService;
import cz.muni.fi.pa165.w2018.dndtroops.service.GroupService;
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
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Tests for GroupFacadeImpl
 *
 * @author Daniel Fecko 445539
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class GroupFacadeImplTest extends AbstractTestNGSpringContextTests {

	@Mock
	private GroupService groupService;

	@Spy
	@Inject
	private BeanMappingService mappingService;

	@InjectMocks
	private GroupFacade groupFacade = new GroupFacadeImpl();

	private Group group1;
	private Group group2;
	private Group group3;

	private Role role1;


	private Hero hero1;

	@BeforeMethod()
	public void setUp() {
		setUpEntities();
		MockitoAnnotations.initMocks(this);
		when(groupService.getById(group1.getId())).thenReturn(group1);
		when(groupService.getAll()).thenReturn(Arrays.asList(group1, group2));
		when(groupService.getByName(group2.getName())).thenReturn(group2);
		when(groupService.getGroupsWithoutRoles(Collections.singletonList(role1.getId()))).thenReturn(Collections.singletonList(group1));
		when(groupService.getHeroesFromGroup(group1.getId())).thenReturn(Collections.singletonList(hero1));
	}

	@Test
	public void getById() {
		GroupDTO groupDTO = groupFacade.getById(group1.getId());
		verify(groupService).getById(group1.getId());
		assertEquals(group1, mappingService.mapTo(groupDTO, Group.class));
	}

	@Test
	public void getAll() {
		List<GroupDTO> groupDTOs = groupFacade.getAll();
		verify(groupService).getAll();
		assertEquals(2, groupDTOs.size());
		List<Group> groups = mappingService.mapTo(groupDTOs, Group.class);
		assertTrue(groups.contains(group1));
		assertTrue(groups.contains(group2));
	}

	@Test
	public void create() {
		GroupChangeDTO groupChangeDTO = mappingService.mapTo(group3, GroupChangeDTO.class);
		groupFacade.create(groupChangeDTO);
		verify(groupService).create(any(Group.class));
	}

	@Test
	public void update() {
		GroupChangeDTO groupChangeDTO = mappingService.mapTo(group3, GroupChangeDTO.class);
		groupChangeDTO.setName("Rouges");
		groupFacade.update(groupChangeDTO);
		verify(groupService).update(any(Group.class));
	}

	@Test
	public void delete() {
		groupFacade.delete(group1.getId());
		verify(groupService).delete(group1.getId());
	}

	@Test
	public void addHero() {
		groupFacade.addHero(group1.getId(), hero1.getId());
		verify(groupService).addHero(group1.getId(), hero1.getId());
	}

	@Test
	public void removeHero() {
		groupFacade.removeHero(group1.getId(), hero1.getId());
		verify(groupService).removeHero(group1.getId(), hero1.getId());
	}

	@Test
	public void getByName() {
		GroupDTO groupDTO = groupFacade.getByName(group2.getName());
		verify(groupService).getByName(group2.getName());
		assertEquals(group2, mappingService.mapTo(groupDTO, Group.class));
	}

	@Test
	public void getGroupsWithoutRoles() {
		List<GroupDTO> groupDTOs = groupFacade.getGroupsWithoutRoles(Collections.singletonList(role1.getId()));
		verify(groupService).getGroupsWithoutRoles(Collections.singletonList(role1.getId()));
		assertEquals(1, groupDTOs.size());
		List<Group> groups = mappingService.mapTo(groupDTOs, Group.class);
		assertTrue(groups.contains(group1));
	}

	@Test
	public void getHeroesFromGroups() {
		List<HeroDTO> heroDTOs = groupFacade.getHeroesFromGroup(group1.getId());
		verify(groupService).getHeroesFromGroup(group1.getId());
		assertEquals(1, heroDTOs.size());
		List<Hero> heroes = mappingService.mapTo(heroDTOs, Hero.class);
		assertTrue(heroes.contains(hero1));
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

		hero1 = new Hero();
		hero1.addRole(role1);
		hero1.setName("WarriorKing");
		hero1.setExperience(9000L);
		hero1.setGroup(group1);
		hero1.setId(9000L);
		hero1.setRace(Race.HUMAN);

	}

}
