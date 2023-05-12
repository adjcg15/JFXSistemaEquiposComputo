package jfxsistemaequiposcomputo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jfxsistemaequiposcomputo.modelo.ConexionBD;
import jfxsistemaequiposcomputo.pojo.SolicitudMantenimiento;
import jfxsistemaequiposcomputo.utils.Constantes;
import jfxsistemaequiposcomputo.utils.Utilidades;
public class SolicitudesDAO {
    public static int crearSolicitud(SolicitudMantenimiento solicitud){
        int respuesta = Constantes.OPERACION_EXITOSA;
        
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                //CREAR SOLICITUD DE DIAGNÓSTICO
                String crearSolicitud = "INSERT INTO solicitudesdiagnostico "
                    + "(observaciones, idUsuario) "
                    + "VALUES (?, ?)";
                
                PreparedStatement crearSolicitudSentenciaPreparada = 
                    conexionBD.prepareStatement(crearSolicitud);
                crearSolicitudSentenciaPreparada.setString(1, solicitud.getObservaciones());
                crearSolicitudSentenciaPreparada.setInt(2, solicitud.getIdUsuario());
                
                int solicitudesAfectadas = crearSolicitudSentenciaPreparada.executeUpdate();
                if(solicitudesAfectadas != 1) {
                    respuesta = Constantes.ERROR_CONSULTA;
                    conexionBD.close();
                    return respuesta;
                }
                
                //OBTENER ID DE NUEVA SOLICITUD
                String obtenerIdSolicitud = "SELECT idSolicitudDiagnostico "
                    + "FROM solicitudesdiagnostico "
                    + "ORDER BY idSolicitudDiagnostico DESC LIMIT 1";
                
                PreparedStatement obtenerIdSolicitudSentenciaPreparada = 
                    conexionBD.prepareStatement(obtenerIdSolicitud);
                
                ResultSet solicitudRecuperada 
                    = obtenerIdSolicitudSentenciaPreparada.executeQuery();
                int idNuevaSolicitud;
                if(solicitudRecuperada.next()) {
                    idNuevaSolicitud = solicitudRecuperada
                        .getInt("idSolicitudDiagnostico");
                } else {
                    respuesta = Constantes.ERROR_CONSULTA;
                    conexionBD.close();
                    return respuesta;
                }
                
                //OBTENER ID DE ESTADO INICIAL
                String obtenerIdEstado = "SELECT idEstado FROM estados "
                    + "WHERE nombre = ?";
                
                PreparedStatement obtenerIdEstadoSentenciaPreparada = 
                    conexionBD.prepareStatement(obtenerIdEstado);
                obtenerIdEstadoSentenciaPreparada.setString(
                    1, 
                    Constantes.ESTADO_SOLICITUD_PENDIENTE
                );
                
                ResultSet estadoRecuperado = obtenerIdEstadoSentenciaPreparada
                    .executeQuery();
                int idEstadoAsociado;
                if(estadoRecuperado.next()) {
                    idEstadoAsociado = estadoRecuperado.getInt("idEstado");
                } else {
                    respuesta = Constantes.ERROR_CONSULTA;
                    conexionBD.close();
                    return respuesta;
                }
                
                //CREAR RELACIÓN DE SOLICITUD CON ESTADO
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
                asociarEstadoSentenciaPreparada.setInt(3, idNuevaSolicitud);
                asociarEstadoSentenciaPreparada.setInt(4, idEstadoAsociado);
                
                int estadosSolicitudAfectados 
                    = asociarEstadoSentenciaPreparada.executeUpdate();
                if(estadosSolicitudAfectados != 1) {
                    respuesta = Constantes.ERROR_CONSULTA;
                    conexionBD.close();
                    return respuesta;
                }
                
                //CREAR EQUIPO ASOCIADO
                String crearEquipo = "INSERT INTO equiposdecomputo "
                    + "(tipo, incluyeCargador, modelo, sistemaOperativo, "
                    + "tamanioPantalla, contraseniaEquipo, procesador, "
                    + "memoria, marca, fechaRegistro, fotoEquipo, "
                    + "usuarioSO, idUsuario, idSolicitudDiagnostico) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                
                PreparedStatement crearEquipoSentenciaPreparada = 
                    conexionBD.prepareStatement(crearEquipo);
                crearEquipoSentenciaPreparada.setString(1, solicitud.getTipo());
                crearEquipoSentenciaPreparada.setBoolean(2, solicitud.isIncluyeCargador());
                crearEquipoSentenciaPreparada.setString(3, solicitud.getModelo());
                crearEquipoSentenciaPreparada.setString(4, solicitud.getSistemaOperativo());
                crearEquipoSentenciaPreparada.setString(5, solicitud.getTamanioPantalla());
                crearEquipoSentenciaPreparada.setString(6, solicitud.getContraeniaSO());
                crearEquipoSentenciaPreparada.setString(7, solicitud.getProcesador());
                crearEquipoSentenciaPreparada.setString(8, solicitud.getMemoriaRAM());
                crearEquipoSentenciaPreparada.setString(9, solicitud.getMarca());
                crearEquipoSentenciaPreparada.setString(10, Utilidades.fechaActualFormatoMySQL());
                crearEquipoSentenciaPreparada.setBytes(11, solicitud.getImagen());
                crearEquipoSentenciaPreparada.setString(12, solicitud.getUsuarioSO());
                crearEquipoSentenciaPreparada.setInt(13, solicitud.getIdUsuario());
                crearEquipoSentenciaPreparada.setInt(14, idNuevaSolicitud);
                
                int equiposAfectados 
                    = crearEquipoSentenciaPreparada.executeUpdate();
                if(equiposAfectados != 1) {
                    respuesta = Constantes.ERROR_CONSULTA;
                    conexionBD.close();
                    return respuesta;
                }
                
                conexionBD.close();
            }catch(SQLException e){
                System.out.println(e.getMessage());
                respuesta = Constantes.ERROR_CONSULTA;
            }
        } else {
            respuesta = Constantes.ERROR_CONEXION;
        }
        
        return respuesta;
    }
}
