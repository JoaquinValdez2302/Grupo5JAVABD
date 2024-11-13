package models;
import java.util.List;

public class SistemaReservas {
    

    public SistemaReservas() {
         }
 // Método para verificar si el horario es válido
    public boolean horarioValido(String horario) {
        String[] partes = horario.split(":");
        int hora = Integer.parseInt(partes[0]);
        return hora >= 13 && hora < 26; // Predio abierto de 13:00 a 02:00
    }

 

    

   
    

    
}
