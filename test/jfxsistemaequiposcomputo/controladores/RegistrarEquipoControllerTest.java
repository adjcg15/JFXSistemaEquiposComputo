package jfxsistemaequiposcomputo.controladores;

import jfxsistemaequiposcomputo.pojo.EquipoComputo;
import jfxsistemaequiposcomputo.pojo.Solicitud;
import jfxsistemaequiposcomputo.pojo.SolicitudConUsuarioYEquipo;
import jfxsistemaequiposcomputo.pojo.Usuario;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rodri
 */
public class RegistrarEquipoControllerTest {
    @Test
    public void testValidarSolicitud(){
        System.out.println("validarSolicitud");
        
        Solicitud solicitud = new Solicitud();
        EquipoComputo equipo = new EquipoComputo();
        byte[] imagen = {1, 2, 3, 4};
        Usuario usuario = new Usuario();
        SolicitudConUsuarioYEquipo solicitudUsuarioEquipo = new SolicitudConUsuarioYEquipo();
        
        solicitud.setIdUsuario(2); // Debe ser un ID registrado en la BD
        solicitud.setObservaciones("Prueba");
        
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
        equipo.setIncluyeCargador(true);
        
        usuario.setApellidoMaterno("Prueba");
        usuario.setApellidoPaterno("Prueba");
        usuario.setContrasenia("Prueba");
        usuario.setCorreo("Prueba");
        usuario.setDirección("Prueba");
        usuario.setNombre("Prueba");
        usuario.setPrivilegiado(true);
        usuario.setTelefono("Prueba");
        
        solicitudUsuarioEquipo.setSolicitud(solicitud);
        solicitudUsuarioEquipo.setEquipo(equipo);
        solicitudUsuarioEquipo.setUsuario(usuario);
        
        boolean respuestaEsperada = true;
        boolean respuestaRecibida = RegistrarEquipoController.validarSolicitud(solicitudUsuarioEquipo);
        assertEquals(respuestaEsperada, respuestaRecibida);
    }
}
