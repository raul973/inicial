package es.uca.sub.Repositorios.Usuario;


import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	
	
	Usuario findByNombre(@Param("nombre") String nombre);

	Usuario findByContrasena(@Param("contrasena") String nombre);

	Usuario findByUsername(@Param("username") String username);

	List<Usuario> findByRol(@Param("rol") String rol);

	


}