    package jfxsistemaequiposcomputo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import jfxsistemaequiposcomputo.modelo.ConexionBD;
import jfxsistemaequiposcomputo.pojo.Diagnostico;
import jfxsistemaequiposcomputo.pojo.EquipoComputo;
import jfxsistemaequiposcomputo.pojo.ListaMantenimientosRespuesta;
import jfxsistemaequiposcomputo.pojo.Mantenimiento;
import jfxsistemaequiposcomputo.pojo.MantenimientoConEquipoYDiagnostico;
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
    
    public static ListaMantenimientosRespuesta recuperarMantenimientosConEquipoYDiagnostico() {
        ListaMantenimientosRespuesta listaMantenimientosRespuesta = 
            new ListaMantenimientosRespuesta();
        listaMantenimientosRespuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        
        Connection conexion = ConexionBD.abrirConexionBD();
        if (conexion != null){
            try {
                String consulta 
                    = "SELECT mantenimientos.idMantenimiento, mantenimientos.comentario, "
                    + "diagnosticos.idDiagnostico, diagnosticos.tipoDeMantenimiento, "
                    + "diagnosticos.diagnosticoPreliminar, diagnosticos.propuestaSolucion, "
                    + "ultimosequipos.idEquipoDeComputo, ultimosequipos.fotoEquipo, "
                    + "ultimosequipos.modelo "
                    + "FROM mantenimientos "
                    + "INNER JOIN diagnosticos ON mantenimientos.idDiagnostico = diagnosticos.idDiagnostico "
                    + "INNER JOIN ( "
                    + "SELECT idSolicitudDiagnostico, modelo, fotoEquipo, MAX(idEquipoDeComputo) AS idEquipoDeComputo "
                    + "FROM equiposdecomputo GROUP BY idSolicitudDiagnostico "
                    + ") AS ultimosequipos "
                    + "ON diagnosticos.idSolicitudDiagnostico = ultimosequipos.idSolicitudDiagnostico";
                
                PreparedStatement sentenciaPreparada 
                    = conexion.prepareStatement(consulta);
                ResultSet resultado = sentenciaPreparada.executeQuery();
                
                ArrayList<MantenimientoConEquipoYDiagnostico> listaMantenimientos 
                    = new ArrayList();
                while(resultado.next()){
                    MantenimientoConEquipoYDiagnostico mantenimientoCompleto
                        = new MantenimientoConEquipoYDiagnostico();
                    
                    Mantenimiento mantenimiento = new Mantenimiento();
                    mantenimiento.setIdMantenimiento(resultado.getInt("idMantenimiento"));
                    mantenimiento.setComentario(resultado.getString("comentario"));
                    mantenimiento.setIdDiagnostico(resultado.getInt("idDiagnostico"));
                    mantenimientoCompleto.setMantenimiento(mantenimiento);
                    
                    Diagnostico diagnostico = new Diagnostico();
                    diagnostico.setIdDiagnostico(resultado.getInt("idDiagnostico"));
                    diagnostico.setTipoDeMantenimiento(resultado.getString("tipoDeMantenimiento"));
                    diagnostico.setDiagnosticoPreliminar(resultado.getString("diagnosticoPreliminar"));
                    diagnostico.setPropuestaSolucion(resultado.getString("propuestaSolucion"));
                    mantenimientoCompleto.setDiagnostico(diagnostico);
                    
                    EquipoComputo equipo = new EquipoComputo();
                    equipo.setIdEquipoComputo(resultado.getInt("idEquipoDeComputo"));
                    equipo.setImagen(resultado.getBytes("fotoEquipo"));
                    equipo.setModelo(resultado.getString("modelo"));
                    mantenimientoCompleto.setEquipo(equipo);
                    
                    listaMantenimientos.add(mantenimientoCompleto);
                }
                
                System.out.println(listaMantenimientos.size());
                listaMantenimientosRespuesta
                    .setMantenimientosCompletos(listaMantenimientos);
                conexion.close();
                System.out.println("TODO OOOOOOOOOOOK");
            } catch (SQLException ex) {
                System.err.println("ALGO SALIÓ MUY MAL!!!");
                System.err.println(ex.getMessage());
                listaMantenimientosRespuesta
                    .setCodigoRespuesta(Constantes.ERROR_CONSULTA); 
            }          
        }else{
            listaMantenimientosRespuesta
                .setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        
        return listaMantenimientosRespuesta;
    }
}
