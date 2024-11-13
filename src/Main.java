import java.util.Scanner;
import models.BaseDeDatos;
import models.SistemaReservas;
public class Main {
    
    public static void mostrarMenu() {
        System.out.println("\n--- Sistema de Alquiler de Canchas ---");
        System.out.println("1. Reservar una cancha");
        System.out.println("2. Liberar una cancha");
        System.out.println("3. Ver todas las canchas");
        System.out.println("4. Modificar reserva");
        System.out.println("5. Salir");
    }

    

    public static void main(String[] args) {
        
        
        BaseDeDatos bd1 = new BaseDeDatos();
        Scanner scanner = new Scanner(System.in);
        SistemaReservas sistema = new SistemaReservas();
       System.out.println("¿primera vez que se ejecuta? (s/n)");

        
        bd1.crearTablas();
        bd1.inicializarCanchas();
        while (true) {
            mostrarMenu();
            System.out.print("Selecciona una opcion: ");
            String opcion = scanner.nextLine();
            
            switch (opcion) {
                case "1":
                    System.out.print("Ingresa el horario de la reserva (HH:MM): ");
                    String horario = scanner.nextLine();
                    System.out.println("ingresa la duración de la reserva en horas (1,2,3, etc): ");
                    String duracion = scanner.nextLine();
                    if (sistema.horarioValido(horario)) {
                         bd1.mostrarCanchas();
                        // Solicitar datos del cliente
                        
                       System.out.println("\n----------------------------");
                       System.out.println("ingrese los datos del cliente");
                        bd1.agregarCliente ();
                        // Realizar la reserva con el cliente
                        bd1.agregarReserva(horario, duracion);
                    } else{   System.out.println("Horario no válido. El predio está abierto de 13:00 a 02:00.");}
                    break;
                case "2":
                    bd1.mostrarReservas();
                    bd1.eliminarReserva();
                    break;
                case "3":
                    bd1.mostrarCanchas();
                    break;
                case "4":
                    System.out.print("Ingresa el nuevo horario de la reserva (HH:MM): ");
                        horario = scanner.nextLine();
                    System.out.println("ingresa la duración de la reserva en horas (1,2,3, etc): ");
                        duracion = scanner.nextLine();
                    if (sistema.horarioValido(horario)) {
                        bd1.mostrarReservas();
                        bd1.modificarReserva(horario, duracion);
                         
                    } else{   System.out.println("Horario no válido. El predio está abierto de 13:00 a 02:00.");}
                    break;
                case "5":
                    
                    System.out.println("Gracias por utilizar el sistema de reservas del Grupo n5!!!");
                    System.out.println("Hasta luego!!!");
                    bd1.eliminarTablas();
                    scanner.close();
                    bd1.cerrarConexion();
                    return;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        }
    }
}

