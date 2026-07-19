package com.springlens.parser.spring.extractor;

import com.springlens.parser.java.model.JavaAnnotation;
import com.springlens.parser.java.model.JavaClass;
import com.springlens.parser.java.model.JavaMethod;
import com.springlens.parser.java.model.JavaProject;
import com.springlens.parser.spring.common.SpringConstants;
import com.springlens.parser.spring.model.SpringCacheable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CacheableExtractor {

    private static final List<String> CACHE_ANNOTATIONS = List.of(
            SpringConstants.CACHEABLE,
            SpringConstants.CACHE_PUT,
            SpringConstants.CACHE_EVICT
    );

    public List<SpringCacheable> extract(JavaProject project) {

        List<SpringCacheable> cacheables = new ArrayList<>();

        for (JavaClass javaClass : project.classes()) {

            for (JavaMethod method : javaClass.methods()) {

                for (JavaAnnotation annotation : method.annotations()) {

                    if (CACHE_ANNOTATIONS.contains(annotation.name())) {

                        cacheables.add(
                                new SpringCacheable(
                                        javaClass.name(),
                                        javaClass.packageName(),
                                        method.name(),
                                        annotation.name()
                                )
                        );

                    }

                }

            }

        }

        return List.copyOf(cacheables);

    }

}