package parkingpackage;

public class VehicleCounter {
	String counterID; // Identity of the vehicle counter, based on the host name. When multiple LwM2M clients run on the same host, used a "-N" sequence number to make the distinction.
	int counter; // The number of vehicles that passed this vehicle detector.
	String lastPlate; // If the detector is based on a camera with ALPR, the last detected license plate is provided in this resource.
	int Direction; // Direction of the gate that has the counter, 1 is enter, 0 is exit
	String lotName; // The name of the parking lot where the vehicle counter belongs to.  The value is non-volatile, such that a restart will result in registering at the related parking lot server.
	String text;
	
	public VehicleCounter(String counterID, int direction, String lotName) {
		this.counterID = counterID;
		Direction = direction;
		this.lotName = lotName;
		counter = 0;
		lastPlate = "NA";
	}
	
	public void updateCounter(int counter, String plate)
	{
		this.counter = counter;
		this.lastPlate = plate;
	}
	
	public void updateDisplay(String text)
	{
		this.text = text;
	}

}
