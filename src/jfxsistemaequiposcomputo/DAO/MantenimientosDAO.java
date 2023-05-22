    package jfxsistemaequiposcomputo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jfxsistemaequiposcomputo.modelo.ConexionBD;
import jfxsistemaequiposcomputo.utils.Constantes;

/**
 *
 * @author rodri
 */
public class MantenimientosDAO {
    public static int crearMantenimiento(int idDiagnostico){
        int respuesta = Constantes.OPERACION_EXITOSA;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentenciaMantenimiento = "INSERT INTO mantenimientos "
                        + "(idDiagnostico) VALUES (?)";
                PreparedStatement sentenciaPreparada = conexionBD.prepareStatement(sentenciaMantenimiento);
                sentenciaPreparada.setInt(1, idDiagnostico);
                
                int mantenimientosAfectados = sentenciaPreparada.executeUpdate();
                if(mantenimientosAfectados != 1){
                    respuesta = Constantes.ERROR_CONSULTA;
                }
                conexionBD.close();
            }catch(SQLException ex){
                ex.printStackTrace();
                respuesta = Constantes.ERROR_CONSULTA;
            }
        }else{
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
}
