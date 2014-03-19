package sourcecoded.mods.creativeAdditions.shared;

public class RelativesAPI {

	public static int[] relativesDifference = {0,0,0};
	public static int[] relativesInitial = {0,0,0};
	public static int[] relativesFinal = {0,0,0};
	
	//Set Relative Start, ARGS: x, y, z
		public static void setRel(double x, double y, double z) {
			relativesInitial[0] = (int)x;
			relativesInitial[1] = (int)y;
			relativesInitial[2] = (int)z;		
		}
		
		//Set Relative Final, returns with relative distance. ARGS: x, y, z
		public static int[] getRel(double x, double y, double z) {		
			relativesFinal[0] = (int)x;
			relativesFinal[1] = (int)y;
			relativesFinal[2] = (int)z;	
			
			for (int i=0; i<3; i++) {
				relativesDifference[i] = relativesInitial[i] - relativesFinal[i];
			}
			
			return relativesDifference;
		}
}
