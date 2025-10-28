package Service;

import java.util.List;
import java.util.Optional;
import Entity.UsuarioSocial;

public interface InterUsuarioSocial {

    public void Guardar(UsuarioSocial usuarioSocial);
    
    public void Eliminar(UsuarioSocial usuarioSocial);
    
    public void Editar(UsuarioSocial usuarioSocial);
    
    public Optional<UsuarioSocial> Busquedaporid(Long id);
    
    public boolean existeUsuarioDuplicado(String usuario, Long id);
    
    public UsuarioSocial buscarUsuarioPorUsuario(String usuario);
    
    public List<UsuarioSocial> getUsuarioSocial();
}
