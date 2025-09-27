package com.billontrax.modules.product.enums;

/**
 * Product type classification controlling inventory field requirements.
 * PHYSICAL: Tangible goods requiring inventory tracking
 * SERVICE: Intangible service offering (no stock)
 * DIGITAL: Digital product / downloadable (no stock)
 */
public enum ProductType {
    PHYSICAL,
    SERVICE,
    DIGITAL
}

