package org.dclou.example.demogpb.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	List<Customer> findByName(@Param("name") String name);
}
