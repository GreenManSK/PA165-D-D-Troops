package cz.muni.fi.pa165.w2018.dndtroops.service.facade;

import cz.muni.fi.pa165.w2018.dndtroops.api.dto.HeroChangeDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.dto.HeroDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.dto.HeroSearchDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.dto.RoleDTO;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Role;
import cz.muni.fi.pa165.w2018.dndtroops.backend.enums.Race;
import cz.muni.fi.pa165.w2018.dndtroops.api.facade.HeroFacade;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Group;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Hero;
import cz.muni.fi.pa165.w2018.dndtroops.service.BeanMappingService;
import cz.muni.fi.pa165.w2018.dndtroops.service.HeroService;
import cz.muni.fi.pa165.w2018.dndtroops.service.configuration.ServiceConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

/**
 * Tests fro HeroFacade implementation
 *
 * @author Marek Valko
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class HeroFacadeImplTest extends AbstractTestNGSpringContextTests {
    @Mock
    private HeroService heroService;

    @Spy
    @Inject
    private BeanMappingService beanMappingService;

    @InjectMocks
    private HeroFacade heroFacade = new HeroFacadeImpl();

    private Hero hero1;
    private Hero hero2;
    private Hero hero3;
    private Hero hero4;
    private Role role;
    private Group group;

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
        hero3.setId(3L);
        hero3.setName("Cyril");
        hero3.setExperience(30L);

        hero4 = new Hero();
        hero4.setId(4L);
        hero4.setName("David");
        hero4.setRace(Race.ELF);
        hero4.setExperience(15L);
        hero4.addRole(role);
    }

    @Test
    public void getById() {
        when(heroService.getById(hero1.getId())).thenReturn(hero1);
        HeroDTO heroDTO = heroFacade.getById(hero1.getId());
        verify(heroService).getById(hero1.getId());
        assertEquals(hero1, beanMappingService.mapTo(heroDTO, Hero.class));
    }

    @Test
    public void getAll() {
        when(heroService.getAll()).thenReturn(Arrays.asList(hero1,hero2));
        List<HeroDTO> heroDTOs = heroFacade.getAll();
        verify(heroService).getAll();
        List<Hero> heroes = beanMappingService.mapTo(heroDTOs, Hero.class);
        assertEquals(2,heroes.size());
        assertTrue(heroes.contains(hero1));
        assertTrue(heroes.contains(hero2));
    }

    @Test
    public void create() {
        HeroChangeDTO heroChangeDTO = beanMappingService.mapTo(hero3, HeroChangeDTO.class);
        heroChangeDTO.setRoleIds(new ArrayList<>());
        heroFacade.create(heroChangeDTO);
        verify(heroService).create(any(Hero.class));
    }

    @Test
    public void update() {
        HeroChangeDTO heroChangeDTO = beanMappingService.mapTo(hero3, HeroChangeDTO.class);
        heroChangeDTO.setRoleIds(new ArrayList<>());
        heroChangeDTO.setName("Cecilia");
        heroFacade.update(heroChangeDTO);
        verify(heroService).update(any(Hero.class));
    }

    @Test
    public void delete() {
        heroFacade.delete(hero1.getId());
        verify(heroService).delete(hero1.getId());
    }

    @Test
    public void search() {
        HeroSearchDTO heroSearchDTO = new HeroSearchDTO();
        heroSearchDTO.setRole(beanMappingService.mapTo(role, RoleDTO.class));
        heroSearchDTO.setRace(cz.muni.fi.pa165.w2018.dndtroops.api.enums.Race.ELF);
        heroSearchDTO.setFromExperience(0L);
        heroSearchDTO.setToExperience(20L);
        when(heroService.search(role, Race.ELF, 0L, 20L)).thenReturn(Arrays.asList(hero4));
        List<HeroDTO> heroDTOs = heroFacade.search(heroSearchDTO);
        verify(heroService).search(role,Race.ELF, 0L, 20L);
        assertEquals(1,heroDTOs.size());
        assertTrue(heroDTOs.contains(beanMappingService.mapTo(hero4,HeroDTO.class)));
    }
}
