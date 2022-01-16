package parkingpackage;

import java.sql.Timestamp;

public class Vehicle {
	String plate;
	Timestamp timeIn;
	Timestamp timeOut;
	boolean hasReservation;
	
	public Vehicle(String plate) {
		super();
		this.plate = plate;
	}
	
	
}
