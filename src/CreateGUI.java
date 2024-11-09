import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CreateGUI implements GUI {
    private final JFrame frame = new JFrame();
    private final JTextField nameField = new JTextField();
    private final JTextField tagField = new JTextField();
    private final JTextArea foodArea = new JTextArea(5, 20);
    private final JTextArea spiceArea = new JTextArea(5, 20);
    private final JTextArea stepArea = new JTextArea(5, 20);

    public CreateGUI() {
        JButton backButton = new JButton("返回");
        JButton comfierButton = new JButton("确认");
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 10, 40));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // 1
        JLabel nameLabel = new JLabel("名字:");
        nameLabel.setFont(GUI.defaultFont);
        panel.add(nameLabel);
        panel.add(nameField);

        // 3
        JLabel tagLabel = new JLabel("标签:");
        tagLabel.setFont(GUI.defaultFont);
        JLabel tagLabel2 = new JLabel("(e.g.炸物,猪肉)");
        JPanel tagPanel = new JPanel(new GridLayout(2, 1));
        tagPanel.add(tagLabel);
        tagPanel.add(tagLabel2);
        panel.add(tagPanel);
        panel.add(tagField);

        // 4
        JLabel foodLabel = new JLabel("食材/g:");
        foodLabel.setFont(GUI.defaultFont);
        JLabel foodLabel2 = new JLabel("(e.g.猪肉:100)");
        JPanel foodPanel = new JPanel(new GridLayout(2, 1));
        foodPanel.add(foodLabel);
        foodPanel.add(foodLabel2);
        panel.add(foodPanel);
        JScrollPane foodPane = new JScrollPane(foodArea);
        foodPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(foodPane);

        // 5
        JLabel spiceLabel = new JLabel("香料/g:");
        spiceLabel.setFont(GUI.defaultFont);
        JLabel spiceLabel2 = new JLabel("(e.g.辣椒:5)");
        JPanel spicePanel = new JPanel(new GridLayout(2, 1));
        spicePanel.add(spiceLabel);
        spicePanel.add(spiceLabel2);
        panel.add(spicePanel);
        JScrollPane spicePane = new JScrollPane(spiceArea);
        spicePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(spicePane);

        // 6
        JLabel stepLabel = new JLabel("过程:");
        stepLabel.setFont(GUI.defaultFont);
        panel.add(stepLabel);
        JScrollPane stepPane = new JScrollPane(stepArea);
        stepPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(stepPane);

        // 7
        panel.add(comfierButton);
        panel.add(backButton);

        backButton.addActionListener(e -> {
            Main.mainGUI.setVisible(true);
            Main.createGUI.setVisible(false);
        });

        comfierButton.addActionListener(new writeFIle());

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("菜");
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(false);
    }

    private class writeFIle implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent a) {

            // validate
            if (nameField.getText().equals("")) {
                JOptionPane.showMessageDialog(frame, "菜单必须要有个名字");
                return;
            }

            if (tagField.getText().equals("")) {
                JOptionPane.showMessageDialog(frame, "给餐单至少一个标签\n比如：牛肉，猪肉");
                return;
            }

            if (foodArea.getText().equals("")) {
                JOptionPane.showMessageDialog(frame, "至少添加一样食材\n比如：牛肉:100");
                return;
            }

            if (spiceArea.getText().equals("")) {
                JOptionPane.showMessageDialog(frame, "至少添加一样香料\n比如：花椒:4");
                return;
            }

            if (stepArea.getText().equals("")) {
                JOptionPane.showMessageDialog(frame, "至少有一步烹饪步骤\n比如：把牛肉下锅");
                return;
            }

            String[] foodLines = foodArea.getText().split("\\n");
            for (String line : foodLines) {
                if (!line.contains(":")) {
                    JOptionPane.showMessageDialog(frame, "食材格式不正确，请确保每行输入包含一个冒号（:）");
                    return;
                }
                try {
                    String[] lineSplit = line.split(":");
                    double gram = Double.parseDouble(lineSplit[1]);
                    if (gram <= 0) {
                        JOptionPane.showMessageDialog(frame, "食材重量必须大于0");
                        return;
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frame, "食材重量必须是数字");
                    return;
                }
            }
            String[] spiceLines = spiceArea.getText().split("\\n");
            for (String line : spiceLines) {
                if (!line.contains(":")) {
                    JOptionPane.showMessageDialog(frame, "香料格式不正确，请确保每行输入包含一个冒号（:）");
                    return;
                }
                try {
                    String[] lineSplit = line.split(":");
                    double gram = Double.parseDouble(lineSplit[1]);
                    if (gram <= 0) {
                        JOptionPane.showMessageDialog(frame, "香料重量必须大于0");
                        return;
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frame, "香料重量必须是数字");
                    return;
                }
            }

            String fileName = "Menu/" + nameField.getText() + ".jasonnb";
            try {
                FileWriter fileWriter = new FileWriter(fileName, StandardCharsets.UTF_8);
                fileWriter.write("name:" + nameField.getText() + "\n\n");
                fileWriter.write("Tag:" + tagField.getText() + "\n\n");
                for (String line : foodLines) {
                    fileWriter.write("FoodIngredient:" + line + "\n");
                }
                fileWriter.write("\n");

                for (String line : spiceLines) {
                    fileWriter.write("SpiceIngredient:" + line + "\n");
                }
                fileWriter.write("\n");

                fileWriter.write("Calorie:0\n\n");
                fileWriter.write("CookingSteps:\n" + stepArea.getText());
                fileWriter.close();
                JOptionPane.showMessageDialog(frame, "已保存！");

                nameField.setText("");
                tagField.setText("");
                foodArea.setText("");
                spiceArea.setText("");
                stepArea.setText("");
                Main.mainGUI.setVisible(true);
                Main.createGUI.setVisible(false);

            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(frame, "保存失败");
                ioException.printStackTrace();
            }

        }
    }

    @Override
    public void setVisible(boolean visible) {
        this.frame.setVisible(visible);
    }

}
