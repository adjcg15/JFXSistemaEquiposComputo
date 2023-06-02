package jfxsistemaequiposcomputo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jfxsistemaequiposcomputo.modelo.ConexionBD;
import jfxsistemaequiposcomputo.pojo.EquipoComputo;
import jfxsistemaequiposcomputo.utils.Constantes;
import jfxsistemaequiposcomputo.utils.Utilidades;

public class EquiposComputoDAO {
    public static int asociarSolicitudEquipo(EquipoComputo nuevoEquipo) {
        int codigoRespuesta = Constantes.OPERACION_EXITOSA;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String crearEquipo = "INSERT INTO equiposdecomputo "
                    + "(tipo, incluyeCargador, modelo, sistemaOperativo, "
                    + "tamanioPantalla, contraseniaEquipo, procesador, "
                    + "memoria, marca, fechaRegistro, fotoEquipo, "
                    + "usuarioSO, idUsuario, idSolicitudDiagnostico) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                
                PreparedStatement crearEquipoSentenciaPreparada = 
                    conexionBD.prepareStatement(crearEquipo);
                crearEquipoSentenciaPreparada.setString(1, nuevoEquipo.getTipo());
                crearEquipoSentenciaPreparada.setBoolean(2, nuevoEquipo.isIncluyeCargador());
                crearEquipoSentenciaPreparada.setString(3, nuevoEquipo.getModelo());
                crearEquipoSentenciaPreparada.setString(4, nuevoEquipo.getSistemaOperativo());
                crearEquipoSentenciaPreparada.setFloat(5, nuevoEquipo.getTamanioPantalla());
                crearEquipoSentenciaPreparada.setString(6, nuevoEquipo.getContraseniaSO());
                crearEquipoSentenciaPreparada.setString(7, nuevoEquipo.getProcesador());
                crearEquipoSentenciaPreparada.setInt(8, nuevoEquipo.getMemoriaRAM());
                crearEquipoSentenciaPreparada.setString(9, nuevoEquipo.getMarca());
                crearEquipoSentenciaPreparada.setString(10, Utilidades.fechaActualFormatoMySQL());
                crearEquipoSentenciaPreparada.setBytes(11, nuevoEquipo.getImagen());
                crearEquipoSentenciaPreparada.setString(12, nuevoEquipo.getUsuarioSO());
                crearEquipoSentenciaPreparada.setInt(13, nuevoEquipo.getIdUsuario());
                crearEquipoSentenciaPreparada.setInt(14, nuevoEquipo.getIdSolicitud());
                
                int equiposAfectados 
                    = crearEquipoSentenciaPreparada.executeUpdate();
                if(equiposAfectados != 1) {
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
    public static int crearEquipoComputo(EquipoComputo nuevoEquipo, int idUsuario, int idSolicitudDiagnostico){
        int respuesta; 
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String crearNuevoEquipo  = "INSERT INTO equiposdecomputo "
                    + "(tipo, incluyeCargador, modelo, sistemaOperativo, "
                    + "tamanioPantalla, contraseniaEquipo, procesador, "
                    + "memoria, marca, fechaRegistro, fotoEquipo, "
                    + "usuarioSO, idUsuario, idSolicitudDiagnostico) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                
                PreparedStatement nuevoEquipoSentencia = conexionBD.prepareStatement(crearNuevoEquipo);
                nuevoEquipoSentencia.setString(1, nuevoEquipo.getTipo());
                nuevoEquipoSentencia.setBoolean(2, nuevoEquipo.isIncluyeCargador());
                nuevoEquipoSentencia.setString(3, nuevoEquipo.getModelo());
                nuevoEquipoSentencia.setString(4, nuevoEquipo.getSistemaOperativo());
                nuevoEquipoSentencia.setFloat(5, nuevoEquipo.getTamanioPantalla());
                nuevoEquipoSentencia.setString(6, nuevoEquipo.getContraseniaSO());
                nuevoEquipoSentencia.setString(7, nuevoEquipo.getProcesador());
                nuevoEquipoSentencia.setInt(8, nuevoEquipo.getMemoriaRAM());
                nuevoEquipoSentencia.setString(9, nuevoEquipo.getMarca());
                nuevoEquipoSentencia.setString(10, nuevoEquipo.getFechaRegistro());
                nuevoEquipoSentencia.setBytes(11, nuevoEquipo.getImagen());
                nuevoEquipoSentencia.setString(12, nuevoEquipo.getUsuarioSO());
                nuevoEquipoSentencia.setInt(13, idUsuario);
                nuevoEquipoSentencia.setInt(14, idSolicitudDiagnostico);
                int filasAfectadas = nuevoEquipoSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
                conexionBD.close();
                
                
            }catch (SQLException e){
                e.printStackTrace();
                respuesta = Constantes.ERROR_CONSULTA;
            }
        }else{
            respuesta = Constantes.ERROR_CONEXION;
            
        }
        System.out.println(respuesta);
        return respuesta;
    }     
 }




