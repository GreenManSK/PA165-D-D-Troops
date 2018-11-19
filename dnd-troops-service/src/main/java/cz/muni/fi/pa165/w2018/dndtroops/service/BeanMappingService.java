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

    /**
     * Maps the collection of source type to a collection of the target type.
     * Maps {@code null} to {@code null}.
     *
     * @param source     Collection of source type data.
     * @param mapToClass target type
     */
    <T> List<T> mapTo(Collection<?> source, Class<T> mapToClass);

    /**
     * Maps the source object to the target type.
     * Maps {@code null} to {@code null}.
     */
    <T> T mapTo(Object source, Class<T> mapToClass);

    /**
     * Maps between race enums
     */
    Race mapTo(cz.muni.fi.pa165.w2018.dndtroops.backend.enums.Race race, Class<Race> mapToClass);

    /**
     * Maps between race enums
     */
    cz.muni.fi.pa165.w2018.dndtroops.backend.enums.Race mapTo(Race race, Class<cz.muni.fi.pa165.w2018.dndtroops.backend.enums.Race> mapToClass);

    /**
     * Returns underlying mapper.
     */
    Mapper getMapper();
}
