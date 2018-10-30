package cz.muni.fi.pa165.w2018.dndtroops.service;

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

    Mapper getMapper();
}
