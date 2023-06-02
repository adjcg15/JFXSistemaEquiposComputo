package jfxsistemaequiposcomputo.DAO;
import jfxsistemaequiposcomputo.pojo.ListaTipoRefaccionesRespuesta;
import jfxsistemaequiposcomputo.utils.Constantes;
import org.junit.Assert;
import org.junit.Test;

public class TipoRefaccionesDAOTest {
    @Test
    public void testObtenerListaTiposRefacciones() {
        System.out.println("Recuperado de tipos de refacciones");
        
        ListaTipoRefaccionesRespuesta respuesta = 
            TipoRefaccionesDAO.obtenerTipoRefacciones();
        int codigoRespuesta = respuesta.getCodigoRespuesta();
        int codigoEsperado = Constantes.OPERACION_EXITOSA;
        
        Assert.assertEquals(codigoEsperado, codigoRespuesta);
    }
}
