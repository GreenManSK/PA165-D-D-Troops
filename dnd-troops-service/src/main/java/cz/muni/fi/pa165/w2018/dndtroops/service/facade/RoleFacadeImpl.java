package cz.muni.fi.pa165.w2018.dndtroops.service.facade;

import cz.muni.fi.pa165.w2018.dndtroops.api.dto.RoleChangeDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.dto.RoleDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.facade.RoleFacade;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.Role;
import cz.muni.fi.pa165.w2018.dndtroops.service.BeanMappingService;
import cz.muni.fi.pa165.w2018.dndtroops.service.HeroService;
import cz.muni.fi.pa165.w2018.dndtroops.service.RoleService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RoleFacadeImpl implements RoleFacade {

    @Inject
    private RoleService roleService;

    @Inject
    private BeanMappingService mapper;

//    private Role roleChangeDTOToRole(RoleChangeDTO roleChangeDTO) {
//        Role role = mapper.mapTo(roleChangeDTO, Role.class);
//        role.
//    }

    @Override
    public RoleDTO getById(long id) {return mapper.mapTo(roleService.getById(id), RoleDTO.class);}

    @Override
    public List<RoleDTO> getAll() {return mapper.mapTo(roleService.getAll(), RoleDTO.class);}

    @Override
    public long create(RoleChangeDTO roleChangeDTO) {
        Role role = mapper.mapTo(roleChangeDTO, Role.class);
        roleService.create(role);
        return role.getId();
    }

    @Override
    public void update(RoleChangeDTO roleChangeDTO) {
        Role role = mapper.mapTo(roleChangeDTO, Role.class);
        roleService.update(role);
    }

    @Override
    public void delete(long id) {
        roleService.delete(id);
    }

    @Override
    public RoleDTO getByName(String name) {
        return null;
    }


}
