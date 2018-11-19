package cz.muni.fi.pa165.w2018.dndtroops.service.facade;

import cz.muni.fi.pa165.w2018.dndtroops.api.dto.TroopChangeDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.dto.TroopDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.facade.TroopFacade;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Troop;
import cz.muni.fi.pa165.w2018.dndtroops.service.BeanMappingService;
import cz.muni.fi.pa165.w2018.dndtroops.service.TroopService;
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
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


/**
 * Tests for TroopFacadeImpl
 *
 * @author Dusan Hetlerovic
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class TroopFacadeImplTest extends AbstractTestNGSpringContextTests {

    @Mock
    private TroopService troopService;

    @Spy
    @Inject
    private BeanMappingService mappingService;

    @InjectMocks
    private TroopFacade troopFacade = new TroopFacadeImpl();

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
        when(troopService.getById(troop1.getId())).thenReturn(troop1);
        TroopDTO troopDto = troopFacade.getById(troop1.getId());
        verify(troopService).getById(troop1.getId());
        assertEquals(troop1, mappingService.mapTo(troopDto, Troop.class));
    }

    @Test
    public void getAll() {
        when(troopService.getAll()).thenReturn(Arrays.asList(troop1, troop3));
        List<TroopDTO> troopDTOs = troopFacade.getAll();
        verify(troopService).getAll();
        assertEquals(2, troopDTOs.size());
        List<Troop> troops = mappingService.mapTo(troopDTOs, Troop.class);
        assertEquals(2, troops.size());
        assertTrue(troops.contains(troop1));
        assertTrue(troops.contains(troop3));
    }

    @Test
    public void getAllByName() {
        when(troopService.getAllByName(troop1.getName())).thenReturn(Arrays.asList(troop1, troop4));
        List<TroopDTO> troopDTOs = troopFacade.getAllByName(troop1.getName());
        verify(troopService).getAllByName(troop1.getName());
        assertEquals(2, troopDTOs.size());
        List<Troop> troops = mappingService.mapTo(troopDTOs, Troop.class);
        assertEquals(2, troops.size());
        assertTrue(troops.contains(troop1));
        assertTrue(troops.contains(troop4));
    }

    @Test
    public void getAllByMission() {
        when(troopService.getAllByMission(troop1.getMission())).thenReturn(Arrays.asList(troop1, troop2));
        List<TroopDTO> troopDTOs = troopFacade.getAllByMission(troop1.getMission());
        verify(troopService).getAllByMission(troop1.getMission());
        assertEquals(2, troopDTOs.size());
        List<Troop> troops = mappingService.mapTo(troopDTOs, Troop.class);
        assertEquals(2, troops.size());
        assertTrue(troops.contains(troop1));
        assertTrue(troops.contains(troop2));
    }

    @Test
    public void create() {
        TroopChangeDTO troopChangeDto = mappingService.mapTo(troop2, TroopChangeDTO.class);
        troopFacade.create(troopChangeDto);
        verify(troopService).create(any(Troop.class));
    }

    @Test
    public void update() {
        TroopChangeDTO troopChangeDto = mappingService.mapTo(troop2, TroopChangeDTO.class);
        troopChangeDto.setName("Shrek 2");
        troopFacade.update(troopChangeDto);
        verify(troopService).update(any(Troop.class));
    }

    @Test
    public void delete () {
        troopFacade.delete(troop1.getId());
        verify(troopService).delete(troop1.getId());
    }


    /**
     * Helper method creating troop instances
     */
    private void setUpTroops() {
        troop1 = new Troop();
        troop1.setId(1L);
        troop1.setName("Killer ladybug");
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
        troop4.setName("Killer ladybug");
        troop4.setMission("Ask about weather");
    }

}
