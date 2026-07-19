package com.springlens.parser.spring.common;

import java.util.List;

public final class SpringConstants {

    private SpringConstants() {
    }

    public static final String COMPONENT = "Component";
    public static final String SERVICE = "Service";
    public static final String REPOSITORY = "Repository";
    public static final String CONTROLLER = "Controller";
    public static final String REST_CONTROLLER = "RestController";
    public static final String CONFIGURATION = "Configuration";
    public static final String BEAN = "Bean";
    public static final String AUTOWIRED = "Autowired";
    public static final String REQUEST_MAPPING = "RequestMapping";
    public static final String GET_MAPPING = "GetMapping";
    public static final String POST_MAPPING = "PostMapping";
    public static final String PUT_MAPPING = "PutMapping";
    public static final String DELETE_MAPPING = "DeleteMapping";
    public static final String PATCH_MAPPING = "PatchMapping";
    public static final String CONFIGURATION_PROPERTIES = "ConfigurationProperties";
    public static final String ENTITY = "Entity";
    public static final String TRANSACTIONAL = "Transactional";
    public static final String SCHEDULED = "Scheduled";
    public static final String ASYNC = "Async";
    public static final String EVENT_LISTENER = "EventListener";

    public static final String CACHEABLE = "Cacheable";
    public static final String CACHE_PUT = "CachePut";
    public static final String CACHE_EVICT = "CacheEvict";

    public static final String ENABLE_WEB_SECURITY = "EnableWebSecurity";
    public static final String ENABLE_METHOD_SECURITY = "EnableMethodSecurity";
    public static final String PRE_AUTHORIZE = "PreAuthorize";
    public static final String SECURED = "Secured";
    public static final String ROLES_ALLOWED = "RolesAllowed";

    public static final String PROFILE = "Profile";
    public static final String QUALIFIER = "Qualifier";
    public static final String VALUE = "Value";

    public static final String LAZY = "Lazy";
    public static final String PRIMARY = "Primary";

    public static final String CONDITIONAL = "Conditional";
    public static final String CONDITIONAL_ON_BEAN = "ConditionalOnBean";
    public static final String CONDITIONAL_ON_CLASS = "ConditionalOnClass";
    public static final String CONDITIONAL_ON_PROPERTY = "ConditionalOnProperty";
    public static final String CONDITIONAL_ON_MISSING_BEAN = "ConditionalOnMissingBean";

    public static final List<String> CONTROLLER_ANNOTATIONS = List.of(
            CONTROLLER,
            REST_CONTROLLER
    );

    public static final List<String> REQUEST_MAPPINGS = List.of(
            REQUEST_MAPPING,
            GET_MAPPING,
            POST_MAPPING,
            PUT_MAPPING,
            DELETE_MAPPING,
            PATCH_MAPPING
    );

}