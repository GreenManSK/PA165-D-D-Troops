package cz.muni.fi.pa165.w2018.dndtroops.service.configuration;

import cz.muni.fi.pa165.w2018.dndtroops.api.dto.*;
import cz.muni.fi.pa165.w2018.dndtroops.backend.DndTroopsApplicationContext;
import cz.muni.fi.pa165.w2018.dndtroops.backend.entity.*;
import cz.muni.fi.pa165.w2018.dndtroops.service.HeroServiceImpl;
import cz.muni.fi.pa165.w2018.dndtroops.service.facade.HeroFacadeImpl;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Spring configuration for service layer
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@Configuration
@Import(DndTroopsApplicationContext.class)
@ComponentScan(basePackageClasses = {HeroServiceImpl.class, HeroFacadeImpl.class})
public class ServiceConfiguration {
    @Bean
    public Mapper dozer() {
        DozerBeanMapper dozer = new DozerBeanMapper();
        dozer.addMapping(new DozerCustomConfig());
        return dozer;
    }

    public class DozerCustomConfig extends BeanMappingBuilder {
        @Override
        protected void configure() {
            mapping(Hero.class, HeroDTO.class);
            mapping(Role.class, RoleDTO.class);
            mapping(Group.class, GroupDTO.class);
            mapping(Troop.class, TroopDTO.class);
            mapping(User.class, UserDTO.class);
        }
    }
}
