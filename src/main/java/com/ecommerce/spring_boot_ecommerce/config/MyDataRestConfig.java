package com.ecommerce.spring_boot_ecommerce.config;

import com.ecommerce.spring_boot_ecommerce.entity.Country;
import com.ecommerce.spring_boot_ecommerce.entity.Product;
import com.ecommerce.spring_boot_ecommerce.entity.ProductCategory;
import com.ecommerce.spring_boot_ecommerce.entity.State;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    private final EntityManager entityManager;

    @Autowired
    public MyDataRestConfig(EntityManager theEntityManager) {
        this.entityManager = theEntityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

        HttpMethod[] theUnsupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE};

        // disable HTTP methods for Product: PUT, POST and DELETE
        // disable HTTP methods for ProductCategory: PUT, POST and DELETE
        disableHttpMethods(Product.class,config,theUnsupportedActions );
        disableHttpMethods(ProductCategory.class,config,theUnsupportedActions );
        disableHttpMethods(Country.class,config,theUnsupportedActions );
        disableHttpMethods(State.class,config,theUnsupportedActions );

        // Call an internal helper method to expose IDs
        exposeIds(config);
    }

    private static void disableHttpMethods(Class theClass,RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
    }

    private void exposeIds(RepositoryRestConfiguration config) {
        // Expose entity IDs
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        List<Class<?>> entityClasses = new ArrayList<>();

        for (EntityType<?> tempEntityType : entities) {
            entityClasses.add(tempEntityType.getJavaType());
        }

        Class<?>[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }
}
