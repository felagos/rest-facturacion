package com.rest.facturacion.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.rest.facturacion.entities.Cliente;

public interface IClienteRepository extends PagingAndSortingRepository<Cliente, Long> {
	
	public Cliente findByCreatedAt(Date date);
	
	@Query("select c from Cliente c left join fetch c.facturas where c.id = :id")
	public Cliente fetchClientWithFacturaById(@Param("id") Long id);
	
}
