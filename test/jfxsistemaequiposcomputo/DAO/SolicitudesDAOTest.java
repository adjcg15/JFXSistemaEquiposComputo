package jfxsistemaequiposcomputo.DAO;

import jfxsistemaequiposcomputo.pojo.EquipoComputo;
import jfxsistemaequiposcomputo.pojo.Solicitud;
import jfxsistemaequiposcomputo.pojo.SolicitudConUsuarioYEquipo;
import jfxsistemaequiposcomputo.pojo.Usuario;
import jfxsistemaequiposcomputo.utils.Constantes;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rodri
 */
public class SolicitudesDAOTest {
    @Test
    public void testCrearSolicitud() {
        System.out.println("crearSolicitud");
        Solicitud solicitud = new Solicitud();
        
        solicitud.setIdUsuario(2); // Debe ser un ID registrado en la BD
        solicitud.setObservaciones("Prueba");
        
        int respuestaEsperada = 23; //Se debe verificar en la BD cuál es el siguiente ID
        int respuestaRecibida = SolicitudesDAO.crearSolicitud(solicitud);
        assertEquals(respuestaEsperada, respuestaRecibida);
    }

    @Test
    public void testCrearSolicitudConUsuarioYEquipo() {
        System.out.println("crearSolicitudConUsuarioYEquipo");
        Solicitud solicitud = new Solicitud();
        EquipoComputo equipo = new EquipoComputo();
        byte[] imagen = {1, 2, 3, 4};
        Usuario usuario = new Usuario();
        SolicitudConUsuarioYEquipo solicitudCompleta = new SolicitudConUsuarioYEquipo();
        
        solicitud.setIdEstado(1);  // Debe ser un ID registrado en la BD
        solicitud.setIdUsuario(2); // Debe ser un ID registrado en la BD
        solicitud.setObservaciones("Prueba");
        
        equipo.setTipo("Prueba");
        equipo.setIncluyeCargador(true);
        equipo.setMarca("Prueba");
        equipo.setModelo("Prueba");
        equipo.setTamanioPantalla(12.5F);
        equipo.setProcesador("Prueba");
        equipo.setMemoriaRAM(6);
        equipo.setSistemaOperativo("Prueba");
        equipo.setUsuarioSO("Prueba");
        equipo.setContraseniaSO("Prueba");
        equipo.setImagen(imagen);
        equipo.setIncluyeCargador(true);
        
        usuario.setIdUsuario(2); // Debe ser un ID registrado en la BD
        usuario.setApellidoMaterno("Prueba");
        usuario.setApellidoPaterno("Prueba");
        usuario.setContrasenia("Prueba");
        usuario.setCorreo("Prueba");
        usuario.setDirección("Prueba");
        usuario.setNombre("Prueba");
        usuario.setPrivilegiado(true);
        usuario.setTelefono("Prueba");
        
        solicitudCompleta.setSolicitud(solicitud);
        solicitudCompleta.setEquipo(equipo);
        solicitudCompleta.setUsuario(usuario);
        
        int respuestaEsperada = Constantes.OPERACION_EXITOSA;
        int respuestaRecibida = SolicitudesDAO.crearSolicitudConUsuarioYEquipo(solicitudCompleta);
        assertEquals(respuestaEsperada, respuestaRecibida);
    }
}
