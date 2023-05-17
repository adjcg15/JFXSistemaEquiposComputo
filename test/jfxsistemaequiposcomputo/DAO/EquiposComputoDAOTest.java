package jfxsistemaequiposcomputo.DAO;

import jfxsistemaequiposcomputo.pojo.EquipoComputo;
import jfxsistemaequiposcomputo.utils.Constantes;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rodri
 */
public class EquiposComputoDAOTest {
    @Test
    public void testAsociarSolicitudEquipo() {
        System.out.println("asociarSolicitudEquipo");
        
        EquipoComputo equipo = new EquipoComputo();
        
        byte[] imagen = {1, 2, 3, 4};
        
        equipo.setIdUsuario(2); //Debe ser un ID existente en la BD
        equipo.setIdSolicitud(20); //Debe ser un ID existente en la BD
        equipo.setTipo("Prueba");
        equipo.setIncluyeCargador(true);
        equipo.setMarca("Prueba");
        equipo.setModelo("Prueba");
        equipo.setTamanioPantalla("Prueba");
        equipo.setProcesador("Prueba");
        equipo.setMemoriaRAM("Prueba");
        equipo.setSistemaOperativo("Prueba");
        equipo.setUsuarioSO("Prueba");
        equipo.setContraseniaSO("Prueba");
        equipo.setImagen(imagen);
        
        int respuestaEspereda = Constantes.OPERACION_EXITOSA;
        int respuestaRecibida = EquiposComputoDAO.asociarSolicitudEquipo(equipo);
        assertEquals(respuestaEspereda, respuestaRecibida);
    }
    
}
