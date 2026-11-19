package cdshop.business;

import java.util.ArrayList;

public class Cart {
    private final ArrayList<LineItem> items = new ArrayList<>();

    public ArrayList<LineItem> getItems() { return items; }

    public LineItem findItem(String productCode) {
        for (LineItem item : items) {
            if (item.getProduct().getId().equals(productCode)) {
                return item;
            }
        }
        return null;
    }

    public void add(Product product) {
        LineItem item = findItem(product.getId());
        if (item == null) {
            items.add(new LineItem(product, 1));
        } else {
            item.setQuantity(Math.addExact(item.getQuantity(), 1));
        }
    }

    public void update(String productCode, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity must be zero or greater.");
        }
        LineItem item = findItem(productCode);
        if (item == null) {
            throw new IllegalArgumentException("This CD is not in your cart.");
        }
        if (quantity == 0) {
            remove(productCode);
        } else {
            item.setQuantity(quantity);
        }
    }

    public void remove(String productCode) {
        items.removeIf(item -> item.getProduct().getId().equals(productCode));
    }

    public double getTotal() {
        double total = 0;
        for (LineItem item : items) {
            total += item.getAmount();
        }
        return total;
    }
}
