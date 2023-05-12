package jfxsistemaequiposcomputo.DAO;

import jfxsistemaequiposcomputo.pojo.SolicitudMantenimiento;
import jfxsistemaequiposcomputo.utils.Constantes;
import org.junit.Test;
import static org.junit.Assert.*;

public class SolicitudesDAOTest {
@Test
    public void testCrearSolicitud() {
        System.out.println("crearSolicitud");
        byte[] imagen = {1, 2, 3, 4};
        SolicitudMantenimiento solicitud = new SolicitudMantenimiento();
        solicitud.setIdUsuario(1); //Requiere tener registros válidos de usuario en la BD
        solicitud.setTipo("sdg");
        solicitud.setIncluyeCargador(true);
        solicitud.setModelo("dsdf");
        solicitud.setSistemaOperativo("wef");
        solicitud.setContraeniaSO("fwe");
        solicitud.setMarca("sggr");
        solicitud.setImagen(imagen);
        solicitud.setUsuarioSO("erwer");
        int respuestaEsperada = Constantes.OPERACION_EXITOSA;
        int respuestaRecibida = SolicitudesDAO.crearSolicitud(solicitud);
        assertEquals(respuestaEsperada, respuestaRecibida);
    }
}