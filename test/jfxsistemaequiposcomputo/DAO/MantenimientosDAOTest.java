package jfxsistemaequiposcomputo.DAO;

import jfxsistemaequiposcomputo.pojo.ListaMantenimientosRespuesta;
import jfxsistemaequiposcomputo.utils.Constantes;
import org.junit.Assert;
import org.junit.Test;

public class MantenimientosDAOTest {
    @Test
    public void testRecuperarMantenimientos() {
        System.out.println("Recuperado de mantenimientos");
        
        ListaMantenimientosRespuesta respuesta = 
            MantenimientosDAO.recuperarMantenimientosConEquipoYDiagnostico();
        int codigoRespuesta = respuesta.getCodigoRespuesta();
        int codigoEsperado = Constantes.OPERACION_EXITOSA;
        
        Assert.assertEquals(codigoEsperado, codigoRespuesta);
    }
    
    @Test
    public void testGuardarComentario() {
        System.out.println("Guardado de comentario");
        
        /* El primer parámetro debe apuntar al id de un diagnóstico 
         * que tiene un mantenimiento asociado en la BD
         */
        int codigoRespuesta = 
            MantenimientosDAO.guardarComentario(7, "hola mundo");
        int codigoEsperado = Constantes.OPERACION_EXITOSA;
        
        Assert.assertEquals(codigoEsperado, codigoRespuesta);
    }
}
