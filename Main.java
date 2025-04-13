import java.util.*;

class Entrada {
    private int numero;
    private String zona;
    private String tipoCliente;
    private double precioFinal;

    public Entrada(int numero, String zona, String tipoCliente, double precioFinal) {
        this.numero = numero;
        this.zona = zona;
        this.tipoCliente = tipoCliente;
        this.precioFinal = precioFinal;
    }

    public int getNumero() {
        return numero;
    }

    public String getZona() {
        return zona;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public double getPrecioFinal() {
        return precioFinal;
    }

    public String toString() {
        return "Entrada N°" + numero + " | Zona: " + zona + " | Tipo: " + tipoCliente + " | Precio: $" + String.format("%,.0f", precioFinal);
    }
}

public class Main {
    static Scanner sc = new Scanner(System.in);
    static List<Entrada> entradasVendidas = new ArrayList<>();
    static int contadorEntradas = 1;
    static double ingresosTotales = 0;
    static int totalVendidas = 0;
    static final int CAPACIDAD_SALA = 150;


    static final double PRECIO_VIP = 100000;
    static final double PRECIO_PLATEA_BAJA = 80000;
    static final double PRECIO_PLATEA_ALTA = 60000;
    static final double PRECIO_PALCO = 40000;

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- Teatro Moro ---");
            System.out.println("1. Comprar entrada");
            System.out.println("2. Ver promociones");
            System.out.println("3. Buscar entrada");
            System.out.println("4. Eliminar entrada");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = leerEntero();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    comprarEntrada();
                    break;
                case 2:
                    mostrarPromociones();
                    break;
                case 3:
                    buscarEntrada();
                    break;
                case 4:
                    eliminarEntrada();
                    break;
                case 5:
                    System.out.println("Saliendo del programa... ¡Hasta luego!");
                    return;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    static void comprarEntrada() {
        System.out.println("Seleccione una zona (VIP, Platea Baja, Platea Alta, Palco):");
        String zona = sc.nextLine().trim();
        double precioBase;

        switch (zona.toLowerCase()) {
            case "vip":
                precioBase = PRECIO_VIP;
                break;
            case "platea baja":
                precioBase = PRECIO_PLATEA_BAJA;
                break;
            case "platea alta":
                precioBase = PRECIO_PLATEA_ALTA;
                break;
            case "palco":
                precioBase = PRECIO_PALCO;
                break;
            default:
                System.out.println("Zona inválida.");
                return;
        }

        System.out.print("Ingrese su edad: ");
        int edad = leerEntero();
        if (edad < 1 || edad > 99) {
            System.out.println("Edad invalido. Por favor intente nuevamente");
        }
        sc.nextLine();

        String tipoCliente;
        double descuento = 0;

        if (edad <= 17) {
            descuento = 0.10;
            tipoCliente = "Estudiante";
        } else if (edad >= 60) {
            descuento = 0.15;
            tipoCliente = "Tercera Edad";
        } else {
            tipoCliente = "General";
        }

        System.out.print("Cantidad de entradas: ");
        int cantidad = leerEntero();
        sc.nextLine();

        if (totalVendidas + cantidad > CAPACIDAD_SALA) {
            System.out.println("No hay suficientes entradas disponibles. Quedan: " + (CAPACIDAD_SALA - totalVendidas));
            return;
        }


        if (zona.equalsIgnoreCase("VIP") && cantidad == 5) {
            precioBase = PRECIO_PLATEA_BAJA;
            System.out.println("¡Promoción aplicada! 5 entradas VIP al precio de Platea Baja.");
        }

        double precioFinalUnitario = precioBase * (1 - descuento);
        double total = precioFinalUnitario * cantidad;

        for (int i = 0; i < cantidad; i++) {
            entradasVendidas.add(new Entrada(contadorEntradas++, zona, tipoCliente, precioFinalUnitario));
        }

        ingresosTotales += total;
        totalVendidas += cantidad;

        System.out.println("\nResumen de compra:");
        System.out.println("Zona: " + zona);
        System.out.println("Tipo cliente: " + tipoCliente);
        System.out.println("Precio por entrada: $" + String.format("%,.0f", precioFinalUnitario));
        System.out.println("Entradas compradas: " + cantidad);
        System.out.println("Total a pagar: $" + String.format("%,.0f", total));
    }

    static void mostrarPromociones() {
        System.out.println("\n--- Promociones ---");
        System.out.println("- 10% de descuento para estudiantes (menores de 18 años).");
        System.out.println("- 15% de descuento para adultos mayores (60 años o más).");
        System.out.println("- Compra 5 entradas VIP y paga como si fueran Platea Baja.");
    }

    static void buscarEntrada() {
        System.out.println("Buscar por:");
        System.out.println("1. Número de entrada");
        System.out.println("2. Zona");
        System.out.println("3. Tipo de cliente");
        System.out.print("Seleccione una opción: ");
        int opcion = leerEntero();
        sc.nextLine();

        boolean encontrada = false;
        switch (opcion) {
            case 1:
                System.out.print("Ingrese número de entrada: ");
                int numero = leerEntero();
                for (Entrada e : entradasVendidas) {
                    if (e.getNumero() == numero) {
                        System.out.println(e);
                        encontrada = true;
                    }
                }
                break;
            case 2:
                System.out.print("Ingrese zona (VIP, Platea Baja, etc.): ");
                String zona = sc.nextLine().trim();
                for (Entrada e : entradasVendidas) {
                    if (e.getZona().equalsIgnoreCase(zona)) {
                        System.out.println(e);
                        encontrada = true;
                    }
                }
                break;
            case 3:
                System.out.print("Ingrese tipo (Estudiante, Tercera Edad, General): ");
                String tipo = sc.nextLine().trim();
                for (Entrada e : entradasVendidas) {
                    if (e.getTipoCliente().equalsIgnoreCase(tipo)) {
                        System.out.println(e);
                        encontrada = true;
                    }
                }
                break;
            default:
                System.out.println("Opción inválida.");
        }

        if (!encontrada) {
            System.out.println("No se encontraron entradas con esos datos.");
        }
    }

    static void eliminarEntrada() {
        System.out.print("Ingrese el número de entrada a eliminar: ");
        int numero = leerEntero();
        sc.nextLine();

        Iterator<Entrada> it = entradasVendidas.iterator();
        boolean eliminada = false;

        while (it.hasNext()) {
            Entrada e = it.next();
            if (e.getNumero() == numero) {
                ingresosTotales -= e.getPrecioFinal();
                totalVendidas--;
                it.remove();
                eliminada = true;
                System.out.println("Entrada eliminada correctamente.");
                break;
            }
        }

        if (!eliminada) {
            System.out.println("No se encontró la entrada con ese número.");
        }
    }

    static int leerEntero() {
        while (!sc.hasNextInt()) {
            System.out.println("Entrada inválida. Intente nuevamente:");
            sc.next();
        }
        return sc.nextInt();
    }
}

