package cz.muni.fi.pa165.w2018.dndtroops.service;


import cz.muni.fi.pa165.w2018.dndtroops.backend.dao.RoleDao;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Role;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Implementation of Role Service
 *
 * @author Marek Valko
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Inject
    private RoleDao roleDao;

    @Override
    public Role getById(long id) {
        return roleDao.getById(id);
    }

    @Override
    public List<Role> getAll() { return roleDao.getAll(); }

    @Override
    public void create(Role role) { roleDao.create(role);}

    @Override
    public void update(Role role) { roleDao.create(role);}

    @Override
    public void delete(long id) {
        Role role = roleDao.getById(id);
        if (role != null) {
            roleDao.delete(role);
        }
    }
}
