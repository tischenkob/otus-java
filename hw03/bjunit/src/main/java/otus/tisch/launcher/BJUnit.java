public class BJUnit implements TestLauncher {
    public static void main(String[] args) {
        BJUnit tester = new BJUnit();
        tester.setup(args[0]);
        tester.launch();
        tester.teardown();
    }
    
    void setup(String className);
    void launch();
    void teardown();
}
