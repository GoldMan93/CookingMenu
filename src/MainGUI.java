import javax.swing.*;
import java.awt.*;

public class MainGUI implements GUI {
    private final JFrame frame = new JFrame();

    public MainGUI() {

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3,1,10,20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        JButton courseButton = new JButton("菜单");
        JButton newButton = new JButton("创建新菜");
        JButton closeButton = new JButton("退出");


        mainPanel.add(courseButton);

        courseButton.setFont(GUI.defaultFont);
        courseButton.addActionListener(e -> {
            Main.mainGUI.setVisible(false);
            Main.courseGUI.setVisible(true);
        });
        mainPanel.add(newButton);
        newButton.setFont(GUI.defaultFont);
        newButton.addActionListener(e -> {
            Main.mainGUI.setVisible(false);
            Main.createGUI.setVisible(true);
        });

        mainPanel.add(closeButton);
        closeButton.setFont(GUI.defaultFont);
        closeButton.addActionListener(e -> {
            frame.dispose(); // Close the JFrame
            System.exit(0);
        });

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("菜");
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(false);

    }

    @Override
    public void setVisible(boolean visible) {
        this.frame.setVisible(visible);
    }



}
