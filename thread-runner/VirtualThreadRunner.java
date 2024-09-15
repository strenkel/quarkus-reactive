public class VirtualThreadRunner {
  
  public static void main(String args[]) throws InterruptedException{  

    long start = System.currentTimeMillis();

    for (int i = 0; i < 1_000_000; i++) {
      System.out.println("===> " + i);
      Thread.startVirtualThread(() -> {
        try {
          var millis = System.currentTimeMillis() - start;
          System.out.println("Millis: " + millis + " " + Thread.currentThread());
          Thread.sleep(100_000); // 100 sec
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      });
    }
  }

}

