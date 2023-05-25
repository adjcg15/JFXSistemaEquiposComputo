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
    
   public static ListaRefaccionesRespuesta obtenerRefacciones(int idTipoRefaccion){
       ListaRefaccionesRespuesta refaccionesRespuesta = new ListaRefaccionesRespuesta();
       refaccionesRespuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
       
       Connection conexionBD = ConexionBD.abrirConexionBD();
       if(conexionBD != null){
           try{
                String consultaRefacciones = "SELECT idRefaccion, stock, nombre "
                        + "FROM refacciones WHERE idTipoRefaccion = ? AND stock > 0";
                PreparedStatement sentenciaRefacciones = conexionBD.prepareStatement(consultaRefacciones);
                sentenciaRefacciones.setInt(1, idTipoRefaccion);
                ResultSet respuesta = sentenciaRefacciones.executeQuery();
                ArrayList<Refaccion> refacciones = new ArrayList();
                while(respuesta.next()) {
                    Refaccion refaccion = new Refaccion();
                    refaccion.setIdRefaccion(respuesta.getInt("idRefaccion"));
                    refaccion.setNombre(respuesta.getString("nombre"));
                    refaccion.setStock(respuesta.getInt("stock"));
                    refacciones.add(refaccion);
                }
            refaccionesRespuesta.setRefacciones(refacciones);
            conexionBD.close();
           }catch(SQLException ex){
               ex.printStackTrace();
               refaccionesRespuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
           }
       }else{
           refaccionesRespuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
       }
       return refaccionesRespuesta;
   }
   
   public static int asociarRefaccionMantenimiento(int idMantenimiento, int idRefaccion){
       int codigoRepuesta = Constantes.OPERACION_EXITOSA;
       Connection conexionBD = ConexionBD.abrirConexionBD();
       if(conexionBD != null){
           try{
               String sentenciaAsociacion = "INSERT INTO refaccionesmantenimientos "
                       + "(idMantenimiento, idRefaccion) VALUES (?, ?);";
               PreparedStatement sentenciaPreparada = conexionBD.prepareStatement(sentenciaAsociacion);
               sentenciaPreparada.setInt(1, idMantenimiento);
               sentenciaPreparada.setInt(2, idRefaccion);
               int asociacionesAfectadas = sentenciaPreparada.executeUpdate();
               if(asociacionesAfectadas != 1){
                   codigoRepuesta = Constantes.ERROR_CONSULTA;
               }
               
               int respuestaEliminacion = eliminarStockRefaccion(idRefaccion);
               if(respuestaEliminacion != Constantes.OPERACION_EXITOSA){
                   return respuestaEliminacion;
               }
           }catch(SQLException ex){
               ex.printStackTrace();
               codigoRepuesta = Constantes.ERROR_CONSULTA;
           }
       }else{
           codigoRepuesta = Constantes.ERROR_CONEXION;
       }
       return codigoRepuesta;
   }
   
   public static int eliminarStockRefaccion(int idRefaccion){
       int codigoRepuesta = Constantes.OPERACION_EXITOSA;
       Connection conexionBD = ConexionBD.abrirConexionBD();
       if(conexionBD != null){
           try{
               String sentenciaEliminacion = "UPDATE refacciones SET stock = stock-1 WHERE idRefaccion = ?";
               PreparedStatement sentenciaPreparada = conexionBD.prepareStatement(sentenciaEliminacion);
               sentenciaPreparada.setInt(1, idRefaccion);
               int asociacionesAfectadas = sentenciaPreparada.executeUpdate();
               if(asociacionesAfectadas != 1){
                   codigoRepuesta = Constantes.ERROR_CONSULTA;
               }
           }catch(SQLException ex){
               ex.printStackTrace();
           }
       }else{
           codigoRepuesta = Constantes.ERROR_CONEXION;
       }
       return codigoRepuesta;       
   }
}
