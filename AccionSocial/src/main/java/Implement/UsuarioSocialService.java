package Implement;

import java.util.List;
import java.util.Optional;

import Entity.UsuarioSocial;
import Repository.UsuarioSocialRepository;
import Service.InterUsuarioSocial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioSocialService implements InterUsuarioSocial {
    
    @Autowired
    private UsuarioSocialRepository usuarioSocialRepository;

    @Override
    public void Guardar(UsuarioSocial usuarioSocial) {
        usuarioSocialRepository.save(usuarioSocial);
    }

    @Override
    public Optional<UsuarioSocial> Busquedaporid(Long id) {
        return usuarioSocialRepository.findById(id);
    }

    @Override
    public UsuarioSocial buscarUsuarioPorUsuario(String usuario) {
        return usuarioSocialRepository.findByUsuario(usuario);
    }


    @Override
    public boolean existeUsuarioDuplicado(String usuario, Long id) {
        UsuarioSocial usuarioExistente = usuarioSocialRepository.findByUsuario(usuario);
        if (usuarioExistente == null) {
            return false;
        }
        return id == null || !usuarioExistente.getId().equals(id);
    }


    @Override
    public void Eliminar(UsuarioSocial usuarioSocial) {
        usuarioSocialRepository.delete(usuarioSocial);
    }

    @Override
    public void Editar(UsuarioSocial usuarioSocial) {
        usuarioSocialRepository.save(usuarioSocial);
    }

    @Override
    public List<UsuarioSocial> getUsuarioSocial() {
        return usuarioSocialRepository.findAll();
    }
}
