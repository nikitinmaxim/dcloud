package org.dclou.example.demogpb.catalog.web;

import org.dclou.example.demogpb.catalog.Item;
import org.dclou.example.demogpb.catalog.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CatalogController {

    @Autowired
	private ItemRepository itemRepository;

	public CatalogController() {
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Item> getItems() {
        return itemRepository.findAll();
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Item getItem(@PathVariable("id") long id) {
		return itemRepository.findOne(id);
	}
}
