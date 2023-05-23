package jfxsistemaequiposcomputo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import jfxsistemaequiposcomputo.modelo.ConexionBD;
import jfxsistemaequiposcomputo.pojo.ListaRefaccionesRespuesta;
import jfxsistemaequiposcomputo.pojo.Refaccion;
import jfxsistemaequiposcomputo.utils.Constantes;

public class RefaccionesDAO {
    public static ListaRefaccionesRespuesta recuperarRefaccionesMantenimiento(int idMantenimiento) {
        ListaRefaccionesRespuesta refaccionesRespuesta = new ListaRefaccionesRespuesta();
        refaccionesRespuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = 
                    "SELECT refaccionesmantenimientos.idRefaccion, "
                    + "refacciones.nombre, refacciones.idTipoRefaccion, "
                    + "tipoRefacciones.nombre AS tipoRefaccion "
                    + "FROM refaccionesmantenimientos "
                    + "INNER JOIN refacciones "
                    + "ON refaccionesmantenimientos.idRefaccion = refacciones.idRefaccion "
                    + "INNER JOIN tiporefacciones "
                    + "ON refacciones.idTipoRefaccion = tiporefacciones.idTipoRefaccion "
                    + "WHERE idMantenimiento = ?";
                
                PreparedStatement sentenciaPreparada = 
                    conexionBD.prepareStatement(consulta);
                sentenciaPreparada.setInt(1, idMantenimiento);
                
                ResultSet respuesta = sentenciaPreparada.executeQuery();
                
                ArrayList<Refaccion> refacciones = new ArrayList();
                while(respuesta.next()) {
                    Refaccion refaccion = new Refaccion();
                    
                   refaccion.setIdRefaccion(respuesta.getInt("idRefaccion"));
                   refaccion.setNombre(respuesta.getString("nombre"));
                   refaccion.setIdTipoRefaccion(respuesta.getInt("idTipoRefaccion"));
                   refaccion.setTipoRefaccion(respuesta.getString("tipoRefaccion"));
                   
                   refacciones.add(refaccion);
                }
                
                refaccionesRespuesta.setRefacciones(refacciones);
                conexionBD.close();
            }catch(SQLException e){
                System.out.println(e.getMessage());
                refaccionesRespuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            refaccionesRespuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        
        return refaccionesRespuesta;
    }
}
