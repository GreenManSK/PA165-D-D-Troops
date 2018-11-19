package cz.muni.fi.pa165.w2018.dndtroops.service.facade;

import cz.muni.fi.pa165.w2018.dndtroops.api.dto.HeroChangeDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.dto.HeroDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.dto.HeroSearchDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.facade.HeroFacade;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Hero;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Role;
import cz.muni.fi.pa165.w2018.dndtroops.backend.enums.Race;
import cz.muni.fi.pa165.w2018.dndtroops.service.BeanMappingService;
import cz.muni.fi.pa165.w2018.dndtroops.service.GroupService;
import cz.muni.fi.pa165.w2018.dndtroops.service.HeroService;
import cz.muni.fi.pa165.w2018.dndtroops.service.RoleService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

/**
 * HeroFacade Implementation
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@Service
@Transactional
public class HeroFacadeImpl implements HeroFacade {

    @Inject
    private HeroService heroService;

    @Inject
    private GroupService groupService;

    @Inject
    private RoleService roleService;

    @Inject
    private BeanMappingService mapper;

    /**
     * Retrieve hero by the id
     *
     * @param id of the hero
     * @return hero or {@code null}, if there is no hero with requested id
     */
    @Override
    public HeroDTO getById(long id) {
        return mapper.mapTo(heroService.getById(id), HeroDTO.class);
    }

    /**
     * Return all heroes
     *
     * @return List of HeroDTOs
     */
    @Override
    public List<HeroDTO> getAll() {
        return mapper.mapTo(heroService.getAll(), HeroDTO.class);
    }

    /**
     * Creates new hero
     *
     * @param heroChangeDTO HeroChangeDTO
     * @return ID of new hero
     */
    @Override
    public long create(HeroChangeDTO heroChangeDTO) {
        Hero hero = heroChangeDTOToHero(heroChangeDTO);
        heroService.create(hero);
        return hero.getId();
    }

    /**
     * Updates the hero specified by the ID of the DAO object
     *
     * @param heroChangeDTO HeroChangeDTO
     */
    @Override
    public void update(HeroChangeDTO heroChangeDTO) {
        Hero hero = heroChangeDTOToHero(heroChangeDTO);
        heroService.update(hero);
    }

    /**
     * Map HeroChangeDTO to hero and adds group and roles according to their IDs
     *
     * @param heroChangeDTO HeroChangeDTO
     * @return Mapped Hero entity
     */
    private Hero heroChangeDTOToHero(HeroChangeDTO heroChangeDTO) {
        Hero hero = mapper.mapTo(heroChangeDTO, Hero.class);
        hero.setGroup(heroChangeDTO.getGroupId() != null ? groupService.getById(heroChangeDTO.getGroupId()): null);
        for (Long id : heroChangeDTO.getRoleIds()) {
            Role role = roleService.getById(id);
            if (role != null) {
                hero.addRole(role);
            }
        }
        return hero;
    }

    /**
     * Deletes hero by its ID
     *
     * @param id ID of the hero
     */
    @Override
    public void delete(long id) {
        heroService.delete(id);
    }

    /**
     * Find all heroes according to the query specified by DTO
     *
     * @param searchDTO HeroSearchDTO
     * @return List of heroes satisfying the query
     */
    @Override
    public List<HeroDTO> search(HeroSearchDTO searchDTO) {
        return mapper.mapTo(heroService.search(
                mapper.mapTo(searchDTO.getRole(), Role.class),
                mapper.mapTo(searchDTO.getRace(), Race.class),
                searchDTO.getFromExperience(),
                searchDTO.getToExperience()), HeroDTO.class);
    }
}
