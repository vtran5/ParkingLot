package parkingpackage;

public class TestClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// Create a parking lot with 4 parking spot, including 2 IoT spots
		ParkingLot lot1 = new ParkingLot("1", "test lot" , 4);
		lot1.registerSpot("1", 2, 4);
		lot1.registerSpot("2", 3, 4);
		lot1.registerCounter("Counter1", 1);
		lot1.registerCounter("Counter2", 0);
		
		// Reserve scenario
		lot1.reserveGeneral("AA-15");
		lot1.reserveSpot("2","AA-14");
		
		// Parking and leaving scenario
		lot1.enterNoti("AA-16");
		lot1.parkNoti("1");
		lot1.enterNoti("AA-14");
		lot1.parkNoti("2");
		lot1.enterNoti("AA-15");
		lot1.leaveSpotNoti("2");
		lot1.leaveNoti("AA-14");
		lot1.leaveNoti("AA-15");
		lot1.leaveSpotNoti("1");
		lot1.leaveNoti("AA-16");
		
		System.out.println("Hellow world");
	}

}
