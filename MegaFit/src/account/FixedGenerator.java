package account;

import java.util.Random;

public class FixedGenerator extends Random {
	
	public FixedGenerator() {
		
	}
	
	public FixedGenerator(long seed) {
		super(seed);
	}
	
	public int nextPositiveInt() {
		return next(Integer.SIZE - 1);
	}

	
}
