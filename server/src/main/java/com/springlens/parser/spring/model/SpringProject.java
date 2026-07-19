package com.springlens.parser.spring.model;

import java.util.List;

public record SpringProject(

        List<SpringController> controllers,

        List<SpringService> services,

        List<SpringRepository> repositories,

        List<SpringComponent> components,

        List<SpringConfiguration> configurations,

        List<SpringBean> beans,

        List<AutowiredDependency> autowiredDependencies,

        List<RequestMapping> requestMappings,

        List<ConfigurationProperty> configurationProperties,

        List<SpringEntity> entities,

        List<SpringTransactional> transactionals,

        List<SpringScheduled> scheduledTasks,

        List<SpringAsync> asyncMethods,

        List<SpringEventListener> eventListeners,

        List<SpringCacheable> cacheables,

        List<SpringSecurityComponent> securityComponents,

        List<SpringProfile> profiles,

        List<SpringQualifier> qualifiers,

        List<SpringValue> values

) {
}