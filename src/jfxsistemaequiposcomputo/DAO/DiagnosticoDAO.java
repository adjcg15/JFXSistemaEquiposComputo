package jfxsistemaequiposcomputo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jfxsistemaequiposcomputo.modelo.ConexionBD;
import jfxsistemaequiposcomputo.pojo.Diagnostico;
import jfxsistemaequiposcomputo.utils.Constantes;
import jfxsistemaequiposcomputo.utils.Utilidades;

/**
 *
 * @author rodri
 */
public class DiagnosticoDAO {
    public static int crearDiagnostico(Diagnostico diagnostico){
        int respuesta = Constantes.OPERACION_EXITOSA;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String crearDiagnostico = "INSERT INTO diagnosticos (tipoDeMantenimiento, "
                        + "fechaAtencion, diagnosticoPreliminar, fechaSolicitud, "
                        + "costoEstimado, propuestaSolucion, idSolicitudDiagnostico)"
                        + " VALUES (?, ?, ?, ?, ?, ?, ?);";
                PreparedStatement crearDiagnosticoSentencia = conexionBD.prepareStatement(crearDiagnostico);
                
                crearDiagnosticoSentencia.setString(1, diagnostico.getTipoDeMantenimiento());
                crearDiagnosticoSentencia.setString(2, diagnostico.getFechaAtencion());
                crearDiagnosticoSentencia.setString(3, diagnostico.getDiagnosticoPreliminar());
                crearDiagnosticoSentencia.setString(4, diagnostico.getFechaSolicitud());
                crearDiagnosticoSentencia.setFloat(5, diagnostico.getCostoEstimado());
                crearDiagnosticoSentencia.setString(6, diagnostico.getPropuestaSolucion());
                crearDiagnosticoSentencia.setInt(7, diagnostico.getIdSolicitudDiagnostico());
               
                int diagnosticosAfectados = crearDiagnosticoSentencia.executeUpdate();
                if(diagnosticosAfectados != 1) {
                    respuesta = Constantes.ERROR_CONSULTA;
                }
                
                int respuestaActualizacion = 
                        SolicitudesDAO.actualizarEstadoSolicitud(Constantes.ESTADO_SOLICITUD_ACEPTADA, 
                                diagnostico.getIdSolicitudDiagnostico());
                if(respuestaActualizacion != Constantes.OPERACION_EXITOSA){
                    return respuestaActualizacion;
                }
                
                conexionBD.close();
            }catch(SQLException ex){
                ex.printStackTrace();
                System.out.println(ex.getMessage());
                respuesta = Constantes.ERROR_CONSULTA;
            }
        }else{
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
}
