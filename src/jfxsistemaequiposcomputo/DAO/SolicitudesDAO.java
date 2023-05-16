package jfxsistemaequiposcomputo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import jfxsistemaequiposcomputo.modelo.ConexionBD;
import jfxsistemaequiposcomputo.pojo.Solicitud;
import jfxsistemaequiposcomputo.pojo.SolicitudConUsuarioYEquipo;
import jfxsistemaequiposcomputo.utils.Constantes;

public class SolicitudesDAO {
    public static int crearSolicitud(Solicitud solicitud) {
        int idSolicitud = -1;
        
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null) {
            try{
                String crearSolicitud = "INSERT INTO solicitudesdiagnostico "
                    + "(observaciones, idUsuario) "
                    + "VALUES (?, ?)";
                
                PreparedStatement crearSolicitudSentenciaPreparada = 
                    conexionBD.prepareStatement(
                        crearSolicitud, 
                        Statement.RETURN_GENERATED_KEYS
                    );
                crearSolicitudSentenciaPreparada.setString(1, solicitud.getObservaciones());
                crearSolicitudSentenciaPreparada.setInt(2, solicitud.getIdUsuario());
                
                int solicitudesAfectadas = crearSolicitudSentenciaPreparada.executeUpdate();
                if(solicitudesAfectadas != 1) {
                    conexionBD.close();
                    return idSolicitud;
                }
                
                ResultSet resultadoGuardado = 
                    crearSolicitudSentenciaPreparada.getGeneratedKeys();
                if (resultadoGuardado.next()) {
                    idSolicitud = resultadoGuardado.getInt(1);
                }
                
                conexionBD.close();
            }catch(SQLException e){
                System.out.println(e.getMessage());
                idSolicitud = -1;
            }
        } else {
            idSolicitud = -1;
        }
        
        return idSolicitud;
    }
    
    public static int crearSolicitudConUsuarioYEquipo(SolicitudConUsuarioYEquipo solicitudCompleta) {
        int respuesta = Constantes.OPERACION_EXITOSA;
        
        int idNuevaSolicitud = crearSolicitud(solicitudCompleta.getSolicitud());
        if(idNuevaSolicitud < 0) {
            respuesta = Constantes.ERROR_CONEXION;
            return respuesta;
        }
        solicitudCompleta.getSolicitud().setIdSolicitud(idNuevaSolicitud);
        
        int idEstadoInicial = EstadosDAO.obtenerIdEstadoPorNombre(
            Constantes.ESTADO_SOLICITUD_PENDIENTE
        );
        if(idEstadoInicial < 0) {
            respuesta = Constantes.ERROR_CONEXION;
            return respuesta;
        }
        
        int codigoRespuestaAsociacion = EstadosDAO.asociarEstadoSolicitud(
            solicitudCompleta.getSolicitud().getIdSolicitud(), 
            idEstadoInicial
        );
        if(codigoRespuestaAsociacion != Constantes.OPERACION_EXITOSA) {
            return codigoRespuestaAsociacion;
        }
        
        solicitudCompleta
            .getEquipo()
            .setIdSolicitud(solicitudCompleta.getSolicitud().getIdSolicitud());
        solicitudCompleta.getEquipo().setIdUsuario(
            solicitudCompleta.getUsuario().getIdUsuario()
        );
        int codigoRespuestaRegistroEquipo = EquiposComputoDAO.asociarSolicitudEquipo(
            solicitudCompleta.getEquipo()
        );
        if(codigoRespuestaRegistroEquipo != Constantes.OPERACION_EXITOSA) {
            return codigoRespuestaRegistroEquipo;
        }
        
        return respuesta;
    }
}
