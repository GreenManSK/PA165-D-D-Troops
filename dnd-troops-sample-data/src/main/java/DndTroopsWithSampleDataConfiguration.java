import cz.muni.fi.pa165.w2018.dndtroops.service.configuration.ServiceConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;

/**
 * Class DndTroopsWithSampleDataConfiguration
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@Configuration
@Import(ServiceConfiguration.class)
//@ComponentScan(basePackageClasses = {SampleDataLoadingFacadeImpl.class}) TODO: uncomment when implemented
public class DndTroopsWithSampleDataConfiguration {
    final static Logger log = LoggerFactory.getLogger(DndTroopsWithSampleDataConfiguration.class);

    //TODO: add autowired SampleDataLoadingFacade

    @PostConstruct
    public void dataLoading() {
        log.debug("sample data loading");
        //TODO: load data
        log.debug("sample data loaded");
    }
}
