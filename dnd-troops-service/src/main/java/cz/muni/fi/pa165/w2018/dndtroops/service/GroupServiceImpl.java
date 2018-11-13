package cz.muni.fi.pa165.w2018.dndtroops.service;

import cz.muni.fi.pa165.w2018.dndtroops.backend.dao.GroupDao;
import cz.muni.fi.pa165.w2018.dndtroops.backend.dao.HeroDao;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Group;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Hero;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Role;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of the Group Service Interface
 *
 * @author Dusan Hetlerovic
 */
@Service
public class GroupServiceImpl implements GroupService {

    @Inject
    private GroupDao groupDao;

    @Inject
    private HeroDao heroDao;

    @Override
    public Group getById(long id) {
        return groupDao.getById(id);
    }

    @Override
    public Group getByName(String name) {
        List<Group> groups = groupDao.getAll()
                .stream()
                .filter(group -> group.getName().equals(name))
                .collect(Collectors.toList());
        return (groups.isEmpty() ? null : groups.get(0));
    }

    @Override
    public List<Group> getGroupsWithoutRoles(List<Long> roles) {
        return groupDao.getAll()
                .stream()
                .filter(group -> {
            for (Hero h : group.getHeroes()) {
                for (Role r : h.getRoles()) {
                    if (roles != null && roles.contains(r.getId())) {
                        return false;
                    }
                }
            }
            return true;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Group> getAll() {
        return groupDao.getAll();
    }

    @Override
    public void create(Group group) {
        groupDao.create(group);
    }

    @Override
    public void update(Group group) {
        groupDao.create(group);
    }

    @Override
    public void delete(long id) {
        Group group = groupDao.getById(id);
        if (group != null) {
            groupDao.remove(group);
        }
    }

    @Override
    public void addHero(long groupId, long heroId) {
        Hero hero = heroDao.getById(heroId);
        Group group = groupDao.getById(groupId);
        if (hero != null && group != null) {
            group.addHero(hero);
            groupDao.update(group);
        }
    }

    @Override
    public void removeHero(long groupId, long heroId) {
        Hero hero = heroDao.getById(heroId);
        Group group = groupDao.getById(groupId);
        if (group != null) {
            group.removeHero(hero);
            groupDao.update(group);
        }
    }

    @Override
    public List<Hero> getHeroesFromGroup(long id) {
        return new ArrayList<>(groupDao.getById(id).getHeroes());
    }

}
