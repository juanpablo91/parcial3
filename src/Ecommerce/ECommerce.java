package Ecommerce;

import model.ItemVenta;
import model.Producto;
import model.Venta;
import funtional.InterfacesFuncionales;

import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Sistema de E-Commerce de Ropa implementado con Programación Funcional
 * Uso extensivo de: lambdas, streams, interfaces funcionales, recursión
 */
public class ECommerce {

    // Estado inmutable - se actualiza creando nuevas colecciones
    private List<Producto> inventario;
    private List<Venta> ventas;

    public ECommerce() {
        this.inventario = new ArrayList<>();
        this.ventas = new ArrayList<>();
    }

    // ==================== FUNCIÓN 1: REGISTRAR VENTAS ====================

    /**
     * Registra una nueva venta y actualiza el inventario
     * Usa validación funcional y actualización inmutable
     */
    public void registrarVenta(String codigo, String id, LocalDate fecha, List<ItemVenta> items) {
        // Validar stock usando programación funcional
        boolean stockDisponible = validarStockRecursivo.apply(items, 0);

        if (!stockDisponible) {
            System.out.println("❌ Error: Stock insuficiente para completar la venta");
            return;
        }

        // Crear venta
        Venta nuevaVenta = new Venta(codigo, id, fecha, items);
        ventas = Stream.concat(ventas.stream(), Stream.of(nuevaVenta))
                .collect(Collectors.toList());

        // Actualizar inventario (descontar productos)
        inventario = actualizarInventarioPorVenta.apply(inventario, items);

        System.out.println("✅ Venta registrada exitosamente");
        System.out.println(nuevaVenta);
    }

    // Función recursiva para validar stock
    private final BiFunction<List<ItemVenta>, Integer, Boolean> validarStockRecursivo =
            new BiFunction<List<ItemVenta>, Integer, Boolean>() {
                @Override
                public Boolean apply(List<ItemVenta> items, Integer indice) {
                    if (indice >= items.size()) return true;

                    ItemVenta item = items.get(indice);
                    Optional<Producto> productoOpt = inventario.stream()
                            .filter(p -> p.getId().equals(item.getProductoId()))
                            .findFirst();

                    if (productoOpt.isEmpty() || productoOpt.get().getCantidadStock() < item.getCantidad()) {
                        return false;
                    }

                    return apply(items, indice + 1);
                }
            };

    // ==================== FUNCIÓN 2: SIMULAR INVENTARIO (DESCUENTO) ====================

    /**
     * Actualiza inventario descontando productos vendidos
     * Usa map funcional para crear nuevo inventario inmutable
     */
    private final BiFunction<List<Producto>, List<ItemVenta>, List<Producto>> actualizarInventarioPorVenta =
            (productos, items) -> productos.stream()
                    .map(producto -> {
                        Optional<ItemVenta> itemOpt = items.stream()
                                .filter(i -> i.getProductoId().equals(producto.getId()))
                                .findFirst();

                        return itemOpt
                                .map(item -> producto.actualizarStock(producto.getCantidadStock() - item.getCantidad()))
                                .orElse(producto);
                    })
                    .collect(Collectors.toList());

    // ==================== FUNCIÓN 3: CONSULTAR INFORMACIÓN DE VENTAS ====================

    /**
     * Consulta ventas por diferentes criterios usando streams y lambdas
     */
    public void consultarVentas(Predicate<Venta> criterio) {
        List<Venta> resultado = ventas.stream()
                .filter(criterio)
                .collect(Collectors.toList());

        if (resultado.isEmpty()) {
            System.out.println("No se encontraron ventas con el criterio especificado");
            return;
        }

        System.out.println("\n📊 VENTAS ENCONTRADAS: " + resultado.size());
        imprimirListaRecursiva.accept(resultado, 0);
    }

    // Función recursiva para imprimir listas
    private final BiConsumer<List<?>, Integer> imprimirListaRecursiva =
            new BiConsumer<List<?>, Integer>() {
                @Override
                public void accept(List<?> lista, Integer indice) {
                    if (indice >= lista.size()) return;
                    System.out.println("\n" + (indice + 1) + ". " + lista.get(indice));
                    accept(lista, indice + 1);
                }
            };

    // ==================== FUNCIÓN 4: MOSTRAR VALOR TOTAL DE COMPRA ====================

