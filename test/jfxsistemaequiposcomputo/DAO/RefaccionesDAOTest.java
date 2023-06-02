/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package jfxsistemaequiposcomputo.DAO;

import jfxsistemaequiposcomputo.pojo.ListaRefaccionesRespuesta;
import jfxsistemaequiposcomputo.utils.Constantes;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dnava
 */
public class RefaccionesDAOTest {
    
    public RefaccionesDAOTest() {
    }

    @Test
    public void testRecuperarRefaccionesMantenimiento() {
        System.out.println("recuperarRefaccionesMantenimiento");
        int idMantenimiento = 0;
        ListaRefaccionesRespuesta result = RefaccionesDAO.recuperarRefaccionesMantenimiento(idMantenimiento);
        assertEquals(Constantes.OPERACION_EXITOSA, result.getCodigoRespuesta());
    }
}
