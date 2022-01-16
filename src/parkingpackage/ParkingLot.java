package parkingpackage;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
	String lotID; // Identity of the parking lot, based on the host name
	String lotName; // The name of the parking lot, such that parking spots and vehicle counters register at this server.
	int capacity; // Maximum number of spots for vehicles, including the registered parking spot clients.
	int registered; // Number of registered IoT spots 
	int reservations; // Number of reservations on the parking lot
	int vehicles; // Number of vehicles in the parking lot = counterIn - counterOut
	int free; // Number of free parking spots, which is equal to Capacity - Vehicles - Reservations
	private List<ParkingSpot> spotList = new ArrayList<>(); // List of registered spots (IoT equipped spots)
	private List<Vehicle> carList = new ArrayList<>(); // List of vehicles in the parking lot
	private List<Vehicle> reserveList = new ArrayList<>(); // List of reservation
	private VehicleCounter counterIn;
	private VehicleCounter counterOut;
	
	
	public ParkingLot(String lotID, String lotName, int capacity) {
		this.lotID = lotID;
		this.lotName = lotName;
		this.capacity = capacity;
		this.reservations = 0;
		this.free = capacity;
		this.vehicles = 0;
		this.registered = 0;
	}

	// Register a spot client
	public void registerSpot(String id, int xVal, int yVal)
	{
		ParkingSpot spot = new ParkingSpot(id, this.lotName, xVal, yVal);
		spotList.add(spot);
		registered++;
	}
	
	// Get the number of available spots
	public int getAvailableSpot()
	{
		return free;
	}
	
	// Find the spotID of the a available IoT spot
	public String findAvailableSpot()
	{
		ParkingSpot spot = spotList.stream().filter(c -> ParkingSpot.State.Free.equals(c.spotState)).findFirst().orElse(null);
		return (spot.equals(null)) ? "No Available Spot" : spot.spotID;
	}
	
	// Reserve a random available spot
	public void reserveGeneral(String vehicleID)
	{
		if(free > 0)
		{
			Vehicle car = new Vehicle(vehicleID);
			car.hasReservation = true;
			reserveList.add(car);
			reservations++;
			free--;
		}
	}
	
	// Reserve a specific spot
	public void reserveSpot(String spotID, String vehicleID)
	{
		ParkingSpot spot = spotList.stream().filter(c -> spotID.equals(c.spotID)).findFirst().orElseThrow();
		spot.updateState(ParkingSpot.State.Reserved, vehicleID);
		Vehicle car = new Vehicle(vehicleID);
		car.hasReservation = true;
		reserveList.add(car);
		reservations++;
		free--;
	}
	
	// Called when there is a vehicle parked in a spot
	public void parkNoti(String spotID)
	{
		Timestamp time = new Timestamp(System.currentTimeMillis());
		ParkingSpot spot = spotList.stream().filter(c -> spotID.equals(c.spotID)).findFirst().orElseThrow();
		if(spot.spotState.equals(ParkingSpot.State.Reserved))
		{
			Vehicle car = new Vehicle(spot.vehicleID);
			car.hasReservation = true;
			reserveList.remove(car);
		}
		spot.updateState(ParkingSpot.State.Occupied, "NA");
		spot.timeParked = time;
	}
	
	// Called when there is a vehicle leave a spot
	public void leaveSpotNoti(String spotID)
	{
		Timestamp time = new Timestamp(System.currentTimeMillis());
		ParkingSpot spot = spotList.stream().filter(c -> spotID.equals(c.spotID)).findFirst().orElseThrow();
		spot.updateState(ParkingSpot.State.Free, spotID);
		spot.timeLeft = time;
	}
	
	// Called when there is a vehicle enter the lot
	public void enterNoti(String carID)
	{
		Timestamp time = new Timestamp(System.currentTimeMillis());
		Vehicle car = new Vehicle(carID);
		if(reserveList.stream().anyMatch(c -> carID.equals(c.plate)))
		{
			car.hasReservation = true;
			reserveList.remove(car);
			reservations--;
		}
		car.timeIn = time;
		carList.add(car);
		vehicles++;
		free = capacity - reservations - vehicles;
	}
	
	// Called when there is a vehicle leave the lot
	public void leaveNoti(String carID)
	{
		Timestamp time = new Timestamp(System.currentTimeMillis());
		Vehicle car = carList.stream().filter(c -> carID.equals(c.plate)).findFirst().orElseThrow();
		carList.remove(car);
		car.timeOut = time;
		vehicles--;
		free = capacity - reservations - vehicles;
	}
	
	public void registerCounter(String counterID, int direction)
	{
		if (direction == 1)
		{
			counterIn = new VehicleCounter(counterID, direction, lotName);
		}
		else if (direction == 0)
		{
			counterOut = new VehicleCounter(counterID, direction, lotName);
		}
		else
		{
			throw new ArrayIndexOutOfBoundsException();
		}
	}
	
	public void updateCounter(int direction, int counter, String plate)
	{
		if (direction == 1)
		{
			counterIn.updateCounter(counter, plate);
		}
		else if (direction == 0)
		{
			counterOut.updateCounter(counter, plate);
		}
		else
		{
			throw new ArrayIndexOutOfBoundsException();
		}
	}
	
	public void updateState()
	{
		vehicles = counterIn.counter - counterOut.counter;
		free = capacity - vehicles - reservations;
	}
	
}
