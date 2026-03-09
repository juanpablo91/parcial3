package funtional;

import model.Producto;

import java.util.List;
import java.util.function.*;
/**
 * Interfaces funcionales personalizadas para operaciones del e-commerce, mucho mas ordenado y escalable sobre el tiempo
 */
public class InterfacesFuncionales  {
    /**
     * Interfaz para procesar recursivamente una lista
     */
    @FunctionalInterface
    public interface ProcesadorRecursivo<T, R> {
        R procesar(List<T> lista, int indice, R acumulador);
    }

    /**
     * Interfaz para validación de stock
     */
    @FunctionalInterface
    public interface ValidadorStock {
        boolean validar(Producto producto, int cantidadRequerida);
    }

    /**
     * Interfaz para búsqueda de productos
     */
    @FunctionalInterface
    public interface ActualizarInventario{
        List<Producto> actualizar(List<Producto> inventario, String productoId, int cantidad);
    }

    /**
     * Interfaz para actualización de inventario
     */

    public interface CalculadorEstadistica<T> {
        double calcular(List<T> datos);
    }

    /**
     * Interfaz para filtrado recursivo
     */
    @FunctionalInterface
    public interface FiltradorRecursivo<T> {
        List<T> filtrar(List<T> lista, Predicate<T> criterio, int indice, List<T> resultado);
    }

    /**
     * Interfaz para mapeo recursivo
     */
    @FunctionalInterface
    public interface MapeadorRecursivo<T, R> {
        List<R> mapear(List<T> lista, Function<T, R> funcion, int indice, List<R> resultado);
    }


}
