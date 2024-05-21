package day1;

import java.math.BigInteger;

public class MultithreadedCalculation {
	
	public static void main(String [] args) {
		BigInteger x1=new BigInteger("3285395");
		BigInteger y1=new BigInteger("35");

		BigInteger x2=new BigInteger("2349820");
		BigInteger y2=new BigInteger("98");

		
		try {
			BigInteger answer=calculateResult(x1,y1,x2,y2);
			System.out.println("x1^y1+x2^y2 = "+answer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    public static BigInteger calculateResult(BigInteger base1, BigInteger power1, BigInteger base2, BigInteger power2) {
        BigInteger result;
        PowerCalculatingThread thread1 = new PowerCalculatingThread(base1, power1);
        PowerCalculatingThread thread2 = new PowerCalculatingThread(base2, power2);
 
        thread1.start();
        thread2.start();
 
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
 
        result = thread1.getResult().add(thread2.getResult());
        return result;
    }

    private static class PowerCalculatingThread extends Thread {
        private BigInteger result = BigInteger.ONE;
        private BigInteger base;
        private BigInteger power;
    
        public PowerCalculatingThread(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }
    
        @Override
        public void run() {
          for(BigInteger i = BigInteger.ZERO;
                i.compareTo(power) !=0;
                i = i.add(BigInteger.ONE)) {
                result = result.multiply(base);
            }
        }
    
        public BigInteger getResult() { return result; }
    }
}