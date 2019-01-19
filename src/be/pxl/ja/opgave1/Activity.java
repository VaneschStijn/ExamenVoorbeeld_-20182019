package be.pxl.ja.opgave1;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Activity {
	private String customerNumber;
	private ActivityType activityType;
	private double distance;
	private LocalDate activityDate;
	private ActivityTracker tracker;

	private static final DateTimeFormatter FORMATTERENDOMODO =
			DateTimeFormatter.ofPattern("yyyyMMdd");

	private static final DateTimeFormatter FORMATTERSTRAVA =
			DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public ActivityType getActivityType() {
		return activityType;
	}

	public void setActivityType(ActivityType activityType) {
		this.activityType = activityType;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	public LocalDate getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(LocalDate activityDate) {
		this.activityDate = activityDate;
	}
	
	public void setTracker(ActivityTracker tracker) {
		this.tracker = tracker;
	}
	
	public ActivityTracker getTracker() {
		return tracker;
	}

	//TOEGEVOEGD DOOR STIJN VANESCH
	public Activity toActivityEndomodo(String[] data){
		Activity activity = new Activity();
		activity.setActivityDate(LocalDate.parse(data[0], FORMATTERENDOMODO));
		activity.setCustomerNumber(data[1]);
		activity.setActivityType(ActivityType.valueOf(data[2]));
		activity.setDistance(Double.parseDouble(data[3]));

		return activity;
	}

	//TOEGEVOEGD DOOR STIJN VANESCH
	public Activity toActivityStrava(String[] data){
		Activity activity = new Activity();
		activity.setCustomerNumber(data[2]);
		activity.setActivityDate(LocalDate.parse(data[3], FORMATTERSTRAVA));
		activity.setActivityType(ActivityType.valueOf(data[4]));
		activity.setDistance(Double.parseDouble(data[3]));

		return activity;
	}

}
