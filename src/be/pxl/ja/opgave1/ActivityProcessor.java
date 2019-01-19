package be.pxl.ja.opgave1;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ActivityProcessor {

	private CustomerRepository customerRepository;
	
	public ActivityProcessor(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}
	
	public List<Activity> processActivities(Path activityFile, Path errorFile) {
		// TODO: implement this method
		List<Activity> activities = new ArrayList<>();
		String fileName = activityFile.getFileName().toString();

		if(fileName.contains("strava") || fileName.contains("endomodo")) {
			if(fileName.contains("strava")){
				try (BufferedReader reader = Files.newBufferedReader(activityFile)) {
					String line = null;
					while ((line = reader.readLine()) != null) {
						String[] data = new String[6];

						for (int i = 0; i < 2; i++) {
							int index = line.indexOf(" ");
							data[i] = line.substring(0, index);
							line = line.substring(++index);
						}

						for (int i = 2; i < 6; i++) {
							int index = line.indexOf(";");
							data[i] = line.substring(0, index);
							line = line.substring(++index);
						}

						Activity activity = new Activity();
						activities.add(activity.toActivityStrava(data));
					}
				} catch (IOException e) {
					writeErrorToFile(errorFile);
				}
			}else{
				try (BufferedReader reader = Files.newBufferedReader(activityFile)) {
					String line = null;
					while ((line = reader.readLine()) != null) {
						String[] data = line.split(";");
						Activity activity = new Activity();
						activities.add(activity.toActivityEndomodo(data));
					}
				} catch (IOException e) {
					writeErrorToFile(errorFile);
				}
			}
		}else{
			writeErrorToFile(errorFile);
		}
		return activities;
	}

	private void writeErrorToFile(Path errorFile) {
		//
	}
}