    /**
     * Calcula el total de una venta usando reduce
     */
    public void mostrarTotalVenta(String codigoVenta) {
        Optional<Venta> ventaOpt = ventas.stream()
                .filter(v -> v.getCodigo().equals(codigoVenta))
                .findFirst();

        ventaOpt.ifPresentOrElse(
                venta -> System.out.println(String.format("💰 Total de venta %s: $%.2f",
                        codigoVenta, venta.getTotal())),
                () -> System.out.println("❌ Venta no encontrada")
        );
    }

    // ==================== FUNCIÓN 5: CONSULTAR INFORMACIÓN DE PRODUCTOS ====================

    /**
     * Información general de todos los productos (cantidad total)
     */
    public void consultarCantidadTotalProductos() {
        int total = inventario.stream()
                .mapToInt(Producto::getCantidadStock)
                .sum();

        System.out.println(String.format("📦 Cantidad total de productos en inventario: %d unidades", total));
    }

    /**
     * Información específica de un producto usando Optional
     */
    public void consultarProductoEspecifico(String id) {
        inventario.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .ifPresentOrElse(
                        producto -> {
                            System.out.println("\n🏷️  INFORMACIÓN DEL PRODUCTO:");
                            System.out.println("   Nombre: " + producto.getNombre());
                            System.out.println("   ID: " + producto.getId());
                            System.out.println("   Precio: $" + String.format("%.2f", producto.getPrecio()));
                            System.out.println("   Descripción: " + producto.getDescripcion());
                            System.out.println("   Stock: " + producto.getCantidadStock() + " unidades");
                        },
                        () -> System.out.println("❌ Producto no encontrado")
                );
    }

    // ==================== FUNCIÓN 6: CARGAR NUEVOS PRODUCTOS ====================

    /**
     * Agrega un nuevo producto usando composición funcional
     */
    public void cargarProducto(Producto nuevoProducto) {
        // Validar que no exista
        boolean existe = inventario.stream()
                .anyMatch(p -> p.getId().equals(nuevoProducto.getId()));

        if (existe) {
            System.out.println("❌ Error: Ya existe un producto con el ID " + nuevoProducto.getId());
            return;
        }

        // Agregar producto (inmutabilidad)
        inventario = Stream.concat(inventario.stream(), Stream.of(nuevoProducto))
                .collect(Collectors.toList());

        System.out.println("✅ Producto agregado exitosamente: " + nuevoProducto.getNombre());
    }

    // ==================== FUNCIÓN 7: ACTUALIZAR PRODUCTOS EXISTENTES ====================

    /**
     * Actualiza un producto existente usando map funcional
     */
    public void actualizarProducto(String id, String nombre, double precio, String descripcion, int stock) {
        boolean encontrado = inventario.stream()
                .anyMatch(p -> p.getId().equals(id));

        if (!encontrado) {
            System.out.println("❌ Error: Producto no encontrado");
            return;
        }

        inventario = inventario.stream()
                .map(p -> p.getId().equals(id) ? p.actualizar(nombre, precio, descripcion, stock) : p)
                .collect(Collectors.toList());

        System.out.println("✅ Producto actualizado exitosamente");
    }

    // ==================== FUNCIÓN 8: PROMEDIO DE VENTAS (SEMANAL, MENSUAL, ANUAL) ====================

