package org.dclou.example.demogpb.catalog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

	List<Item> findByName(@Param("name") String name);

	List<Item> findByNameContaining(@Param("name") String name);
}
