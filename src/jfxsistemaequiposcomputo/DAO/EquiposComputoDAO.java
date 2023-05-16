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
                crearEquipoSentenciaPreparada.setString(5, nuevoEquipo.getTamanioPantalla());
                crearEquipoSentenciaPreparada.setString(6, nuevoEquipo.getContraseniaSO());
                crearEquipoSentenciaPreparada.setString(7, nuevoEquipo.getProcesador());
                crearEquipoSentenciaPreparada.setString(8, nuevoEquipo.getMemoriaRAM());
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
}
