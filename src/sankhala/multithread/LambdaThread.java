package sankhala.multithread;

public class LambdaThread {

	public static void main(String[] args) {

		// Before Java 8, you had to use a bulky anonymous inner class like this

		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				for (int i = 0; i < 10; i++) {
					System.out.println("Numbers:: " + i);
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		});
		t1.start();

		// Using Lambda

		Thread t2 = new Thread(() -> {

			for (int i = 0; i < 10; i++) {
				System.out.println("Numbers:: " + i);
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});
		t2.start();

	}

}
