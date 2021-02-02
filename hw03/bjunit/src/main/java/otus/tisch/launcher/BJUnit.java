package otus.tisch.launcher;

public class BJUnit implements TestLauncher {
    public static void main(String[] args) {
        BJUnit tester = new BJUnit();
        tester.setup(args[0]);
        tester.launch();
        tester.teardown();
    }
    
    public void setup(String className){};
    public void launch(){};
    public void teardown(){};
}
