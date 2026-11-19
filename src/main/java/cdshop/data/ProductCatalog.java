package cdshop.data;

import java.util.Map;
import cdshop.business.Product;

public final class ProductCatalog {
    private static final Map<String, Product> PRODUCTS = Map.of(
            "8601", new Product("8601", "86 (the band) - True Life Songs and Pictures", 14.95),
            "pf01", new Product("pf01", "Paddlefoot - The first CD", 12.95),
            "pf02", new Product("pf02", "Paddlefoot - The second CD", 14.95),
            "jr01", new Product("jr01", "Joe Rut - Genuine Wood Grained Finish", 14.95));

    private ProductCatalog() { }

    public static Product findByCode(String productCode) {
        // The catalog key is the same value as Product.id and the form's productCode.
        return productCode == null ? null : PRODUCTS.get(productCode);
    }
}
