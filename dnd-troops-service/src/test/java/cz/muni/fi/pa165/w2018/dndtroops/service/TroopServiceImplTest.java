package cz.muni.fi.pa165.w2018.dndtroops.service;


import cz.muni.fi.pa165.w2018.dndtroops.backend.dao.TroopDao;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Troop;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

/**
 * Tests for TroopServiceImpl
 *
 * @author Dusan Hetlerovic
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class TroopServiceImplTest extends AbstractTestNGSpringContextTests {

    @Mock
    private TroopDao troopDao;

    @Inject
    @InjectMocks
    private TroopService troopService;

    private Troop troop1;
    private Troop troop2;
    private Troop troop3;
    private Troop troop4;


    @BeforeMethod
    public void setUp() {
        setUpTroops();
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void getById() {
        when(troopDao.getById(troop1.getId())).thenReturn(troop1);
        Troop t = troopService.getById(troop1.getId());
        verify(troopDao).getById(troop1.getId());
        assertEquals(t, troop1);
    }

    @Test
    public void getByIdAbsent() {
        long id = 500L;
        when(troopDao.getById(id)).thenReturn(null);
        Troop t = troopService.getById(id);
        verify(troopDao).getById(id);
        assertNull(t);
    }

    @Test
    public void getAll() {
        List<Troop> troops = Arrays.asList(troop2, troop3);
        when(troopDao.getAll()).thenReturn(troops);
        List<Troop> returnedTroops = troopService.getAll();
        verify(troopDao).getAll();
        assertEquals(troops, returnedTroops);
    }

    @Test
    public void getAllNoTroops() {
        when(troopDao.getAll()).thenReturn(Collections.emptyList());
        List<Troop> returnedTroops = troopService.getAll();
        verify(troopDao).getAll();
        assertEquals(returnedTroops.size(), 0);
    }

    @Test
    public void getAllByName() {
        List<Troop> troops = Arrays.asList(troop1, troop4);
        when(troopDao.getAllByName(troop1.getName())).thenReturn(troops);
        List<Troop> returnedTroops = troopService.getAllByName(troop1.getName());
        verify(troopDao).getAllByName(troop1.getName());
        assertEquals(troops, returnedTroops);
    }

    @Test
    public void getAllByNameNoTroops() {
        when(troopDao.getAllByName(troop1.getName())).thenReturn(Collections.emptyList());
        List<Troop> returnedTroops = troopService.getAllByName(troop1.getName());
        verify(troopDao).getAllByName(troop1.getName());
        assertEquals(returnedTroops.size(), 0);
    }

    @Test
    public void getAllByMission() {
        List<Troop> troops = Arrays.asList(troop1, troop2);
        when(troopDao.getAllByMission(troop1.getMission())).thenReturn(troops);
        List<Troop> returnedTroops = troopService.getAllByMission(troop1.getMission());
        verify(troopDao).getAllByMission(troop1.getMission());
        assertEquals(troops, returnedTroops);
    }

    @Test
    public void getAllByMissionNoTroops() {
        when(troopDao.getAllByMission(troop1.getMission())).thenReturn(Collections.emptyList());
        List<Troop> returnedTroops = troopService.getAllByMission(troop1.getMission());
        verify(troopDao).getAllByMission(troop1.getMission());
        assertEquals(returnedTroops.size(), 0);
    }

    @Test
    public void create() {
        doAnswer(invocationOnMock -> {
            Troop troop = (Troop) invocationOnMock.getArguments()[0];
            troop.setId(10L);
            return null;
        }).when(troopDao).create(troop2);
        troopService.create(troop2);
        verify(troopDao).create(troop2);
        assertNotNull(troop2.getId());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void createNull() {
        doThrow(new NullPointerException()).when(troopDao).create(null);
        troopService.create(null);
    }

    @Test(expectedExceptions = DataIntegrityViolationException.class)
    public void createInvalidTroop() {
        troop4.setMission(null);
        doThrow(new DataIntegrityViolationException("")).when(troopDao).create(troop4);
        troopService.create(troop4);
    }

    @Test
    public void update() {
        doNothing().when(troopDao).update(troop2);
        troopService.update(troop2);
        verify(troopDao).update(troop2);
        assertNotNull(troop2.getId());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void updateNull() {
        doThrow(new NullPointerException()).when(troopDao).update(null);
        troopService.update(null);
    }

    @Test(expectedExceptions = DataIntegrityViolationException.class)
    public void updateInvalidTroop() {
        troop3.setGold(null);
        doThrow(new DataIntegrityViolationException("")).when(troopDao).update(troop3);
        troopService.update(troop3);
    }

    @Test
    public void delete() {
        when(troopDao.getById(troop1.getId())).thenReturn(troop1);
        doNothing().when(troopDao).remove(troop1);
        troopService.delete(troop1.getId());
        verify(troopDao).remove(troop1);
    }

    @Test
    public void deleteAbsent() {
        long id = 50L;
        when(troopDao.getById(id)).thenReturn(null);
        troopService.delete(id);
        verify(troopDao, never()).remove(any());
    }



    /**
     * Helper method creating troop instances
     */
    private void setUpTroops() {
        troop1 = new Troop();
        troop1.setId(1L);
        troop1.setName("Killer Ladybug");
        troop1.setMission("Charm");
        troop1.setGold(200L);

        troop2 = new Troop();
        troop2.setId(3L);
        troop2.setName("The flip-flop spider");
        troop2.setMission("Charm");
        troop2.setGold(300L);

        troop3 = new Troop();
        troop3.setId(3L);
        troop3.setName("A really cute cat");
        troop3.setMission("Pet");
        troop3.setGold(1000L);

        troop4 = new Troop();
        troop4.setId(4L);
        troop4.setName("Killer Ladybug");
        troop4.setMission("Ask about weather");
    }

}
