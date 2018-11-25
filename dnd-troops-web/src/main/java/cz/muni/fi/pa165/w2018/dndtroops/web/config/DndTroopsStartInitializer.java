package cz.muni.fi.pa165.w2018.dndtroops.web.config;

import cz.muni.fi.pa165.w2018.dndtroops.web.config.security.DndTroopsSecurityConfig;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * Class DndTroopsStartInitializer
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public class DndTroopsStartInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{DndTroopsMvcConfig.class, DndTroopsSecurityConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("utf-8");
        return new Filter[]{encodingFilter};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }
}
