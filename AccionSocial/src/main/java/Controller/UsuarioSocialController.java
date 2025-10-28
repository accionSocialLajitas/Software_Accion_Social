package Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

import Entity.UsuarioSocial;
import Service.InterUsuarioSocial;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class UsuarioSocialController {

    @Autowired
    private InterUsuarioSocial usuarioSocialService;
    
    @GetMapping("/mostrarlistausuarios")
    public String detallesUsuarios(Model model) {
        model.addAttribute("UsuarioSocial", usuarioSocialService.getUsuarioSocial());
        return "Vistas/lista_usuarios";
    }
    
    @GetMapping("/formulariousuarios")  
    public String guardarDatos(Model model) {
        UsuarioSocial usuarioSocial = new UsuarioSocial();
        usuarioSocial.setRol("Recepcionista");
        model.addAttribute("usuarioSocial", usuarioSocial);
        return "Vistas/formulario_usuario";
    }

    @PostMapping("/guardarusuarios")
    public String guardar(UsuarioSocial usuarioSocial, Model model) {
        Long id = usuarioSocial.getId();
       
        if (usuarioSocialService.existeUsuarioDuplicado(usuarioSocial.getUsuario(), id)) {
            model.addAttribute("error", "El nombre de usuario ya existe");
            model.addAttribute("usuarioSocial", usuarioSocial);
            return "Vistas/formulario_usuario";
        }
        
        usuarioSocialService.Guardar(usuarioSocial);
        return "redirect:/mostrarlistausuarios";
    }
    
    @GetMapping("/editarusuario/{id}")
    public String editarUsuario(@PathVariable Long id, Model model) {
        UsuarioSocial usuarioSocial = usuarioSocialService.Busquedaporid(id).orElse(null);
        if (usuarioSocial != null) {
            model.addAttribute("usuarioSocial", usuarioSocial);
            return "Vistas/formulario_usuario";
        }
        return "redirect:/mostrarlistausuarios";
    }
    
    @GetMapping("/eliminarusuario/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        UsuarioSocial usuarioSocial = usuarioSocialService.Busquedaporid(id).orElse(null);
        if (usuarioSocial != null) {
            usuarioSocialService.Eliminar(usuarioSocial);
        }
        return "redirect:/mostrarlistausuarios";
    }
}
