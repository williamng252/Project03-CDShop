package cdshop.business;

public class LineItem {
    private final Product product;
    private int quantity;

    public LineItem(Product product, int quantity) {
        this.product = product;
        setQuantity(quantity);
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity must be zero or greater.");
        }
        this.quantity = quantity;
    }

    public double getAmount() {
        return product.getPrice() * quantity;
    }
}
