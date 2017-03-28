package sample;


public class Question3 implements Runnable{

    String name;
    SyncPrinter printer;
    public Question3(String name, SyncPrinter printer){
      this.name = name;
      this.printer = printer;
    }
    @Override
    public void run() {
      printer.print(name);
      printer.print(name);
    }


    public static void main(String[] args) {
      SyncPrinter p = new SyncPrinter();
      new Thread(new Question3("A",p)).start();
      new Thread(new Question3("B",p)).start();
    }
  }

class SyncPrinter{
  String lastPrint;
  public synchronized void print(String str){
    while(str.equals(lastPrint)){
      try {
        this.wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    System.out.println(str);
    lastPrint = str;

    this.notifyAll();
  }
}
