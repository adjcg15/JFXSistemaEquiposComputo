package jfxsistemaequiposcomputo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import jfxsistemaequiposcomputo.modelo.ConexionBD;
import jfxsistemaequiposcomputo.pojo.Estado;
import jfxsistemaequiposcomputo.pojo.ListaEstadosRespuesta;
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
    
    public static int asociarEstadoSolicitudAceptadaRechazada(int idSolicitud, int idEstado){
        int codigoRespuesta = Constantes.OPERACION_EXITOSA;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String crearEstadoSolicitud = "INSERT INTO solicitudestados "
                    + "(fechaInicio, fechaFin, activo, idSolicitudDiagnostico, idEstado) "
                    + "VALUES (?, ?, ?, ?, ?)";
                
                PreparedStatement asociarEstadoSentenciaPreparada = 
                    conexionBD.prepareStatement(crearEstadoSolicitud);
                asociarEstadoSentenciaPreparada.setString(
                    1, 
                    Utilidades.fechaActualFormatoMySQL()
                );
                asociarEstadoSentenciaPreparada.setString(
                    2, 
                    Utilidades.fechaActualFormatoMySQL()
                );
                asociarEstadoSentenciaPreparada.setBoolean(3, true);
                asociarEstadoSentenciaPreparada.setInt(4, idSolicitud);
                asociarEstadoSentenciaPreparada.setInt(5, idEstado);
                
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
    
    public static int asociarEstadoSolicitudEnDiagnostico(String fechaIncio, int idSolicitud, String nombreEstado) {
        int codigoRespuesta = Constantes.OPERACION_EXITOSA;
        int idEstado = obtenerIdEstadoPorNombre(nombreEstado);
        
        int idSolicitudEstado = SolicitudesDAO.recuperarIdSolicitudEstadoActiva(idSolicitud);
        int respuestaDesactivar = SolicitudesDAO.desactivarSolicitudAnterior(idSolicitudEstado);
        if(respuestaDesactivar != Constantes.OPERACION_EXITOSA){
            return respuestaDesactivar;
        }
        
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String crearEstadoSolicitud = "INSERT INTO solicitudestados "
                    + "(fechaInicio, activo, idSolicitudDiagnostico, idEstado) "
                    + "VALUES (?, ?, ?, ?)";
                
                PreparedStatement asociarEstadoSentenciaPreparada = 
                    conexionBD.prepareStatement(crearEstadoSolicitud);
                asociarEstadoSentenciaPreparada.setString(1, fechaIncio);
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
    
    public static ListaEstadosRespuesta recuperarEstadosMantenimiento(int idSolicitudDiagnostico) {
        ListaEstadosRespuesta estadosRespuesta = new ListaEstadosRespuesta();
        estadosRespuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = 
                    "SELECT estados.idEstado, fechaInicio, fechaFin, activo, nombre "
                    + "FROM solicitudestados "
                    + "INNER JOIN estados ON estados.idEstado = solicitudestados.idEstado "
                    + "WHERE idSolicitudDiagnostico = ?";
                
                PreparedStatement sentenciaPreparada = 
                    conexionBD.prepareStatement(consulta);
                sentenciaPreparada.setInt(1, idSolicitudDiagnostico);
                
                ResultSet respuesta = sentenciaPreparada.executeQuery();
                
                ArrayList<Estado> estados = new ArrayList();
                while(respuesta.next()) {
                    Estado estado = new Estado();
                    
                   estado.setIdEstado(respuesta.getInt("idEstado"));
                   estado.setFechaInicio(respuesta.getString("fechaInicio"));
                   estado.setFechaFin(respuesta.getString("fechaFin"));
                   estado.setNombre(respuesta.getString("nombre"));
                   estado.setActivo(respuesta.getBoolean("activo"));
                   
                   estados.add(estado);
                }
                
                estadosRespuesta.setEstados(estados);
                conexionBD.close();
            }catch(SQLException e){
                System.out.println(e.getMessage());
                estadosRespuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            estadosRespuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        
        return estadosRespuesta;
    }
}
