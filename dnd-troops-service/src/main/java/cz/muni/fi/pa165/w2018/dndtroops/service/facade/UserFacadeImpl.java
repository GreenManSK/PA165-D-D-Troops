package cz.muni.fi.pa165.w2018.dndtroops.service.facade;

import cz.muni.fi.pa165.w2018.dndtroops.api.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.dto.UserDTO;
import cz.muni.fi.pa165.w2018.dndtroops.api.facade.UserFacade;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.User;
import cz.muni.fi.pa165.w2018.dndtroops.service.BeanMappingService;
import cz.muni.fi.pa165.w2018.dndtroops.service.UserService;

import javax.inject.Inject;
import java.util.List;

public class UserFacadeImpl implements UserFacade{
    @Inject
    private UserService userService;

    @Inject
    private BeanMappingService mapper;

    @Override
    public UserDTO getById(long id) {
        return mapper.mapTo(userService.getById(id), UserDTO.class);
    }

    @Override
    public UserDTO getByLogin(String login) {
        return mapper.mapTo(userService.getByLogin(login), UserDTO.class);
    }

    @Override
    public List<UserDTO> getAll() {
        return mapper.mapTo(userService.getAll(), UserDTO.class);
    }

    @Override
    public long createUser(UserDTO user, String plainPassword) {
        User usr = mapper.mapTo(user, User.class);
        userService.createUser(usr, plainPassword);
        return usr.getId();
    }

    @Override
    public void update(UserDTO user) {
        User usr = mapper.mapTo(user, User.class);
        userService.update(usr);
    }

    @Override
    public void delete(long id) {
        userService.delete(id);
    }

    @Override
    public boolean isUser(long id) {
        User user = userService.getById(id);
        return userService.isUser(user);
    }

    @Override
    public boolean isAdmin(long id) {
        User user = userService.getById(id);
        return userService.isAdmin(user);
    }

    @Override
    public boolean authenticateUser(UserAuthenticateDTO user, String plainPassword) {
        if (user == null) {
            throw new NullPointerException("User cannot be null");
        }
        User u = userService.getByLogin(user.getLogin());
        if (u == null) {
            throw new IllegalArgumentException("User with given login doesnt exist");
        }
        return userService.authenticate(u, plainPassword);
    }
}
