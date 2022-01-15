package parkingpackage;

public class ParkingSpot {
	public enum State{
		Free, Reserved, Occupied
	}
	String spotID;
	State spotState;
	String lotName;
	String vehicleID;
	public ParkingSpot(String spotID, String lotName) {
		this.spotID = spotID;
		this.lotName = lotName;
		this.spotState = State.Free;
	}
	public void updateState(ParkingSpot.State state)
	{
		this.spotState = state;
	}
	
}
