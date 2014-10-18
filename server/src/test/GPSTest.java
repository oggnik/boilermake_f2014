package test;

public class GPSTest {
	
	public static void main(String[] args){
		GPSMath gpsMath = new GPSMath();
		gpsMath.setLocation1(40.428123, -86.916911); //<Lattitude, Longitude>
		gpsMath.setLocation2(40.432223, -86.916461);
		
		System.out.println("\ndistance is  "+gpsMath.getDistance());
		//System.out.println("\nR1  <" + gpsMath.getR1()[0] +", "+ gpsMath.getR1()[1]+">");
		//System.out.println("R2  <" + gpsMath.getR2()[0] +", "+ gpsMath.getR2()[1]+">");
		System.out.println("\nangle from 1 to 2 is " + gpsMath.getAngle12());
		System.out.println("angle from 2 to 1 is " + gpsMath.getAngle21());
		
		
		
	}
}
