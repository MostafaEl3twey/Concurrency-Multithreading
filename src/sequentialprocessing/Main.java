package sequentialprocessing;

class Runner1 {
    public void execute() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Runner1 " + i);
        }
    }
}

class Runner2 {
    public void execute() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Runner2 " + i);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        // Sequential Processing
        Runner1 runner1 = new Runner1();//1
        Runner2 runner2 = new Runner2();//2

        runner1.execute();//3
        runner2.execute();//4
    }
}