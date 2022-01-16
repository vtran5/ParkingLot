package parkingpackage;

import java.sql.Timestamp;

public class ParkingSpot {
	public enum State{
		Free, Reserved, Occupied
	}
	String spotID; // Identity of the parking spot, based on the host name
	State spotState; // Is the parking spot free, reserved or occupied? The state might be reflected in a status LED if it exits
	String lotName; // The name of the parking lot where the parking spot belongs to.
	String vehicleID; // The ID of the vehicle that reserved the spot
	String color; // Green is free, Orange is reserved and Red is occupied
	int xValue;
	int yValue;
	Timestamp timeParked;
	Timestamp timeLeft;

	
	public ParkingSpot(String spotID, String lotName, int xValue, int yValue) {
		this.spotID = spotID;
		this.lotName = lotName;
		this.spotState = State.Free;
		this.xValue = xValue;
		this.yValue = yValue;
	}
	
	public void updatePosition(int xVal, int yVal)
	{
		xValue = xVal;
		yValue = yVal;
	}
	
	public void updateState(ParkingSpot.State state, String vehicleID)
	{
		this.spotState = state;
		updateDisplay(vehicleID);
	}
	
	private void updateDisplay(String vehicleID)
	{
		if(spotState.equals(State.Free))
		{
			color = "green";
			this.vehicleID = "NA";
		}
		else if(spotState.equals(State.Reserved))
		{
			color = "orange";
			this.vehicleID = vehicleID;
		}
		else
		{
			color = "red";
			this.vehicleID = "NA";
		}
	}
	
}
