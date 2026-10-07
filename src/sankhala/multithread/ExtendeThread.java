package sankhala.multithread;

class Runner extends Thread{
	public void run() {
		for(int i=0;i<10;i++) {
			System.out.println("Numbers:: "+i);
		}
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

public class ExtendeThread {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Runner thread1=new Runner();
		thread1.start();
		Runner thread2=new Runner();
		thread2.start();
		
	}

}
