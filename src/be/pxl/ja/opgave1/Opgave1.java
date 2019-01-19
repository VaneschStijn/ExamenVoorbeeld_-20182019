package be.pxl.ja.opgave1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Opgave1 {
	public static void main(String[] args) {
		CustomerRepository customerRepository = new CustomerRepository();
		System.out.println("*** Klanten uit Louisville:");
		// TODO: 1
		long customersFromLouisville = customerRepository
				.findAll()
				.stream()
				.filter(c -> c.getCity().toLowerCase().equals("louisville"))
				.count();

		System.out.println(customersFromLouisville);
		System.out.println();

		System.out.println("*** Jarige klanten: ");
		// TODO: 2
		customerRepository
				.findAll()
				.stream()
				.filter(c -> c.getDateOfBirth().equals(LocalDate.now()))
				.forEach(c -> System.out.println(String.join(" ", c.getName(), c.getFirstName(), c.getDateOfBirth().toString())));
		System.out.println();

		System.out.println("*** 10 jongste klanten:");
		// TODO: 3
//		customerRepository
//				.findAll()
//				.stream()
//				.sorted((customer1, customer2) -> customer2.getDateOfBirth().compareTo(customer1.getDateOfBirth()))
//				.limit(10).forEach(c -> System.out.println(String.join(" ", c.getName(), c.getFirstName(), c.getDateOfBirth().toString())));

		customerRepository
				.findAll()
				.stream()
				.sorted((customer1, customer2) -> customer2.getDateOfBirth().compareTo(customer1.getDateOfBirth()))
				.limit(10).forEach(c -> System.out.println(String.join(" ", c.getName(), c.getFirstName(), c.getDateOfBirth().toString())));

		System.out.println();
	
		// TODO: 4
		ActivityProcessor activityFileProcessor = new ActivityProcessor(customerRepository);
		List<Activity> allActivities = processFilesInDirectory("resources/opgave1", activityFileProcessor);

		System.out.println("*** Top 10 klanten");
		// TODO: 5
		customerRepository
				.findAll()
				.stream()
				.sorted((c1, c2) -> c2.getPoints() - c1.getPoints())
				.limit(10)
				.forEach(c -> System.out.println(String.join(" ",
						c.getName(), c.getFirstName(), c.getDateOfBirth().toString(), c.getPoints() + "")));
		System.out.println();

		System.out.println("** Alle activiteiten meest actieve klant (gesorteerd op datum):");
		// TODO: 6
//		Optional<Customer> customer = customerRepository
//				.findAll()
//				.stream()
//				.sorted((c1, c2) -> c2.getPoints() - c1.getPoints())
//				.findFirst();

		Optional<Customer> customer = customerRepository
				.findAll()
				.stream()
				.min((c1, c2) -> c2.getPoints() - c1.getPoints());

		if(customer.isPresent()){
			printActivities(customer.get(), allActivities);
		}
	}

	private static List<Activity> processFilesInDirectory(String directory, ActivityProcessor activityFileProcessor) {
		Path directoryPath = Paths.get(directory);
		Path errorFile = Paths.get("resources/opgave1/log/errors.log");
		try {
			// alle files 1 voor 1 behandelen
			return Files
					.list(directoryPath)
					// enkel files geen directories
					.filter(p -> Files.isRegularFile(p))
					// process file
					.map(file -> activityFileProcessor.processActivities(file, errorFile))
					// resultaat is lijst, flatmap om van meerdere lijsten van activiteiten 1 lijst te maken
					.flatMap(activities -> activities.stream())
					.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	private static void printActivities(Customer customer, List<Activity> allActivities) {
		allActivities.stream()
				.filter(a -> a.getCustomerNumber().equals(customer.getCustomerNumber()))
				.sorted((a1, a2) -> a2.getActivityDate().compareTo(a1.getActivityDate()))
				.forEach(a -> System.out.println(a.getActivityDate() + " " + a.getActivityType() + " " + getPoints(a)));
	}

	private static int getPoints(Activity activity) {
		return (int) (activity.getDistance() * activity.getActivityType().getPointsPerKm());
	}
}
