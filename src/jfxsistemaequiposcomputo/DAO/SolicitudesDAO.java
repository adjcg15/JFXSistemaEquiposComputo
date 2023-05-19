package jfxsistemaequiposcomputo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import jfxsistemaequiposcomputo.modelo.ConexionBD;
import jfxsistemaequiposcomputo.pojo.EquipoComputo;
import jfxsistemaequiposcomputo.pojo.ListaSolicitudesRespuesta;
import jfxsistemaequiposcomputo.pojo.Solicitud;
import jfxsistemaequiposcomputo.pojo.SolicitudConUsuarioYEquipo;
import jfxsistemaequiposcomputo.pojo.Usuario;
import jfxsistemaequiposcomputo.utils.Constantes;
import jfxsistemaequiposcomputo.utils.Utilidades;

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
    
    public static ListaSolicitudesRespuesta recuperarSolicitudesConUsuarioYEquipo() {
        ListaSolicitudesRespuesta listaSolicitudesRespuesta = 
            new ListaSolicitudesRespuesta();
        listaSolicitudesRespuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        
        Connection conexion = ConexionBD.abrirConexionBD();
        if (conexion != null){
            try {
                String consulta 
                    = "SELECT solicitudesdiagnostico.idSolicitudDiagnostico, "
                    + "observaciones, fechaInicio, usuarios.idUsuario, "
                    + "usuarios.nombre, usuarios.apellidoPaterno, "
                    + "usuarios.apellidoMaterno, usuarios.telefono, usuarios.direccion, "
                    + "usuarios.correo, equiposdecomputo.idEquipoDeComputo, "
                    + "equiposdecomputo.tipo, equiposdecomputo.incluyeCargador, "
                    + "equiposdecomputo.modelo, equiposdecomputo.sistemaOperativo, "
                    + "equiposdecomputo.tamanioPantalla, equiposdecomputo.contraseniaEquipo, "
                    + "equiposdecomputo.procesador, equiposdecomputo.memoria, "
                    + "equiposdecomputo.marca, equiposdecomputo.fechaRegistro, "
                    + "equiposdecomputo.fotoEquipo, equiposdecomputo.usuarioSO "
                    + "FROM solicitudesdiagnostico "
                    + "INNER JOIN solicitudestados ON solicitudesdiagnostico.idSolicitudDiagnostico = solicitudestados.idSolicitudEstado "
                    + "INNER JOIN usuarios ON solicitudesdiagnostico.idUsuario = usuarios.idUsuario "
                    + "INNER JOIN equiposdecomputo ON solicitudesdiagnostico.idSolicitudDiagnostico = equiposdecomputo.idSolicitudDiagnostico "
                    + "WHERE solicitudestados.activo = TRUE";
                
                PreparedStatement sentenciaPreparada 
                    = conexion.prepareStatement(consulta);
                ResultSet resultado = sentenciaPreparada.executeQuery();
                
                ArrayList<SolicitudConUsuarioYEquipo> listaSolicitudes 
                    = new ArrayList();
                while(resultado.next()){
                    SolicitudConUsuarioYEquipo solicitudCompleta
                        = new SolicitudConUsuarioYEquipo();
                    
                    Solicitud solicitud = new Solicitud();
                    solicitud.setIdSolicitud(resultado.getInt("idSolicitudDiagnostico"));
                    solicitud.setObservaciones(resultado.getString("observaciones"));
                    solicitud.setFechaInicio(resultado.getString("fechaInicio"));
                    solicitud.setIdUsuario(resultado.getInt("idUsuario"));
                    solicitudCompleta.setSolicitud(solicitud);
                    
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(resultado.getInt("idUsuario"));
                    usuario.setNombre(resultado.getString("nombre"));
                    usuario.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    usuario.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    usuario.setTelefono(resultado.getString("telefono"));
                    usuario.setDirección(resultado.getString("direccion"));
                    usuario.setCorreo(resultado.getString("correo"));
                    solicitudCompleta.setUsuario(usuario);
                    
                    EquipoComputo equipo = new EquipoComputo();
                    equipo.setIdEquipoComputo(resultado.getInt("idEquipoDeComputo"));
                    equipo.setTipo(resultado.getString("tipo"));
                    equipo.setIncluyeCargador(resultado.getBoolean("incluyeCargador"));
                    equipo.setModelo(resultado.getString("modelo"));
                    equipo.setSistemaOperativo(resultado.getString("sistemaOperativo"));
                    equipo.setTamanioPantalla(resultado.getString("tamanioPantalla"));
                    equipo.setContraseniaSO(resultado.getString("contraseniaEquipo"));
                    equipo.setProcesador(resultado.getString("procesador"));
                    equipo.setMemoriaRAM(resultado.getString("memoria"));
                    equipo.setMarca(resultado.getString("marca"));
                    equipo.setFechaRegistro(resultado.getString("fechaRegistro"));
                    equipo.setImagen(resultado.getBytes("fotoEquipo"));
                    equipo.setUsuarioSO(resultado.getString("usuarioSO"));
                    equipo.setIdSolicitud(resultado.getInt("idSolicitudDiagnostico"));
                    equipo.setIdUsuario(resultado.getInt("idUsuario"));
                    solicitudCompleta.setEquipo(equipo);
                    
                    listaSolicitudes.add(solicitudCompleta);
                }
                
                listaSolicitudesRespuesta.setSolicitudesCompletas(listaSolicitudes);
                conexion.close();
            } catch (SQLException ex) {
                listaSolicitudesRespuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA); 
            }          
        }else{
            listaSolicitudesRespuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        
        return listaSolicitudesRespuesta;
    }
    
    public static int actualizarEstadoSolicitud(String nombreEstado, int idSolicitud){
        int respuesta = Constantes.OPERACION_EXITOSA;
        int idSolicitudEstado = recuperarIdSolicitudEstadoActiva(idSolicitud);
        int idEstado = EstadosDAO.obtenerIdEstadoPorNombre(nombreEstado);
        
        int respuestaDesactivar = desactivarSolicitudAnterior(idSolicitudEstado);
        if(respuestaDesactivar != Constantes.OPERACION_EXITOSA){
            return respuestaDesactivar;
        }
        
        return respuesta;
    }
    
    public static int recuperarIdSolicitudEstadoActiva(int idSolicitud){
        int idSolicitudEstado = -1;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "SELECT idsolicitudEstado FROM solicitudestados"
                        + " WHERE activo = true AND idSolicitudDiagnostico = ?;";
                PreparedStatement sentenciaPreparada = conexionBD.prepareStatement(sentencia);
                sentenciaPreparada.setInt(1, idSolicitud);
                ResultSet idRecuperado  = sentenciaPreparada.executeQuery();
                if(idRecuperado.next()){
                    idSolicitudEstado = idRecuperado.getInt("idsolicitudEstado");
                }
                conexionBD.close();
            }catch(SQLException e){
                idSolicitudEstado = -1;
            }
        }else{
            idSolicitudEstado = -1;
        }
        return idSolicitudEstado;
    }
    
    public static int desactivarSolicitudAnterior(int idSolicitudEstado){
        int respuesta = Constantes.OPERACION_EXITOSA;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String desactivarAnterior = "UPDATE solicitudestados SET fechaFin = ?,"
                        + " activo = false WHERE idSOlicitudEstado = ?;";
                PreparedStatement sentenciaPreparada = conexionBD.prepareStatement(desactivarAnterior);
                sentenciaPreparada.setString(1, Utilidades.fechaActualFormatoMySQL());
                sentenciaPreparada.setInt(2, idSolicitudEstado);
                conexionBD.close();
            }catch(SQLException e){
                respuesta = Constantes.ERROR_CONSULTA;
            }
        }else{
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
}
