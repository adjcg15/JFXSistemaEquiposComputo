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
    public static UsuarioRespuesta verificarUsuarioSesion(String correo, String password){
        UsuarioRespuesta usuarioRespuesta = new UsuarioRespuesta();
        usuarioRespuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        Connection conexion = ConexionBD.abrirConexionBD();
        if (conexion != null){
            try {
                String consulta = "SELECT * FROM usuarios WHERE correo = ? AND contrasenia = ?";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setString(1,correo);
                prepararSentencia.setString(2, password);
                ResultSet resultado = prepararSentencia.executeQuery();
                Usuario usuarioVerificado = new Usuario();
                if(resultado.next()){
                    usuarioVerificado.setIdUsuario(resultado.getInt("idUsuario"));
                    usuarioVerificado.setNombre(resultado.getString("nombre"));
                    usuarioVerificado.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    usuarioVerificado.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    usuarioVerificado.setTelefono(resultado.getString("telefono"));
                    usuarioVerificado.setDirección(resultado.getString("direccion"));
                    usuarioVerificado.setCorreo(resultado.getString("correo"));
                    usuarioVerificado.setContrasenia(resultado.getString("contrasenia"));
                    usuarioVerificado.setPrivilegiado(resultado.getBoolean("privilegiado"));
                }
                usuarioRespuesta.setUsuario(usuarioVerificado);
                conexion.close();
            } catch (SQLException ex) {
                usuarioRespuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA); 
            }          
        }else{
            usuarioRespuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return usuarioRespuesta;
    }
}
