package cz.muni.fi.pa165.w2018.dndtroops.service;


import cz.muni.fi.pa165.w2018.dndtroops.backend.dao.HeroDao;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Group;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Hero;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Role;
import cz.muni.fi.pa165.w2018.dndtroops.backend.enums.Race;
import cz.muni.fi.pa165.w2018.dndtroops.service.configuration.ServiceConfiguration;
import javax.inject.Inject;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

/**
 * Tests for HeroService implementation
 *
 * In search part of the test, first I test, if it works correctly for some/all null parameters
 * Then I test edge cases and border values
 *
 * @author Marek Valko
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class HeroServiceImplTest extends AbstractTestNGSpringContextTests {
    @Mock
    private HeroDao heroDao;

    @Inject
    @InjectMocks
    private HeroService heroService;

    private Hero hero1;
    private Hero hero2;
    private Hero hero3;
    private Hero hero4;
    private Hero hero5;
    private Hero hero6;
    private Group group;
    private Role role;
    private Role roleNotAssigned;
    private List<Hero> searchHeroList;

    @BeforeMethod
    public void setUp() {
        setUpHeroes();
        MockitoAnnotations.initMocks(this);
    }

    private void setUpHeroes() {
        group = new Group();
        group.setId(1L);
        group.setName("Warriors");

        role = new Role();
        role.setId(1L);
        role.setName("Elf magician");
        role.setDescription("Something");

        roleNotAssigned = new Role();
        roleNotAssigned.setId(2L);
        roleNotAssigned.setName("Orc admiral");
        roleNotAssigned.setDescription("Main admiral of orcs");

        hero1 = new Hero();
        hero1.setName("Adam");
        hero1.setId(1L);
        hero1.setRace(Race.HUMAN);
        hero1.setExperience(10L);

        hero2 = new Hero();
        hero2.setId(2L);
        hero2.setName("Boris");
        hero2.setRace(Race.DWARF);
        hero2.setExperience(20L);
        hero2.setGroup(group);

        hero3 = new Hero();
        hero3.setName("Cyril");
        hero3.setExperience(30L);
        hero3.setRace(Race.DWARF);

        hero4 = new Hero();
        hero4 .setId(4L);
        hero4.setName("David");
        hero4.setExperience(5L);
        hero4.setRace(Race.ELF);
        hero4.addRole(role);

        hero5 = new Hero();
        hero5.setId(5L);
        hero5.setName("Emil");
        hero5.setExperience(0L);
        hero5.setRace(Race.GNOME);

        hero6 = new Hero();
        hero6.setId(6L);
        hero6.setName("Filip");
        hero6.setRace(Race.ELF);
        hero6.setExperience(6L);
        searchHeroList = Arrays.asList(hero1,hero2,hero4,hero5,hero6);

    }

    @Test
    public void getById() {
        when(heroDao.getById(hero1.getId())).thenReturn(hero1);
        Hero hero = heroService.getById(hero1.getId());
        verify(heroDao).getById(hero.getId());
        assertEquals(hero1, hero);
    }

    @Test
    public void getByIdWithGroup() {
        when(heroDao.getById(hero2.getId())).thenReturn(hero2);
        Hero hero = heroService.getById(hero2.getId());
        verify(heroDao).getById(hero.getId());
        assertEquals(hero2, hero);
        assertEquals(hero2.getGroup(),hero.getGroup());
    }

    @Test
    public void getByIdNonexistent() {
        long id = 9L;
        when(heroDao.getById(id)).thenReturn(null);
        assertNull(heroService.getById(id));
        verify(heroDao).getById(id);
    }

    @Test
    public void getAll() {
        List<Hero> heroes = Arrays.asList(hero1,hero2);
        when(heroDao.getAll()).thenReturn(new ArrayList<>(heroes));
        List<Hero> allHeroes = heroService.getAll();
        verify(heroDao).getAll();
        assertEquals(heroes,allHeroes);
    }

    @Test
    public void getAllEmpty() {
        when(heroDao.getAll()).thenReturn(Collections.emptyList());
        List<Hero> allHeroes = heroService.getAll();
        verify(heroDao).getAll();
        assertEquals(0,allHeroes.size());
    }

    @Test
    public void create() {
        doAnswer(invocationOnMock -> {
            Hero hero = (Hero) invocationOnMock.getArguments()[0];
            hero.setId(20L);
            return null;
        }).when(heroDao).create(hero3);
        heroService.create(hero3);
        verify(heroDao).create(hero3);
        assertNotNull(hero3.getId());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void createNull() {
        doThrow(new NullPointerException()).when(heroDao).create(null);
        heroService.create(null);
    }

    @Test(expectedExceptions = DataIntegrityViolationException.class)
    public void createInvalidHero() {
        Hero hero = new Hero();
        doThrow(new DataIntegrityViolationException("")).when(heroDao).create(hero);
        heroService.create(hero);
    }

    @Test
    public void update() {
        doNothing().when(heroDao).update(hero1);
        heroService.update(hero1);
        verify(heroDao).update(hero1);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void updateNull() {
        doThrow(new NullPointerException()).when(heroDao).update(null);
        heroService.update(null);
    }

    @Test(expectedExceptions = DataIntegrityViolationException.class)
    public void updateInvalidHero() {
        hero1.setName(null);
        doThrow(new DataIntegrityViolationException("")).when(heroDao).update(hero1);
        heroService.update(hero1);
    }

    @Test
    public void delete() {
        when(heroDao.getById(hero1.getId())).thenReturn(hero1);
        doNothing().when(heroDao).remove(hero1);
        heroService.delete(hero1.getId());
        verify(heroDao).remove(hero1);
    }

    @Test
    public void deleteNonexistent(){
        long id = 9L;
        when(heroDao.getById(id)).thenReturn(null);
        heroService.delete(id);
        verify(heroDao,never()).remove(any());
    }

    /**
     * Here continues tests for search method
     */

    @Test
    public void searchAllParameters() {
        when(heroDao.getAll()).thenReturn(searchHeroList);
        List<Hero> searchHeroes = heroService.search(role, Race.ELF, 0L, 20L);
        verify(heroDao).getAll();
        assertEquals(1,searchHeroes.size());
        assertTrue(searchHeroes.contains(hero4));
    }

    @Test
    public void searchFromGreaterTo() {
        when(heroDao.getAll()).thenReturn(searchHeroList);
        List<Hero> searchHeroes = heroService.search(null,null,10L,5L);
        verify(heroDao).getAll();
        assertEquals(0,searchHeroes.size());
    }

    @Test
    public void searchFromEqualsTo() {
        when(heroDao.getAll()).thenReturn(searchHeroList);
        List<Hero> searchHeroes = heroService.search(null,null,10L,10L);
        verify(heroDao).getAll();
        assertEquals(1, searchHeroes.size());
        assertTrue(searchHeroes.contains(hero1));
    }

    /**
     * Testing 0 - min experience
     */

    @Test
    public void searchFromZero() {
        when(heroDao.getAll()).thenReturn(searchHeroList);
        List<Hero> searchHeroes = heroService.search(null,null,0L,null);
        verify(heroDao).getAll();
        assertEquals(5,searchHeroes.size());
    }

    @Test
    public void searchToZero() {
        when(heroDao.getAll()).thenReturn(searchHeroList);
        List<Hero> searchHeroes = heroService.search(null,null,null,0L);
        verify(heroDao).getAll();
        assertEquals(1, searchHeroes.size());
        assertTrue(searchHeroes.contains(hero5));
    }

    @Test
    public void searchFromToZero() {
        when(heroDao.getAll()).thenReturn(searchHeroList);
        List<Hero> searchHeroes = heroService.search(null,null,0L,0L);
        verify(heroDao).getAll();
        assertEquals(1, searchHeroes.size());
        assertTrue(searchHeroes.contains(hero5));

    }

    /**
     * We have heroes with exp 0,5,6,10,20, testing inner borders 5-6
     */
    @Test
    public void searchFromToBorder() {
        when(heroDao.getAll()).thenReturn(searchHeroList);
        List<Hero> searchHeroes = heroService.search(null,null,5L,6L);
        verify(heroDao).getAll();
        assertEquals(2,searchHeroes.size());
        assertTrue(searchHeroes.contains(hero4));
        assertTrue(searchHeroes.contains(hero6));
    }

    /**
     * We have heroes with exp 0,5,6,10,20, testing outer borders 1-9
     */
    @Test
    public void searchFromToOuterBorder() {
        when(heroDao.getAll()).thenReturn(searchHeroList);
        List<Hero> searchHeroes = heroService.search(null,null,1L,9L);
        verify(heroDao).getAll();
        assertEquals(2,searchHeroes.size());
        assertTrue(searchHeroes.contains(hero4));
        assertTrue(searchHeroes.contains(hero6));
    }

    @Test
    public void searchRaceNotAssigned() {
        when(heroDao.getAll()).thenReturn(searchHeroList);
        List<Hero> searchHeroes = heroService.search(null,Race.ORC,null,null);
        verify(heroDao).getAll();
        assertEquals(0,searchHeroes.size());
    }

    @Test
    public void searchRoleNotAssigned() {
        when(heroDao.getAll()).thenReturn(searchHeroList);
        List<Hero> searchHeroes = heroService.search(roleNotAssigned,null,null,null);
        verify(heroDao).getAll();
        assertEquals(0,searchHeroes.size());
    }

    /**
     * Here continues test for any combination of parameters of method search to be null
     * So all parameters, one, two, three and any combination of them
     * Here are not tested the edge cases
     */

    @Test
    public void searchAllNull() {
        when(heroDao.getAll()).thenReturn(searchHeroList);
        List<Hero> searchHeroes = heroService.search(null,null,null,null);
        verify(heroDao).getAll();
        assertEquals(5,searchHeroes.size());
    }

    @Test
    public void searchRoleNull() {
        when(heroDao.getAll()).thenReturn(searchHeroList);
        List<Hero> searchHeroes = heroService.search(null,Race.ELF,0L,20L);
        verify(heroDao).getAll();
        assertEquals(2,searchHeroes.size());
        assertTrue(searchHeroes.contains(hero4));
        assertTrue(searchHeroes.contains(hero6));

    }

    @Test
    public void searchRaceNull() {
        when(heroDao.getAll()).thenReturn(searchHeroList);
        List<Hero> searchHeroes = heroService.search(role,null,0L,20L);
        verify(heroDao).getAll();
        assertEquals(1, searchHeroes.size());
        assertTrue(searchHeroes.contains(hero4));
    }

    @Test
    public void searchFromNull() {
        when(heroDao.getAll()).thenReturn(searchHeroList);
        List<Hero> searchHeroes = heroService.search(role,Race.ELF,null,20L);
        verify(heroDao).getAll();
        assertEquals(1,searchHeroes.size());
        assertTrue(searchHeroes.contains(hero4));
    }

    @Test
    public void searchToNull() {
        when(heroDao.getAll()).thenReturn(searchHeroList);
        List<Hero> searchHeroes = heroService.search(role,Race.ELF,0L,null);
        verify(heroDao).getAll();
        assertEquals(1, searchHeroes.size());
        assertTrue(searchHeroes.contains(hero4));
    }

    @Test
    public void searchRoleRaceNull() {
        when(heroDao.getAll()).thenReturn(searchHeroList);
        List<Hero> searchHeroes = heroService.search(null,null,0L,20L);
        verify(heroDao).getAll();
        assertEquals(5, searchHeroes.size());
    }

    @Test
    public void searchRoleFromNull() {
        when(heroDao.getAll()).thenReturn(searchHeroList);
        List<Hero> searchHeroes = heroService.search(null,Race.ELF,null,20L);
        verify(heroDao).getAll();
        assertEquals(2,searchHeroes.size());
        assertTrue(searchHeroes.contains(hero4));
        assertTrue(searchHeroes.contains(hero6));
    }

    @Test
    public void searchRoleToNull() {
        when(heroDao.getAll()).thenReturn(searchHeroList);
        List<Hero> searchHeroes = heroService.search(null,Race.ELF,0L,null);
        verify(heroDao).getAll();
        assertEquals(2, searchHeroes.size());
        assertTrue(searchHeroes.contains(hero4));
        assertTrue(searchHeroes.contains(hero6));
    }

    @Test
    public void searchRaceFromNull() {
        when(heroDao.getAll()).thenReturn(searchHeroList);
        List<Hero> searchHeroes = heroService.search(role,null,null,20L);
        verify(heroDao).getAll();
        assertEquals(1, searchHeroes.size());
        assertTrue(searchHeroes.contains(hero4));
    }

    @Test
    public void searchRaceToNull() {
        when(heroDao.getAll()).thenReturn(searchHeroList);
        List<Hero> searchHeroes = heroService.search(role,null,0L,null);
        verify(heroDao).getAll();
        assertEquals(1, searchHeroes.size());
        assertTrue(searchHeroes.contains(hero4));
    }

    @Test
    public void searchFromToNull() {
        when(heroDao.getAll()).thenReturn(searchHeroList);
        List<Hero> searchHeroes = heroService.search(role,Race.ELF,null,null);
        verify(heroDao).getAll();
        assertEquals(1,searchHeroes.size());
        assertTrue(searchHeroes.contains(hero4));
    }

    @Test
    public void searchRoleRaceToNull() {
        when(heroDao.getAll()).thenReturn(searchHeroList);
        List<Hero> searchHeroes = heroService.search(null, null, 0L, null);
        verify(heroDao).getAll();
        assertEquals(5,searchHeroes.size());
    }

    @Test
    public void searchRoleRaceFromNull() {
        when(heroDao.getAll()).thenReturn(searchHeroList);
        List<Hero> searchHeroes = heroService.search(null,null,null,20L);
        verify(heroDao).getAll();
        assertEquals(5,searchHeroes.size());
    }

    @Test
    public void searchRaceFromToNull() {
        when(heroDao.getAll()).thenReturn(searchHeroList);
        List<Hero> searchHeroes = heroService.search(role,null,null,null);
        verify(heroDao).getAll();
        assertEquals(1, searchHeroes.size());
        assertTrue(searchHeroes.contains(hero4));
    }


}
