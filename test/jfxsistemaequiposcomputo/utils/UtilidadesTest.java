package jfxsistemaequiposcomputo.utils;

import org.junit.Test;
import static org.junit.Assert.*;

public class UtilidadesTest {

    @Test
    public void testFechaActualFormatoMySQL() {
        System.out.println("fechaActualFormatoMySQL");
        String fechaEsperada = "2023-05-16";
        String fechaRespuesta = Utilidades.fechaActualFormatoMySQL();
        assertEquals(fechaEsperada, fechaRespuesta);
    }
    
}
