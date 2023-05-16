package jfxsistemaequiposcomputo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jfxsistemaequiposcomputo.modelo.ConexionBD;
import jfxsistemaequiposcomputo.utils.Constantes;
import jfxsistemaequiposcomputo.utils.Utilidades;

public class EstadosDAO {
    public static int obtenerIdEstadoPorNombre(String nombreEstado) {
        int idEstado = -1;
        
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String obtenerIdEstado = "SELECT idEstado FROM estados "
                    + "WHERE nombre = ?";
                
                PreparedStatement obtenerIdEstadoSentenciaPreparada = 
                    conexionBD.prepareStatement(obtenerIdEstado);
                obtenerIdEstadoSentenciaPreparada.setString(
                    1, 
                    nombreEstado
                );
                
                ResultSet estadoRecuperado = obtenerIdEstadoSentenciaPreparada
                    .executeQuery();
                if(estadoRecuperado.next()) {
                    idEstado = estadoRecuperado.getInt("idEstado");
                }
                
                conexionBD.close();
            }catch(SQLException e){
                System.out.println(e.getMessage());
                idEstado = -1;
            }
        } else {
            idEstado = -1;
        }
        
        return idEstado;
    }
    
    public static int asociarEstadoSolicitud(int idSolicitud, int idEstado) {
        int codigoRespuesta = Constantes.OPERACION_EXITOSA;
        
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String crearEstadoSolicitud = "INSERT INTO solicitudestados "
                    + "(fechaInicio, activo, idSolicitudDiagnostico, idEstado) "
                    + "VALUES (?, ?, ?, ?)";
                
                PreparedStatement asociarEstadoSentenciaPreparada = 
                    conexionBD.prepareStatement(crearEstadoSolicitud);
                asociarEstadoSentenciaPreparada.setString(
                    1, 
                    Utilidades.fechaActualFormatoMySQL()
                );
                asociarEstadoSentenciaPreparada.setBoolean(2, true);
                asociarEstadoSentenciaPreparada.setInt(3, idSolicitud);
                asociarEstadoSentenciaPreparada.setInt(4, idEstado);
                
                int estadosSolicitudAfectados 
                    = asociarEstadoSentenciaPreparada.executeUpdate();
                if(estadosSolicitudAfectados != 1) {
                    codigoRespuesta = Constantes.ERROR_CONSULTA;
                }
                
                conexionBD.close();
            }catch(SQLException e){
                System.out.println(e.getMessage());
                codigoRespuesta = Constantes.ERROR_CONSULTA;
            }
        } else {
            codigoRespuesta = Constantes.ERROR_CONEXION;
        }
        
        return codigoRespuesta;
    }
}
