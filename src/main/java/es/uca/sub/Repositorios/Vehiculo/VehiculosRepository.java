package es.uca.sub.Repositorios.Vehiculo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

@Repository
public interface VehiculosRepository extends JpaRepository<Vehiculo,Integer> {
@Query("SELECT v"
		+ " FROM Vehiculo v WHERE v.marca=?1 and v.modelo=?2")
List<Vehiculo>findByMarcaModelo(@Param("marca") String marca,@Param("modelo") String modelo);

@Query("SELECT v"
		+ " FROM Vehiculo v WHERE v.marca=?1 and v.estado=?2")
List<Vehiculo>findByMarcaEstado(@Param("marca") String marca,@Param("estado") String estado);

@Query("SELECT v"
		+ " FROM Vehiculo v WHERE v.modelo=?1 and v.estado=?2")
List<Vehiculo>findByModeloEstado(@Param("modelo") String modelo,@Param("estado") String estado);

@Query("SELECT v"
		+ " FROM Vehiculo v WHERE v.marca=?1 and v.modelo=?2 and v.estado=?3")
List<Vehiculo>findByMarcaModeloEstado(@Param("marca") String marca,@Param("modelo") String modelo,@Param("estado") String estado);

@Query("SELECT v"
		+ " FROM Vehiculo v WHERE v.precio<?1")
List<Vehiculo>findByPrecioMenor(@Param("precio") Integer precio);
@Query("SELECT v"
		+ " FROM Vehiculo v WHERE v.precio<=?1")
List<Vehiculo>findByPrecioMenorIgual(@Param("precio") Integer precio);
@Query("SELECT v"
		+ " FROM Vehiculo v WHERE v.precio>?1")
List<Vehiculo>findByPrecioMayor(@Param("precio") Integer precio);
@Query("SELECT v"
		+ " FROM Vehiculo v WHERE v.precio>=?1")
List<Vehiculo>findByPrecioMayorIgual(@Param("precio") Integer precio);



List<Vehiculo>findByMarca(@Param("marca") String marca);
List<Vehiculo>findByModelo(@Param("modelo") String modelo);
List<Vehiculo>findByMatricula(@Param("matricula") String matricula);
List<Vehiculo>findByEstado(@Param("estado") String estado);
List<Vehiculo>findByCaracteristicas(@Param("caracteristicas") String disponibilidad);
List<Vehiculo>findAll();


List<Vehiculo>findByPrecio(String texto);

List<Vehiculo>findByPrecioBetween(Integer number1,Integer number2);




}