    /**
     * Calcula promedio de ventas por período usando groupingBy y averaging
     */
    public void mostrarPromedioVentasSemanal() {
        calcularPromedioVentasPorPeriodo("semanal",
                venta -> venta.getFecha().getYear() + "-W" + venta.getFecha().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR));
    }

    public void mostrarPromedioVentasMensual() {
        calcularPromedioVentasPorPeriodo("mensual",
                venta -> venta.getFecha().getYear() + "-" + String.format("%02d", venta.getFecha().getMonthValue()));
    }

    public void mostrarPromedioVentasAnual() {
        calcularPromedioVentasPorPeriodo("anual",
                venta -> String.valueOf(venta.getFecha().getYear()));
    }

    private void calcularPromedioVentasPorPeriodo(String tipoPeriodo, Function<Venta, String> clasificadorPeriodo) {
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas");
            return;
        }

        Map<String, Double> promediosPorPeriodo = ventas.stream()
                .collect(Collectors.groupingBy(
                        clasificadorPeriodo,
                        Collectors.averagingDouble(Venta::getTotal)
                ));

        System.out.println(String.format("\n📈 PROMEDIO DE VENTAS POR %s:", tipoPeriodo.toUpperCase()));
        promediosPorPeriodo.forEach((periodo, promedio) ->
                System.out.println(String.format("   %s: $%.2f", periodo, promedio))
        );
    }

    // ==================== FUNCIÓN 9: PRODUCTO MÁS VENDIDO ====================

    /**
     * Encuentra el producto más vendido usando reduce y max
     */
    public void mostrarProductoMasVendido() {
        Map<String, Integer> cantidadesPorProducto = calcularCantidadesVendidasPorProducto();

        if (cantidadesPorProducto.isEmpty()) {
            System.out.println("No hay ventas registradas");
            return;
        }

        String productoIdMasVendido = cantidadesPorProducto.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        if (productoIdMasVendido != null) {
            int cantidad = cantidadesPorProducto.get(productoIdMasVendido);
            inventario.stream()
                    .filter(p -> p.getId().equals(productoIdMasVendido))
                    .findFirst()
                    .ifPresent(producto ->
                            System.out.println(String.format("\n🏆 PRODUCTO MÁS VENDIDO:\n   Nombre: %s\n   Precio: $%.2f\n   Cantidad vendida: %d unidades",
                                    producto.getNombre(), producto.getPrecio(), cantidad))
                    );
        }
    }

    // ==================== FUNCIÓN 10: PRODUCTO MENOS VENDIDO ====================

    /**
     * Encuentra el producto menos vendido usando min
     */
    public void mostrarProductoMenosVendido() {
        Map<String, Integer> cantidadesPorProducto = calcularCantidadesVendidasPorProducto();

        if (cantidadesPorProducto.isEmpty()) {
            System.out.println("No hay ventas registradas");
            return;
        }

        String productoIdMenosVendido = cantidadesPorProducto.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        if (productoIdMenosVendido != null) {
            int cantidad = cantidadesPorProducto.get(productoIdMenosVendido);
            inventario.stream()
                    .filter(p -> p.getId().equals(productoIdMenosVendido))
                    .findFirst()
                    .ifPresent(producto ->
                            System.out.println(String.format("\n📉 PRODUCTO MENOS VENDIDO:\n   Nombre: %s\n   Precio: $%.2f\n   Cantidad vendida: %d unidades",
                                    producto.getNombre(), producto.getPrecio(), cantidad))
                    );
        }
    }

    /**
     * Función auxiliar para calcular cantidades vendidas por producto
     * Usa flatMap y collectors
     */
    private Map<String, Integer> calcularCantidadesVendidasPorProducto() {
        return ventas.stream()
                .flatMap(venta -> venta.getItems().stream())
                .collect(Collectors.groupingBy(
                        ItemVenta::getProductoId,
                        Collectors.summingInt(ItemVenta::getCantidad)
                ));
    }

    // ==================== FUNCIÓN 11: PRODUCTOS SIN STOCK ====================

    /**
     * Muestra productos sin stock usando filter y forEach
     */
    public void mostrarProductosSinStock() {
        List<Producto> sinStock = inventario.stream()
                .filter(p -> p.getCantidadStock() == 0)
                .collect(Collectors.toList());

        if (sinStock.isEmpty()) {
            System.out.println("✅ Todos los productos tienen stock disponible");
            return;
        }

        System.out.println("\n⚠️  PRODUCTOS SIN STOCK:");
        sinStock.forEach(producto ->
                System.out.println(String.format("   - %s (ID: %s) - Cantidad: %d",
                        producto.getNombre(), producto.getId(), producto.getCantidadStock()))
        );
    }

    // ==================== MÉTODOS AUXILIARES ====================

    /**
     * Obtiene el inventario actual
     */
    public List<Producto> getInventario() {
        return List.copyOf(inventario);
    }

    /**
     * Obtiene las ventas registradas
     */
    public List<Venta> getVentas() {
        return List.copyOf(ventas);
    }

    /**
     * Muestra todo el inventario usando recursión
     */
    public void mostrarInventarioCompleto() {
        if (inventario.isEmpty()) {
            System.out.println("El inventario está vacío");
            return;
        }

        System.out.println("\n📦 INVENTARIO COMPLETO:");
        imprimirInventarioRecursivo.accept(inventario, 0);
    }

    // Función recursiva para imprimir inventario
    private final BiConsumer<List<Producto>, Integer> imprimirInventarioRecursivo =
            new BiConsumer<List<Producto>, Integer>() {
                @Override
                public void accept(List<Producto> productos, Integer indice) {
                    if (indice >= productos.size()) return;

                    Producto p = productos.get(indice);
                    System.out.println(String.format("%d. %s - $%.2f (Stock: %d)",
                            indice + 1, p.getNombre(), p.getPrecio(), p.getCantidadStock()));

                    accept(productos, indice + 1);
                }
            };
}