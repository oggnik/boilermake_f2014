package test;

public class GPSMath {
	private double[] location1, location2, unit;
	// private double bearing1, bearing2;
	
	public GPSMath() {
		location1 = new double[2];
		location2 = new double[2];
		unit = new double[2];
		unit[0] = 1.0;
		unit[1] = 0.0;
	}
	
	//Inputs in signed degrees format
	// <lat, lon>. lat [-90, 90], lon[-180, 180]
	public int setLocation1(double lat, double lon){
		location1[0] = lat;
		location1[1] = lon;
		return 1;
	}
	
	public int setLocation2(double lat, double lon){
		location2[0] = lat;
		location2[1] = lon;
		return 1;
	}
	
	public double[] hat(double[] vector){
		double[] unitvec = new double[2];
		double mag;
		mag = Math.sqrt(Math.pow(vector[0], 2.0) + Math.pow(vector[1], 2.0));
		unitvec[0] = vector[0]/mag;
		unitvec[1] = vector[1]/mag;
		return unitvec;
	}	
	
	public double getDistance(){
		/* Original distance method: Terrible
		 * double pa, po;
		 * pa = Math.pow((location1[0] - location2[0]), 2.0);
		 * po = Math.pow((location1[1] - location2[1]), 2.0);
		 * return Math.sqrt(pa+po)*20925524.9; //ft radius of earth
		 */
		// Haversine Formula:
		double R, la1, la2, lo1, lo2, a, c, d;
		la1 = Math.toRadians(location1[0]);
		la2 = Math.toRadians(location2[0]);
		lo1 = Math.toRadians(location1[1]);
		lo2 = Math.toRadians(location2[1]);
		R = 20902231; //radius of earth in ft
		a = Math.pow(Math.sin((la2-la1)/2.0), 2.0)+Math.cos(la1)*Math.cos(la2)*Math.pow(Math.sin((lo2-lo1)/2.0),2.0);
		c = 2* Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		d = R*c;
		return d;
	}
	
	public double dotAngle(double[] a, double[] b){
		double dot, L1, L2, product;
		a = hat(a);
		b = hat(b);
		dot = a[0]*b[0]+a[1]*b[1];
		//L1 = Math.sqrt(Math.pow(a[0], 2.0)+Math.pow(a[1], 2.0));
		//L2 = Math.sqrt(Math.pow(b[0], 2.0)+Math.pow(b[1], 2.0));
		//System.out.println(L1+"  "+L2);
		//product = dot / L1 / L2;
		return Math.toDegrees(Math.acos(Math.toRadians(dot)));
	}
	
	public double[] getR1(){ //from 1 to 2
		double[] result = new double[2];
		result[0] = location2[0]-location1[0];
		result[1] = location2[1]-location1[1];
		return result;
	}
	
	public double[] getR2(){ //from 2 to 1
		double[] result = new double[2];
		result[0] = location1[0]-location2[0];
		result[1] = location1[1]-location2[1];
		return result;
	}
	
	public double getAngle12(){ //from 1 to 2
		double orientation; //degrees from north
		double[] R1 = new double[2];
		R1 = getR1();
		int Lodir, Rdir; //direction of Longitude1 and R
		Lodir = (int) (location1[1]/Math.abs(location1[1]));
		Rdir = (int) (R1[1]/ Math.abs(R1[1]));
			//System.out.println("Lodir 1: "+ Lodir); //debugging
			//System.out.println("Rdir 1: "+ Rdir ); //debugging
		if(Lodir == Rdir ){
			orientation = dotAngle(unit, R1);
		}
		else if(Lodir == -1*Rdir){
			orientation = 360 - dotAngle(unit, R1);
		}
		else{
			//R1 directly N or S
			if (R1[0]>0.0){ //R1 points N
				orientation = 0.0;
			}
			else if (R1[0]<0.0){ //R2 points S
				orientation = 180.0;
			}
			else{
				// R1 is zero vector
				System.out.println("ERROR" );
				orientation = 0.0;
			}
		}
		return orientation;
	}
	
	public double getAngle21(){ //from 2 to 1
		double orientation; //degrees from north
		double[] R2 = new double[2];
		R2 = getR2();
		int Lodir, Rdir; //direction of Longitude1 and R
		Lodir = (int) (location2[1]/Math.abs(location2[1]));
		Rdir = (int) (R2[1]/ Math.abs(R2[1]));
			//System.out.println("Lodir 1: "+ Lodir); //debugging
			//System.out.println("Rdir 1: "+ Rdir ); //debugging
		if(Lodir == Rdir ){
			orientation = dotAngle(unit, R2);
		}
		else if(Lodir == -1*Rdir){
			orientation = 360 - dotAngle(unit, R2);
		}
		else{
			//R2 directly N or S
			if (R2[0]>0.0){ //R2 points N
				orientation = 0.0;
			}
			else if (R2[0]<0.0){ //R2 points S
				orientation = 180.0;
			}
			else{
				// R2 is zero vector
				System.out.println("ERROR" );
				orientation = 0.0;
			}
		}
		return orientation;
	}
}