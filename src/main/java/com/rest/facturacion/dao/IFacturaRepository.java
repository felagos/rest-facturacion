package com.rest.facturacion.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rest.facturacion.entities.Factura;

@Repository
public interface IFacturaRepository extends JpaRepository<Factura, Long> {
	
	@Query("select f from Factura f join fetch f.cliente c left join fetch f.itemFacturas i left join fetch i.producto where f.id = :id")
	public Factura fetchFacturaById(@Param("id") Long id);

}
