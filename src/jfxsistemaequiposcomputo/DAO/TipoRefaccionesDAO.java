package jfxsistemaequiposcomputo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import jfxsistemaequiposcomputo.modelo.ConexionBD;
import jfxsistemaequiposcomputo.pojo.ListaTipoRefaccionesRespuesta;
import jfxsistemaequiposcomputo.pojo.TipoRefaccion;
import jfxsistemaequiposcomputo.utils.Constantes;

/**
 *
 * @author rodri
 */
public class TipoRefaccionesDAO {
       public static ListaTipoRefaccionesRespuesta obtenerTipoRefacciones(){
       ListaTipoRefaccionesRespuesta tipoRefaccionesRespuesta = new ListaTipoRefaccionesRespuesta();
       tipoRefaccionesRespuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
       
       Connection conexionBD = ConexionBD.abrirConexionBD();
       if(conexionBD != null){
           try{
                String consultaTipoRefacciones = "SELECT idTipoRefaccion, nombre FROM tiporefacciones";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consultaTipoRefacciones);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<TipoRefaccion> tipoRefaccionConsulta = new ArrayList();
                while(resultado.next()){
                    TipoRefaccion tipoRefaccion = new TipoRefaccion();
                    tipoRefaccion.setIdTipoRefaccion(resultado.getInt("idTipoRefaccion"));
                    tipoRefaccion.setNombre(resultado.getString("nombre"));
                    tipoRefaccionConsulta.add(tipoRefaccion);
                }
                tipoRefaccionesRespuesta.setTipoRefacciones(tipoRefaccionConsulta);
                conexionBD.close();
           }catch(SQLException ex){
               ex.printStackTrace();
               tipoRefaccionesRespuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
           }
       }else{
           tipoRefaccionesRespuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
       }
       return tipoRefaccionesRespuesta;
   }
}
