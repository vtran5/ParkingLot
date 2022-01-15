package parkingpackage;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
	String lotID;
	String lotName;
	int capacity;
	int reservations;
	int vehicles;
	int free;
	private List<ParkingSpot> spotList = new ArrayList<>(); 
	
	
	
	public ParkingLot(String lotID, String lotName, int capacity) {
		this.lotID = lotID;
		this.lotName = lotName;
		this.capacity = capacity;
		this.reservations = 0;
		this.free = capacity;
		this.vehicles = 0;
		for (int i = 0; i < capacity; i++)
		{
			ParkingSpot spot = new ParkingSpot(Integer.toString(i), this.lotName);
			spotList.add(spot);
		}
	}

	public void addSpot(String id)
	{
		ParkingSpot spot = new ParkingSpot(id, this.lotName);
		spotList.add(spot);
		capacity++;
		free++;
	}
	
	// Get the number of available spots
	public int getAvailableSpot()
	{
		int available = (int) spotList.stream().filter(c -> ParkingSpot.State.Free.equals(c.spotState)).count();
		return available;
	}
	
	// Find the spotID of the a available spot
	public String findAvailableSpot()
	{
		ParkingSpot spot = spotList.stream().filter(c -> ParkingSpot.State.Free.equals(c.spotState)).findFirst().orElse(null);
		return (spot.equals(null)) ? "No Available Spot" : spot.spotID;
	}
	
	// Reserve a random available spot
	public void reserveGeneral()
	{
		ParkingSpot spot = spotList.stream().filter(c -> ParkingSpot.State.Free.equals(c.spotState)).findFirst().orElseThrow();
		spot.updateState(ParkingSpot.State.Reserved);
		reservations++;
		free--;
	}
	
	// Reserve a specific spot
	public void reserveSpot(String spotID)
	{
		ParkingSpot spot = spotList.stream().filter(c -> spotID.equals(c.spotID)).findFirst().orElseThrow();
		spot.updateState(ParkingSpot.State.Reserved);
		reservations++;
		free--;
	}
	
	public void parkNotify(String spotID)
	{
		ParkingSpot spot = spotList.stream().filter(c -> spotID.equals(c.spotID)).findFirst().orElseThrow();
		spot.updateState(ParkingSpot.State.Occupied);
		vehicles++;
		free--;
	}
	
}
