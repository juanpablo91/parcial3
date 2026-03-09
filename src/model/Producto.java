package model;
import java.util.Objects;

/**
 * Clase inmutable que representa un producto de ropa
 */

public class Producto {
    private String id;
    private String nombre;
    private double precio;
    private String descripcion;
    private int cantidadStock;

    //constructor
    public Producto( String id, String nombre, double precio, String descripcion, int cantidadStock) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.cantidadStock = cantidadStock;
    }

    //Actualizar un productor
    public Producto actualizar(String nombre, double precio, String descripcion, int cantidadStock) {
        return new Producto(this.id , nombre, precio, descripcion, cantidadStock);
    }

    // Método funcional para actualizar stock (retorna nuevo objeto)
    public Producto actualizarStock(int nuevaCantidad) {
        return new Producto(this.id, this.nombre, this.precio, this.descripcion, nuevaCantidad);
    }

    //Getters
    public String getId(){ return this.id; }
    public String getNombre(){ return this.nombre; }
    public double getPrecio(){ return this.precio; }
    public String getDescripcion(){ return this.descripcion; }
    public int getCantidadStock(){ return this.cantidadStock; }

    //sobre escritura de metodos

    //EL metodo equals y hascode se usan para evitar duplicados
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return Objects.equals(id, producto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    //EL metodo toString, igualmente, para que sea mas ameno cuando se imprima
    @Override
    public String toString(){
        return String.format("Producto{id:'%s, nombre:'%s, precio=%.2f, descripcion='%s', stock=%d}", id, nombre, precio, descripcion, cantidadStock);
    }

}
