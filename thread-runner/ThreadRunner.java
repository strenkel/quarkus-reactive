import java.time.LocalDate;

public class ThreadRunner {

public static void main(String args[]){  

    long start = System.currentTimeMillis();

    for (int i = 0; i < 100_000; i++) {
      new Thread(() -> {
        try {
          var millis = System.currentTimeMillis() - start;
          System.out.println("Millis: " + millis + " " + Thread.currentThread());
          Thread.sleep(10_000); // 10 sec
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }).start();
    }
  }

}
