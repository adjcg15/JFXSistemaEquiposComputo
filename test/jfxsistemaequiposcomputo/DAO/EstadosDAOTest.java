package jfxsistemaequiposcomputo.DAO;
import jfxsistemaequiposcomputo.utils.Constantes;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rodri
 */
public class EstadosDAOTest {
    @Test
    public void testObtenerIdEstadoPorNombre() {
        System.out.println("obtenerIdEstadoPorNombre");
        String estadoPendiente = Constantes.ESTADO_SOLICITUD_PENDIENTE;
        int respuestaEsperada = 1; //Puede regresar 1 (pendiente), 2 (aceptada), y 3 (rechazada)
        int respuestaRecibida = EstadosDAO.obtenerIdEstadoPorNombre(estadoPendiente);
        assertEquals(respuestaEsperada, respuestaRecibida);
    }
    
    @Test
    public void testAsociarEstadoSolicitud() {
        System.out.println("asociarEstadoSolicitud");
        int idSolicitud = 20; // Debe ser in ID existente en la BD
        int idEstado = 1; // Debe ser in ID existente en la BD
        int respuestaEsperada = Constantes.OPERACION_EXITOSA;
        int respuestaRecibida = EstadosDAO.asociarEstadoSolicitud(idSolicitud, idEstado);
        assertEquals(respuestaEsperada, respuestaRecibida);
    }
    
}
