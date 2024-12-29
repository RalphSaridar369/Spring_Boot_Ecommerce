package com.ecommerce.spring_boot_ecommerce.dao;

import com.ecommerce.spring_boot_ecommerce.entity.Country;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "inlineCountry", types = { Country.class })
public interface CountryProjection {
    int getId();
    String getCode();
    String getName();
}
