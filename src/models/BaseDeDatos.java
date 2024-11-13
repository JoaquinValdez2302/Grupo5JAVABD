package models;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.util.Scanner;

public class BaseDeDatos {
     String url = "";
        String user = "";
        String password = "";
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        String sql = "";
        Scanner scanner = new Scanner(System.in);
        public  BaseDeDatos(){
            try {
                connection = DriverManager.getConnection(url, user, password);
                stmt = connection.createStatement();
            } catch (SQLException e) {
                System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            }
        }

public void agregarCliente(){
        try {
            System.out.print("Nombre: ");
            String Nombre = scanner.nextLine();
            System.out.print("apellido: ");
            String Apellido = scanner.nextLine(); 
            System.out.print("numero de telefono: ");
            String numeroTelefono = scanner.nextLine();
            sql = "INSERT INTO cliente (nombre, apellido, numeroTelefono) VALUES ('" + Nombre + "', '" + Apellido + "', '" + numeroTelefono + "');";
            int rowCount = stmt.executeUpdate(sql);
            System.out.println("Número de filas afectadas: " + rowCount);
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }


//Método para reservar una cancha
public void agregarReserva(String horario, String duracion){
        try {
            System.out.print("ingrese el id de la cancha a reservar:");
             int id_cancha = scanner.nextInt();
             System.out.println("----------------------------------------");
            System.out.println("- id del cliente:");
             System.out.println("----------------------------------------");
             int id_cliente = scanner.nextInt();
            //Modifica la tabla reserva con los datos proporcionados
            sql = "INSERT INTO reserva (horario, duracion, id_cliente) VALUES ('" + horario + "',  '" + duracion + " horas " + "', '" + id_cliente + "');";
            int rowCount = stmt.executeUpdate(sql);
            System.out.println("Número de filas afectadas: " + rowCount);
            sql = "SELECT id FROM reserva WHERE id_cliente = ?";
            PreparedStatement sentencia = connection.prepareStatement(sql);
            sentencia.setInt(1, id_cliente);
            rs = sentencia.executeQuery();
            //Cambia los valores de la cancha reservada 
            if (rs.next()) {
               int id_reserva= rs.getInt("id");
                System.out.println("El ID de la fila es: " + id_reserva);
                sql = "UPDATE cancha  SET id_reserva = '" + id_reserva + "', estado = 'ocupado' WHERE id = '" + id_cancha + "';";
                rowCount = stmt.executeUpdate(sql);
            } else {
                System.out.println("No se encontró la fila.");
            }
           

        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }
//Método para agregar una cancha de ser necesario
public void agregarCancha( String tipo_cancha){
        try {
            sql = "INSERT INTO cancha (tipo_cancha, estado) VALUES ('" + tipo_cancha +"' " + "'Disponible'" + ");";
            int rowCount = stmt.executeUpdate(sql);
            System.out.println("Número de filas afectadas: " + rowCount);
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }   
//Método para crear las primeras canchas
public void inicializarCanchas(){
    try {
        sql = "INSERT INTO cancha (tipo_cancha) VALUES ('Fútbol'), ('Basquet'), ('Tenis'), ('Fútbol'), ('Paddle'), ('Tenis');";
        int rowCount = stmt.executeUpdate(sql);
    } catch (SQLException e) {
        System.out.println("Error al conectar a la base de datos: " + e.getMessage());
    }
}
//Método para mostrar cada cancha con sus atributos 
public void mostrarCanchas () {
    try {
        sql = "SELECT * FROM cancha";
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            int id = rs.getInt("id");
            String tipo_cancha = rs.getString("tipo_cancha");
            String estado = rs.getString("estado");
            int id_reserva = rs.getInt("id_reserva");
            System.out.println("|id: " + id + " | tipo de cancha: " + tipo_cancha + " | " +  "estado: " + estado + "| " +  "id de la reserva: " + id_reserva + " |");
        }
    } catch (SQLException e) {
        System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }    
    }
//Método para mostrar las reservas
public void mostrarReservas () {
        try {
            sql = "SELECT * FROM reserva ";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String horario = rs.getString("horario");
                String duracion = rs.getString("duracion");
                int id_cliente = rs.getInt("id_cliente");
                System.out.println("id: " + id + ", horario: " + horario + ", duracion: " + duracion + "id_cliente: " + id_cliente);
            }
        
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            }
} 
//Método para eliminar una reserva a partir del id 
public void eliminarReserva(){
    try {
        System.out.println("ingrese el id de la reserva a eliminar");
        int id = scanner.nextInt();
        sql= "UPDATE cancha SET id_reserva = NULL WHERE id_reserva = '" + id + "' ;";
        int rowCount = stmt.executeUpdate(sql);
        sql= "UPDATE reserva SET id_cliente = NULL WHERE id = '" + id + "' ;";
        rowCount = stmt.executeUpdate(sql);
        sql = "DELETE FROM reserva WHERE id = '" + id + "';";
        rowCount = stmt.executeUpdate(sql);
        System.out.println("Número de filas afectadas: " + rowCount);
    } catch (SQLException e) {
        System.out.println("Error al conectar a la base de datos: " + e.getMessage());
    }
}
public  void modificarReserva(String horario, String duracion) {
    try {
        System.out.println("ingrese el id de la reserva a modificar");
        int id = scanner.nextInt();
        sql= "UPDATE reserva SET horario = '" + horario + "' WHERE id = '" + id + "' ;";
        int rowCount = stmt.executeUpdate(sql);
        sql= "UPDATE reserva SET duracion = '" + duracion + " horas '" + " WHERE id = '" + id + "' ;";
         rowCount = stmt.executeUpdate(sql);
        System.out.println("Número de filas afectadas: " + rowCount);
    } catch (SQLException e) {
        System.out.println("Error al conectar a la base de datos: " + e.getMessage());
    }
}
//Método inicial para crear las tablas necesarias
public void crearTablas (){
        try {   
            sql = "CREATE TABLE cliente (" + "id INT AUTO_INCREMENT PRIMARY KEY, " + "nombre VARCHAR(50) NOT NULL, " + "apellido VARCHAR (50) NOT NULL, " + "numeroTelefono VARCHAR (50)" + ");" ;
            int rowCount = stmt.executeUpdate(sql);
            sql = "CREATE TABLE reserva (" + "id INT AUTO_INCREMENT PRIMARY KEY, " + "horario VARCHAR(50) NOT NULL, " + "duracion varchar(50), " + "id_cliente int, " +  "FOREIGN KEY (id_cliente) REFERENCES cliente (id)" + ");" ; 
            rowCount = stmt.executeUpdate(sql);
            sql = "CREATE TABLE cancha (" + "id INT AUTO_INCREMENT PRIMARY KEY, " + "tipo_cancha VARCHAR(50) NOT NULL, " + "id_reserva int," + "FOREIGN KEY (id_reserva) REFERENCES reserva (id)," +  "estado VARCHAR(50)" + ");" ; 
            rowCount = stmt.executeUpdate(sql);
           System.out.println("Número de filas afectadas: " + rowCount);
       } catch (SQLException e) {
           System.out.println("Error al conectar a la base de datos: " + e.getMessage());
       }
       }
// Método final que elimina las tablas
public void eliminarTablas () {
    try {
        sql = "DROP TABLE cliente, reserva, cancha;";
        int rowCount = stmt.executeUpdate(sql);
    } catch (SQLException e) {
        System.out.println("Error al conectar a la base de datos: " + e.getMessage());
    }
}
public void cerrarConexion(){
        try {
            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
    
