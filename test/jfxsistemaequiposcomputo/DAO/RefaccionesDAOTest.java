package jfxsistemaequiposcomputo.DAO;
import jfxsistemaequiposcomputo.pojo.ListaRefaccionesRespuesta;
import jfxsistemaequiposcomputo.utils.Constantes;
import org.junit.Assert;
import org.junit.Test;

public class RefaccionesDAOTest {
    @Test
    public void testAsociarRefaccionMantenimiento() {
        System.out.println("Asociado de refacción a mantenimiento");
        
        /* Ambos parámetros deben ser ID de registros dentro de la  BD, el 
         * primero de un mantenimiento, el segundo de una refacción
         */
        int codigoRespuesta = 
            RefaccionesDAO.asociarRefaccionMantenimiento(1, 12);
        int codigoEsperado = Constantes.OPERACION_EXITOSA;
        
        Assert.assertEquals(codigoEsperado, codigoRespuesta);
    }
    
    @Test
    public void testObtenerRefacciones() {
        System.out.println("Obtener refacciones por tipo de refacción");
        
        /* El parámetro debe ser un id válido de un tipo de refacción registrado
         * en BD
         */
        ListaRefaccionesRespuesta respuesta = 
            RefaccionesDAO.obtenerRefacciones(1);
        int codigoRespuesta = respuesta.getCodigoRespuesta();
        int codigoEsperado = Constantes.OPERACION_EXITOSA;
        
        Assert.assertEquals(codigoEsperado, codigoRespuesta);
    }
    
    @Test
    public void testRecuperarRefaccionesDeMantenimiento() {
        System.out.println("Recuperar refacciones de mantenimiento");
        
        /* El parámetro debe ser un id válido de un mantenimiento registrado
         * en BD
         */
        ListaRefaccionesRespuesta respuesta = 
            RefaccionesDAO.recuperarRefaccionesMantenimiento(1);
        int codigoRespuesta = respuesta.getCodigoRespuesta();
        int codigoEsperado = Constantes.OPERACION_EXITOSA;
        
        Assert.assertEquals(codigoEsperado, codigoRespuesta);
    }

    @Test
    public void testRecuperarRefaccionesMantenimiento() {
        System.out.println("recuperarRefaccionesMantenimiento");
        int idMantenimiento = 0;
        ListaRefaccionesRespuesta result = RefaccionesDAO.recuperarRefaccionesMantenimiento(idMantenimiento);
        Assert.assertEquals(Constantes.OPERACION_EXITOSA, result.getCodigoRespuesta());
    }
}
