package model;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Clase inmutable que representa una venta
 */

public class Venta {
    private final String codigo;
    private final String id;
    private final LocalDate fecha;
    private final List<ItemVenta> items;  //uso de una lista inmutable

    //Constructor
    public Venta(String codigo, String id, LocalDate fecha, List<ItemVenta> items) {
        this.codigo = codigo;
        this.id = id;
        this.fecha = fecha;
        this.items = List.copyOf(items);
    }
    //Getters
    public double getTotal() {
        //utilizamoos steam para convetir items a un stream y hacer calculos sobre este
        return items.stream()
                .mapToDouble(ItemVenta::getSubtotal)
                .sum();
    }
    public String getCodigo() {return codigo;}
    public String getId() {return id;}
    public LocalDate getFecha() {return fecha;}
    public List<ItemVenta> getItems() {return items;}

    @Override //sobre escribimos el metodo para hacerlo mas legible cuando System.out.print(venta)
    public String toString() {
        String itemsStr = items.stream()
                .map(ItemVenta::toString)
                .collect(Collectors.joining("\n"));

        return String.format("Venta{codigo='%s', id='%s', fecha=%s, total=$%.2f}\nItems:\n%s",
                codigo, id, fecha, getTotal(), itemsStr);
    }




}
