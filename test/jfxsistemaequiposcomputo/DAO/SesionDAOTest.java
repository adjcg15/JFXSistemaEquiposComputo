package jfxsistemaequiposcomputo.DAO;

import jfxsistemaequiposcomputo.pojo.UsuarioRespuesta;
import jfxsistemaequiposcomputo.utils.Constantes;
import org.junit.Test;
import static org.junit.Assert.*;

public class SesionDAOTest {
    @Test
    public void testVerificarUsuarioSesion() {
        System.out.println("verificarUsuarioSesion");
        String correo = "";
        String password = "";
        UsuarioRespuesta usuarioEsperado = new UsuarioRespuesta();
        usuarioEsperado.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        UsuarioRespuesta usuarioRespuesta = SesionDAO.verificarUsuarioSesion(correo, password);
        assertEquals(usuarioEsperado.getCodigoRespuesta(), usuarioRespuesta.getCodigoRespuesta());
    }
}
