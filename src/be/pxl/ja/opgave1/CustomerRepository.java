package be.pxl.ja.opgave1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomerRepository {
	private Map<String, Customer> customers = new HashMap<>();
	
	public CustomerRepository() {
		for (Customer customer : Customers.customers) {
			customers.put(customer.getCustomerNumber(), customer);
		}
	}
	
	public Customer getByCustomerNumber(String customerNumber) {
		// TOEGEVOEGD DOOR STIJN VANESCH
		Optional<Customer> result = customers
				.values()
				.stream()
				.filter(c -> c.getCustomerNumber().equals(customerNumber))
				.findFirst();

		//		if(!result.isPresent()){
		//			return null;
		//		}
		//
		//		return result.get();

		return result.orElse(null);
	}
	
	public List<Customer> findAll() {
		// TOEGEVOEGD DOOR STIJN VANESCH
		return new ArrayList<>(customers.values());

		//return customers.values().stream().collect(Collectors.toList());
	}
}
