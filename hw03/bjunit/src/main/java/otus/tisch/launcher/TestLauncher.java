public interface TestLauncher {
    void setup(String className);
    void launch();
    void teardown();
}
