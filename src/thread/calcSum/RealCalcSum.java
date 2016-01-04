package thread.calcSum;

import java.util.concurrent.Callable;

public class RealCalcSum implements Callable {
	
	private int[] target ;
	
	public RealCalcSum(int[] target) {
		this.target = target;
	}
	public RealCalcSum() {
		this.target = new int[0];
	}

	@Override
	public Object call() throws Exception {
		Thread.sleep(1000);
		//System.out.println("Calc It!!!");
		if(this.target.length==0)return 0;
		Long sum = 0L;
		int size = this.target.length;
		int k = 0;
		for (; k<size; k++) 
		{
			sum+=this.target[k];
		}
		return sum;
	}
}
