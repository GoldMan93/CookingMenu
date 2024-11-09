public class Main {
    public static MainGUI mainGUI;
    public static CourseGUI courseGUI;
    public static CreateGUI createGUI;

    public static void main(String[] args) {

        mainGUI = new MainGUI();
        courseGUI = new CourseGUI();
        createGUI = new CreateGUI();

        mainGUI.setVisible(true);
    }
}
