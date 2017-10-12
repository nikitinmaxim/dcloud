package org.dclou.example.demogpb.customer.mvc;

import org.dclou.example.demogpb.customer.Customer;
import org.dclou.example.demogpb.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;

	public CustomerController() {
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Customer> getCustomers() {
		return customerRepository.findAll();
	}

	@GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
	public Customer getCustomer(@PathVariable("id") long id) {
		return customerRepository.findOne(id);
	}
}
