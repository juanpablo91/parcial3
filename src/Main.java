import Ecommerce.ECommerce;
import model.ItemVenta;
import model.Producto;

import java.time.LocalDate;
import java.util.*;
import java.util.function.*;

/**
 * Clase principal con menú interactivo usando programación funcional
 * El menú se implementa con recursión (sin ciclos while/for)
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static ECommerce ecommerce = new ECommerce();

    public static void main(String[] args) {
        mostrarBienvenida();
        // Iniciar el menú principal de forma recursiva
        mostrarMenuPrincipal();
        scanner.close();
    }

    /**
     * Muestra el mensaje de bienvenida
     */
    private static void mostrarBienvenida() {
        System.out.println("\n═══════════════════════════════════════════════════════════");
        System.out.println("    🛍️  SISTEMA DE E-COMMERCE DE ROPA");
        System.out.println("        Programación Funcional en Java");
        System.out.println("═══════════════════════════════════════════════════════════\n");
    }

    /**
     * FUNCIÓN RECURSIVA para mostrar el menú principal
     * Reemplaza el ciclo while tradicional
     */
    private static void mostrarMenuPrincipal() {
        System.out.println("\n╔════════════════════ MENÚ PRINCIPAL ═══════════════════════╗");
        System.out.println("║  1.  Cargar nuevo producto                                ║");
        System.out.println("║  2.  Actualizar producto existente                        ║");
        System.out.println("║  3.  Ver inventario completo                              ║");
        System.out.println("║  4.  Consultar cantidad total de productos                ║");
        System.out.println("║  5.  Consultar producto específico por ID                 ║");
        System.out.println("║  6.  Registrar nueva venta                                ║");
        System.out.println("║  7.  Consultar ventas                                     ║");
        System.out.println("║  8.  Mostrar total de una venta                           ║");
        System.out.println("║  9.  Promedio de ventas (semanal, mensual, anual)         ║");
        System.out.println("║  10. Producto más vendido                                 ║");
        System.out.println("║  11. Producto menos vendido                               ║");
        System.out.println("║  12. Productos sin stock                 " +
                "" +
                "" +
                "                 ║");
        System.out.println("║  13. Cargar datos de ejemplo(Remomendado)                 ║");
        System.out.println("║  0.  Salir                                                ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
        System.out.print("\n👉 Seleccione una opción: ");

        String entrada = scanner.nextLine().trim();

        // Usar Optional y lambdas para manejar la opción
        procesarOpcion(entrada);
    }

    /**
     * Procesa la opción seleccionada usando programación funcional
     */
    private static void procesarOpcion(String opcion) {
        // Map de opciones a acciones (funcional)
        Map<String, Runnable> acciones = Map.ofEntries(
                Map.entry("1", () -> opcionCargarProducto()),
                Map.entry("2", () -> opcionActualizarProducto()),
                Map.entry("3", () -> opcionVerInventario()),
                Map.entry("4", () -> opcionCantidadTotal()),
                Map.entry("5", () -> opcionConsultarProducto()),
                Map.entry("6", () -> opcionRegistrarVenta()),
                Map.entry("7", () -> opcionConsultarVentas()),
                Map.entry("8", () -> opcionMostrarTotalVenta()),
                Map.entry("9", () -> opcionPromedioVentas()),
                Map.entry("10", () -> opcionProductoMasVendido()),
                Map.entry("11", () -> opcionProductoMenosVendido()),
                Map.entry("12", () -> opcionProductosSinStock()),
                Map.entry("13", () -> opcionCargarDatosEjemplo()),
                Map.entry("0", () -> opcionSalir())
        );

        // Ejecutar acción usando Optional (funcional)
        Optional.ofNullable(acciones.get(opcion))
                .ifPresentOrElse(
                        accion -> {
                            accion.run();
                            // Si no es salir, volver a mostrar menú (recursión)
                            if (!opcion.equals("0")) {
                                pausar();
                                mostrarMenuPrincipal();
                            }
                        },
                        () -> {
                            System.out.println("❌ Opción inválida. Intente nuevamente.");
                            mostrarMenuPrincipal(); // Recursión
                        }
                );
    }

    // ==================== OPCIONES DEL MENÚ ====================

    /**
     * Opción 1: Cargar nuevo producto
     */
    private static void opcionCargarProducto() {
        System.out.println("\n╔═══════════════ CARGAR NUEVO PRODUCTO ═══════════════╗");

        System.out.print("ID del producto: ");
        String id = scanner.nextLine().trim();

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();

        System.out.print("Precio: $");
        double precio = leerDouble();

        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine().trim();

        System.out.print("Cantidad en stock: ");
        int stock = leerInt();

        Producto nuevoProducto = new Producto(id, nombre, precio, descripcion, stock);
        ecommerce.cargarProducto(nuevoProducto);
    }

    /**
     * Opción 2: Actualizar producto existente
     */
    private static void opcionActualizarProducto() {
        System.out.println("\n╔═══════════════ ACTUALIZAR PRODUCTO ═══════════════╗");

        System.out.print("ID del producto a actualizar: ");
        String id = scanner.nextLine().trim();

        System.out.print("Nuevo nombre: ");
        String nombre = scanner.nextLine().trim();

        System.out.print("Nuevo precio: $");
        double precio = leerDouble();

        System.out.print("Nueva descripción: ");
        String descripcion = scanner.nextLine().trim();

        System.out.print("Nuevo stock: ");
        int stock = leerInt();

        ecommerce.actualizarProducto(id, nombre, precio, descripcion, stock);
    }

    /**
     * Opción 3: Ver inventario completo
     */
    private static void opcionVerInventario() {
        System.out.println("\n╔═══════════════ INVENTARIO COMPLETO ═══════════════╗");
        ecommerce.mostrarInventarioCompleto();
    }

    /**
     * Opción 4: Consultar cantidad total
     */
    private static void opcionCantidadTotal() {
        System.out.println("\n╔═══════════════ CANTIDAD TOTAL DE PRODUCTOS ═══════════════╗");
        ecommerce.consultarCantidadTotalProductos();
    }

    /**
     * Opción 5: Consultar producto específico
     */
    private static void opcionConsultarProducto() {
        System.out.println("\n╔═══════════════ CONSULTAR PRODUCTO ═══════════════╗");
        System.out.print("ID del producto: ");
        String id = scanner.nextLine().trim();
        ecommerce.consultarProductoEspecifico(id);
    }

    /**
     * Opción 6: Registrar nueva venta
     */
    private static void opcionRegistrarVenta() {
        System.out.println("\n╔═══════════════ REGISTRAR NUEVA VENTA ═══════════════╗");

        System.out.print("Código de venta: ");
        String codigo = scanner.nextLine().trim();

        System.out.print("ID de venta: ");
        String id = scanner.nextLine().trim();

        LocalDate fecha = LocalDate.now();
        System.out.println("Fecha de venta: " + fecha);

        // Recolectar items usando recursión
        List<ItemVenta> items = recolectarItemsVenta(new ArrayList<>());

        if (!items.isEmpty()) {
            ecommerce.registrarVenta(codigo, id, fecha, items);
        } else {
            System.out.println("❌ No se agregaron productos a la venta");
        }
    }

    /**
     * FUNCIÓN RECURSIVA para recolectar items de venta
     */
    private static List<ItemVenta> recolectarItemsVenta(List<ItemVenta> itemsActuales) {
        System.out.print("\n¿Agregar producto a la venta? (s/n): ");
        String respuesta = scanner.nextLine().trim().toLowerCase();

        if (!respuesta.equals("s")) {
            return itemsActuales;
        }

        System.out.print("ID del producto: ");
        String productoId = scanner.nextLine().trim();

        // Buscar producto en inventario
        Optional<Producto> productoOpt = ecommerce.getInventario().stream()
                .filter(p -> p.getId().equals(productoId))
                .findFirst();

        if (productoOpt.isEmpty()) {
            System.out.println("❌ Producto no encontrado");
            return recolectarItemsVenta(itemsActuales); // Recursión
        }

        Producto producto = productoOpt.get();
        System.out.println("Producto: " + producto.getNombre() + " - Stock disponible: " + producto.getCantidadStock());

        System.out.print("Cantidad: ");
        int cantidad = leerInt();

        if (cantidad > producto.getCantidadStock()) {
            System.out.println("❌ Stock insuficiente");
            return recolectarItemsVenta(itemsActuales); // Recursión
        }

        ItemVenta nuevoItem = new ItemVenta(productoId, producto.getNombre(), cantidad, producto.getPrecio());
        List<ItemVenta> nuevaLista = new ArrayList<>(itemsActuales);
        nuevaLista.add(nuevoItem);

        System.out.println("✅ Producto agregado: " + nuevoItem);

        // Llamada recursiva para agregar más items
        return recolectarItemsVenta(nuevaLista);
    }

    /**
     * Opción 7: Consultar ventas
     */
    private static void opcionConsultarVentas() {
        System.out.println("\n╔═══════════════ CONSULTAR VENTAS ═══════════════╗");
        System.out.println("1. Todas las ventas");
        System.out.println("2. Ventas de hoy");
        System.out.println("3. Ventas por código");
        System.out.print("Seleccione: ");

        String opcion = scanner.nextLine().trim();

        switch (opcion) {
            case "1" -> ecommerce.consultarVentas(venta -> true);
            case "2" -> ecommerce.consultarVentas(venta -> venta.getFecha().equals(LocalDate.now()));
            case "3" -> {
                System.out.print("Código de venta: ");
                String codigo = scanner.nextLine().trim();
                ecommerce.consultarVentas(venta -> venta.getCodigo().equals(codigo));
            }
            default -> System.out.println("❌ Opción inválida");
        }
    }

    /**
     * Opción 8: Mostrar total de venta
     */
    private static void opcionMostrarTotalVenta() {
        System.out.println("\n╔═══════════════ TOTAL DE VENTA ═══════════════╗");
        System.out.print("Código de venta: ");
        String codigo = scanner.nextLine().trim();
        ecommerce.mostrarTotalVenta(codigo);
    }

    /**
     * Opción 9: Promedio de ventas
     */
    private static void opcionPromedioVentas() {
        System.out.println("\n╔═══════════════ PROMEDIO DE VENTAS ═══════════════╗");
        System.out.println("1. Promedio semanal");
        System.out.println("2. Promedio mensual");
        System.out.println("3. Promedio anual");
        System.out.print("Seleccione: ");

        String opcion = scanner.nextLine().trim();

        switch (opcion) {
            case "1" -> ecommerce.mostrarPromedioVentasSemanal();
            case "2" -> ecommerce.mostrarPromedioVentasMensual();
            case "3" -> ecommerce.mostrarPromedioVentasAnual();
            default -> System.out.println("❌ Opción inválida");
        }
    }

    /**
     * Opción 10: Producto más vendido
     */
    private static void opcionProductoMasVendido() {
        System.out.println("\n╔═══════════════ PRODUCTO MÁS VENDIDO ═══════════════╗");
        ecommerce.mostrarProductoMasVendido();
    }

    /**
     * Opción 11: Producto menos vendido
     */
    private static void opcionProductoMenosVendido() {
        System.out.println("\n╔═══════════════ PRODUCTO MENOS VENDIDO ═══════════════╗");
        ecommerce.mostrarProductoMenosVendido();
    }

    /**
     * Opción 12: Productos sin stock
     */
    private static void opcionProductosSinStock() {
        System.out.println("\n╔═══════════════ PRODUCTOS SIN STOCK ═══════════════╗");
        ecommerce.mostrarProductosSinStock();
    }

    /**
     * Opción 13: Cargar datos de ejemplo
     */
    private static void opcionCargarDatosEjemplo() {
        System.out.println("\n╔═══════════════ CARGAR DATOS DE EJEMPLO ═══════════════╗");
        System.out.print("¿Está seguro? Esto cargará productos y ventas de prueba (s/n): ");
        String confirmacion = scanner.nextLine().trim().toLowerCase();

        if (confirmacion.equals("s")) {
            cargarDatosEjemplo();
            System.out.println("✅ Datos de ejemplo cargados exitosamente");
        }
    }

    /**
     * Opción 0: Salir
     */
    private static void opcionSalir() {
        System.out.println("\n═══════════════════════════════════════════════════════════");
        System.out.println("    👋 ¡Gracias por usar el Sistema E-Commerce!");
        System.out.println("═══════════════════════════════════════════════════════════\n");
    }

    // ==================== FUNCIONES AUXILIARES ====================

    /**
     * Carga datos de ejemplo en el sistema
     */
    private static void cargarDatosEjemplo() {
        List<Producto> productosIniciales = List.of(
                new Producto("P001", "Camisa Polo", 75000, "Camisa polo de algodón 100%", 20),
                new Producto("P002", "Jean Clásico", 120000, "Jean azul corte clásico", 15),
                new Producto("P003", "Vestido Casual", 95000, "Vestido casual para mujer", 10),
                new Producto("P004", "Chaqueta de Cuero", 350000, "Chaqueta de cuero genuino", 5),
                new Producto("P005", "Zapatos Deportivos", 180000, "Zapatos para correr", 12),
                new Producto("P006", "Blusa Elegante", 65000, "Blusa de seda para oficina", 18),
                new Producto("P007", "Pantalón Formal", 98000, "Pantalón de vestir negro", 14),
                new Producto("P008", "Suéter de Lana", 110000, "Suéter tejido a mano", 8),
                new Producto("P009", "Shorts Deportivos", 45000, "Shorts para gym", 25),
                new Producto("P010", "Abrigo de Invierno", 280000, "Abrigo largo para invierno", 6)
        );

        productosIniciales.forEach(ecommerce::cargarProducto);

        // Registrar ventas de ejemplo
        List<ItemVenta> items1 = List.of(
                new ItemVenta("P001", "Camisa Polo", 2, 75000),
                new ItemVenta("P002", "Jean Clásico", 1, 120000)
        );
        ecommerce.registrarVenta("V001", "VENTA-001", LocalDate.now(), items1);

        List<ItemVenta> items2 = List.of(
                new ItemVenta("P003", "Vestido Casual", 1, 95000),
                new ItemVenta("P006", "Blusa Elegante", 2, 65000)
        );
        ecommerce.registrarVenta("V002", "VENTA-002", LocalDate.now().minusDays(7), items2);

        List<ItemVenta> items3 = List.of(
                new ItemVenta("P004", "Chaqueta de Cuero", 1, 350000)
        );
        ecommerce.registrarVenta("V003", "VENTA-003", LocalDate.now().minusMonths(1), items3);
    }

    /**
     * Lee un número entero con manejo de errores funcional
     */
    private static int leerInt() {
        try {
            int valor = Integer.parseInt(scanner.nextLine().trim());
            return valor;
        } catch (NumberFormatException e) {
            System.out.print("❌ Entrada inválida. Intente nuevamente: ");
            return leerInt(); // Recursión para reintentar
        }
    }

    /**
     * Lee un número double con manejo de errores funcional
     */
    private static double leerDouble() {
        try {
            double valor = Double.parseDouble(scanner.nextLine().trim());
            return valor;
        } catch (NumberFormatException e) {
            System.out.print("❌ Entrada inválida. Intente nuevamente: ");
            return leerDouble(); // Recursión para reintentar
        }
    }

    /**
     * Pausa para que el usuario lea el resultado
     */
    private static void pausar() {
        System.out.print("\n📌 Presione Enter para continuar...");
        scanner.nextLine();
    }
}