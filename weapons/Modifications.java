package myvcrime.weapons;

public enum Modifications {
	
	SCHALLD�MPFER(new Amplifier(){
		@Override
		public float amplify(float value) {
			return value/5;
		}		
	});
	
	public Amplifier amplifier;
	
	Modifications(Amplifier amplifier){
		
	}
}
