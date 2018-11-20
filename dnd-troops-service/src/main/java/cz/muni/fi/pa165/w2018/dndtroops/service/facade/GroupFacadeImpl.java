package cz.muni.fi.pa165.w2018.dndtroops.service.facade;

import cz.muni.fi.pa165.w2018.dndtroops.api.dto.GroupChangeDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.dto.GroupDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.dto.HeroDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.facade.GroupFacade;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Group;
import cz.muni.fi.pa165.w2018.dndtroops.service.BeanMappingService;
import cz.muni.fi.pa165.w2018.dndtroops.service.GroupService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

/**
 * GroupFacade implementation
 *
 * @author Dusan Hetlerovic
 */
@Service
@Transactional
public class GroupFacadeImpl implements GroupFacade {

    @Inject
    private GroupService groupService;

    @Inject
    private BeanMappingService mapper;


    @Override
    public GroupDTO getById(long id) {
        return mapper.mapTo(groupService.getById(id), GroupDTO.class);
    }

    @Override
    public GroupDTO getByName(String name) {
        return mapper.mapTo(groupService.getByName(name), GroupDTO.class);
    }

    @Override
    public List<GroupDTO> getGroupsWithoutRoles(List<Long> roles) {
        return mapper.mapTo(groupService.getGroupsWithoutRoles(roles), GroupDTO.class);
    }

    @Override
    public List<GroupDTO> getAll() {
        return mapper.mapTo(groupService.getAll(), GroupDTO.class);
    }

    @Override
    public List<HeroDTO> getHeroesFromGroup(long id) {
        return mapper.mapTo(groupService.getHeroesFromGroup(id), HeroDTO.class);
    }

    @Override
    public long create(GroupChangeDTO group) {
        Group g = mapper.mapTo(group, Group.class);
        groupService.create(g);
        return g.getId();
    }

    @Override
    public void update(GroupChangeDTO group) {
        Group g = mapper.mapTo(group, Group.class);
        groupService.update(g);
    }

    @Override
    public void delete(long id) {
        groupService.delete(id);
    }

    @Override
    public void addHero(long groupId, long heroId) {
        groupService.addHero(groupId, heroId);
    }

    @Override
    public void removeHero(long groupId, long heroId) {
        groupService.removeHero(groupId, heroId);
    }

}
