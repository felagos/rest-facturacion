package com.rest.facturacion.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rest.facturacion.entities.Producto;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, Long> {
	
	@Query("select p from Producto p where p.nombre like %:term%")
	public List<Producto> findByName(@Param("term") String term);
	
	public List<Producto> findByNombreLikeIgnoreCase(String term);

}
