package model;
/**
 * Clase inmutable que representa un item dentro de una venta
 */
public class ItemVenta {
    private final String productoId;
    private final String productoNombre;
    private final int cantidad;
    private final double precioUnitario;

    //contructor
    public ItemVenta(String productoId, String productoNombre, int cantidad, double precioUnitario) {
        this.productoId = productoId;
        this.productoNombre = productoNombre;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    //Getters
    public double getSubtotal() {
        return cantidad * precioUnitario;
    }
    public String getProductoId() { return productoId; }
    public String getProductoNombre() { return productoNombre; }
    public int getCantidad() { return cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
    //sobre escribiemos para imprimir mas legible
    @Override
    public String toString() {
        return String.format("  - %s (ID: %s) x%d @ $%.2f = $%.2f",
                productoNombre, productoId, cantidad, precioUnitario, getSubtotal());
    }

}
