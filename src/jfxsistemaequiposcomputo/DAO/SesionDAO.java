/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jfxsistemaequiposcomputo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jfxsistemaequiposcomputo.modelo.ConexionBD;
import jfxsistemaequiposcomputo.pojo.Usuario;
import jfxsistemaequiposcomputo.pojo.UsuarioRespuesta;
import jfxsistemaequiposcomputo.utils.Constantes;

public class SesionDAO {
    public static Usuario verificarUsuarioSesion(String correo, String password){
        Usuario usuarioVerificado = new Usuario();
        UsuarioRespuesta mensajeUsuario = new UsuarioRespuesta();
        Connection conexion = ConexionBD.abrirConexionBD();
        if (conexion != null){
            try {
                String consulta = "SELECT * FROM usuario WHERE correo = ? AND passwordd = ?";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setString(1,correo);
                prepararSentencia.setString(2, password);
                ResultSet resultado = prepararSentencia.executeQuery();
                mensajeUsuario.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                
                if(resultado.next()){
                    usuarioVerificado.setIdUsuario(resultado.getInt("idUsuario"));
                    usuarioVerificado.setNombre(resultado.getString("nombre"));
                    usuarioVerificado.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    usuarioVerificado.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    usuarioVerificado.setTelefono(resultado.getString("telefono"));
                    usuarioVerificado.setDirección(resultado.getString("direccion"));
                    usuarioVerificado.setCorreo(resultado.getString("coreo"));
                    usuarioVerificado.setContrasenia(resultado.getString("contrasenia"));
                    
                    
                }
                conexion.close();
                
            } catch (SQLException ex) {
                mensajeUsuario.setCodigoRespuesta(Constantes.ERROR_CONSULTA); 
            }          
        }else{
            mensajeUsuario.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return usuarioVerificado;
    }
}
