package cz.muni.fi.pa165.w2018.dndtroops.sampledata;

import cz.muni.fi.pa165.w2018.dndtroops.service.configuration.ServiceConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * Class DndTroopsWithSampleDataConfiguration
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@Configuration
@Import(ServiceConfiguration.class)
@ComponentScan(basePackageClasses = {SampleDataLoadingFacadeImpl.class})
public class DndTroopsWithSampleDataConfiguration {
    final static Logger log = LoggerFactory.getLogger(DndTroopsWithSampleDataConfiguration.class);

    @Inject
    private SampleDataLoadingFacade loadingFacade;

    @PostConstruct
    public void dataLoading() {
        log.debug("sample data loading");
        loadingFacade.loadData();
        log.debug("sample data loaded");
    }
}
