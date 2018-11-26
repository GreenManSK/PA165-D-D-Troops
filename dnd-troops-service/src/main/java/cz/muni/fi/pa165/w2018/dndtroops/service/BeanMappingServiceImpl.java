package cz.muni.fi.pa165.w2018.dndtroops.service;

import cz.muni.fi.pa165.w2018.dndtroops.api.enums.Race;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * BeanMappingService Implementation
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@Service
public class BeanMappingServiceImpl implements BeanMappingService {

    @Inject
    private Mapper dozer;

    @Override
    public <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass) {
        return objects.stream().map(o -> mapTo(o, mapToClass)).collect(Collectors.toList());
    }

    @Override
    public <T> T mapTo(Object u, Class<T> mapToClass) {
        if (u == null)
            return null;
        return dozer.map(u, mapToClass);
    }

    @Override
    public Race mapTo(cz.muni.fi.pa165.w2018.dndtroops.backend.enums.Race race, Class<Race> mapToClass) {
        if (race == null)
            return null;
        return Race.valueOf(race.toString());
    }

    @Override
    public cz.muni.fi.pa165.w2018.dndtroops.backend.enums.Race mapTo(Race race, Class<cz.muni.fi.pa165.w2018.dndtroops.backend.enums.Race> mapToClass) {
        if (race == null)
            return null;
        return cz.muni.fi.pa165.w2018.dndtroops.backend.enums.Race.valueOf(race.toString());
    }

    @Override
    public Mapper getMapper() {
        return dozer;
    }
}
