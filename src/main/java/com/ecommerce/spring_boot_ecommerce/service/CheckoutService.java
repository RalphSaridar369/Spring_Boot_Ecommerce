package com.ecommerce.spring_boot_ecommerce.service;

import com.ecommerce.spring_boot_ecommerce.dto.Purchase;
import com.ecommerce.spring_boot_ecommerce.dto.PurchaseResponse;

public interface CheckoutService {
    PurchaseResponse placeOrder(Purchase purchase);
}
