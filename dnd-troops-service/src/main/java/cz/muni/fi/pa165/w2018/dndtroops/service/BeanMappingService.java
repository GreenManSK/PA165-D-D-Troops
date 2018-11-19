package cz.muni.fi.pa165.w2018.dndtroops.service;

import cz.muni.fi.pa165.w2018.dndtroops.api.enums.Race;
import org.dozer.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * Class BeanMappingService
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public interface BeanMappingService {

    <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass);

    <T> T mapTo(Object u, Class<T> mapToClass);

    /**
     * Maps between race enums
     */
    Race mapTo(cz.muni.fi.pa165.w2018.dndtroops.backend.enums.Race race, Class<Race> mapToClass);

    /**
     * Maps between race enums
     */
    cz.muni.fi.pa165.w2018.dndtroops.backend.enums.Race mapTo(Race race, Class<cz.muni.fi.pa165.w2018.dndtroops.backend.enums.Race> mapToClass);


    Mapper getMapper();
}
