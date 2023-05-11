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
                
                PreparedStatement crearSolicitudSentendiaPreparada = 
                    conexionBD.prepareStatement(crearSolicitud);
                crearSolicitudSentendiaPreparada.setString(1, solicitud.getObservaciones());
                crearSolicitudSentendiaPreparada.setInt(2, solicitud.getIdUsuario());
                
                int solicitudesAfectadas = crearSolicitudSentendiaPreparada.executeUpdate();
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
                
                PreparedStatement asociarEstadoSentendiaPreparada = 
                    conexionBD.prepareStatement(crearEstadoSolicitud);
                asociarEstadoSentendiaPreparada.setString(
                    1, 
                    Utilidades.fechaActualFormatoMySQL()
                );
                asociarEstadoSentendiaPreparada.setBoolean(2, true);
                asociarEstadoSentendiaPreparada.setInt(3, idNuevaSolicitud);
                asociarEstadoSentendiaPreparada.setInt(4, idEstadoAsociado);
                
                int estadosSolicitudAfectados 
                    = crearSolicitudSentendiaPreparada.executeUpdate();
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
                
                PreparedStatement crearEquipoSentendiaPreparada = 
                    conexionBD.prepareStatement(crearEquipo);
                crearEquipoSentendiaPreparada.setString(1, solicitud.getTipo());
                crearEquipoSentendiaPreparada.setBoolean(2, solicitud.isIncluyeCargador());
                crearEquipoSentendiaPreparada.setString(3, solicitud.getModelo());
                crearEquipoSentendiaPreparada.setString(4, solicitud.getSistemaOperativo());
                crearEquipoSentendiaPreparada.setString(5, solicitud.getTamanioPantalla());
                crearEquipoSentendiaPreparada.setString(6, solicitud.getContraeniaSO());
                crearEquipoSentendiaPreparada.setString(7, solicitud.getProcesador());
                crearEquipoSentendiaPreparada.setString(8, solicitud.getMemoriaRAM());
                crearEquipoSentendiaPreparada.setString(9, solicitud.getMarca());
                crearEquipoSentendiaPreparada.setString(10, Utilidades.fechaActualFormatoMySQL());
                crearEquipoSentendiaPreparada.setBytes(11, solicitud.getImagen());
                crearEquipoSentendiaPreparada.setString(12, solicitud.getUsuarioSO());
                crearEquipoSentendiaPreparada.setInt(13, solicitud.getIdUsuario());
                crearEquipoSentendiaPreparada.setInt(14, idNuevaSolicitud);
                
                int equiposAfectados 
                    = crearEquipoSentendiaPreparada.executeUpdate();
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
