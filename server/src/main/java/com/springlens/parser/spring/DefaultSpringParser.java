package com.springlens.parser.spring;

import com.springlens.parser.java.model.JavaProject;
import com.springlens.parser.spring.extractor.AsyncExtractor;
import com.springlens.parser.spring.extractor.AutowiredExtractor;
import com.springlens.parser.spring.extractor.BeanExtractor;
import com.springlens.parser.spring.extractor.CacheableExtractor;
import com.springlens.parser.spring.extractor.ComponentExtractor;
import com.springlens.parser.spring.extractor.ConfigurationExtractor;
import com.springlens.parser.spring.extractor.ConfigurationPropertyExtractor;
import com.springlens.parser.spring.extractor.ControllerExtractor;
import com.springlens.parser.spring.extractor.EntityExtractor;
import com.springlens.parser.spring.extractor.EventListenerExtractor;
import com.springlens.parser.spring.extractor.ProfileExtractor;
import com.springlens.parser.spring.extractor.QualifierExtractor;
import com.springlens.parser.spring.extractor.RepositoryExtractor;
import com.springlens.parser.spring.extractor.RequestMappingExtractor;
import com.springlens.parser.spring.extractor.ScheduledExtractor;
import com.springlens.parser.spring.extractor.SecurityExtractor;
import com.springlens.parser.spring.extractor.ServiceExtractor;
import com.springlens.parser.spring.extractor.TransactionalExtractor;
import com.springlens.parser.spring.extractor.ValueExtractor;
import com.springlens.parser.spring.model.SpringProject;
import org.springframework.stereotype.Component;

@Component
public class DefaultSpringParser implements SpringParser {

    private final ControllerExtractor controllerExtractor;
    private final ServiceExtractor serviceExtractor;
    private final RepositoryExtractor repositoryExtractor;
    private final ComponentExtractor componentExtractor;
    private final ConfigurationExtractor configurationExtractor;
    private final BeanExtractor beanExtractor;
    private final AutowiredExtractor autowiredExtractor;
    private final RequestMappingExtractor requestMappingExtractor;
    private final ConfigurationPropertyExtractor configurationPropertyExtractor;

    private final EntityExtractor entityExtractor;
    private final TransactionalExtractor transactionalExtractor;
    private final ScheduledExtractor scheduledExtractor;
    private final AsyncExtractor asyncExtractor;
    private final EventListenerExtractor eventListenerExtractor;
    private final CacheableExtractor cacheableExtractor;
    private final SecurityExtractor securityExtractor;
    private final ProfileExtractor profileExtractor;
    private final QualifierExtractor qualifierExtractor;
    private final ValueExtractor valueExtractor;

    public DefaultSpringParser(

            ControllerExtractor controllerExtractor,
            ServiceExtractor serviceExtractor,
            RepositoryExtractor repositoryExtractor,
            ComponentExtractor componentExtractor,
            ConfigurationExtractor configurationExtractor,
            BeanExtractor beanExtractor,
            AutowiredExtractor autowiredExtractor,
            RequestMappingExtractor requestMappingExtractor,
            ConfigurationPropertyExtractor configurationPropertyExtractor,
            EntityExtractor entityExtractor,
            TransactionalExtractor transactionalExtractor,
            ScheduledExtractor scheduledExtractor,
            AsyncExtractor asyncExtractor,
            EventListenerExtractor eventListenerExtractor,
            CacheableExtractor cacheableExtractor,
            SecurityExtractor securityExtractor,
            ProfileExtractor profileExtractor,
            QualifierExtractor qualifierExtractor,
            ValueExtractor valueExtractor

    ) {

        this.controllerExtractor = controllerExtractor;
        this.serviceExtractor = serviceExtractor;
        this.repositoryExtractor = repositoryExtractor;
        this.componentExtractor = componentExtractor;
        this.configurationExtractor = configurationExtractor;
        this.beanExtractor = beanExtractor;
        this.autowiredExtractor = autowiredExtractor;
        this.requestMappingExtractor = requestMappingExtractor;
        this.configurationPropertyExtractor = configurationPropertyExtractor;
        this.entityExtractor = entityExtractor;
        this.transactionalExtractor = transactionalExtractor;
        this.scheduledExtractor = scheduledExtractor;
        this.asyncExtractor = asyncExtractor;
        this.eventListenerExtractor = eventListenerExtractor;
        this.cacheableExtractor = cacheableExtractor;
        this.securityExtractor = securityExtractor;
        this.profileExtractor = profileExtractor;
        this.qualifierExtractor = qualifierExtractor;
        this.valueExtractor = valueExtractor;

    }

    @Override
public SpringProject parse(JavaProject project) {

    return new SpringProject(

            controllerExtractor.extract(project),

            serviceExtractor.extract(project),

            repositoryExtractor.extract(project),

            componentExtractor.extract(project),

            configurationExtractor.extract(project),

            beanExtractor.extract(project),

            autowiredExtractor.extract(project),

            requestMappingExtractor.extract(project),

            configurationPropertyExtractor.extract(project),

            entityExtractor.extract(project),

            transactionalExtractor.extract(project),

            scheduledExtractor.extract(project),

            asyncExtractor.extract(project),

            eventListenerExtractor.extract(project),

            cacheableExtractor.extract(project),

            securityExtractor.extract(project),

            profileExtractor.extract(project),

            qualifierExtractor.extract(project),

            valueExtractor.extract(project)

    );

}
}