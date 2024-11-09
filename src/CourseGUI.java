import Course.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.Collator;
import java.util.*;
import java.util.List;

public class CourseGUI implements GUI{
    private int count = 1;

    private final JFrame frame = new JFrame();
    private final JPanel panel = new JPanel();

    private Course currentCourse;
    private final List<Course> allCourses = new ArrayList<>();
    private final List<String> tags = new ArrayList<>();

    private JComboBox<Course> courseDropdown;
    private JComboBox<String> tagDropDown;
    private final JScrollPane ingredientPane = new JScrollPane();
    private final JCheckBox foodCheck = new JCheckBox("只显示食材");
    private final JCheckBox spiceCheck = new JCheckBox("只显示香料");
    private final JButton showSteps = new JButton("显示步骤");
    private final JButton incButton = new JButton("+");
    private final JButton decButton = new JButton("-");
    private final JLabel countLabel = new JLabel(count + " People");
    public final JButton backButton = new JButton("回到菜单");


    public CourseGUI() {
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        panel.setLayout(new GridLayout(2, 2,10,10));

        this.importAllCourse();
        currentCourse = allCourses.get(0);
        this.updateIngredientTable();
        this.tags.add("全部");
        this.getAllTags();

        //0
        this.firstBox();

        //1
        this.secondBox();

        //2
        this.thirdBox();

        //3
        this.fourthBox();

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("菜");
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(false);
    }

    //1,0
    public void firstBox() {
        JPanel dropPanel = new JPanel();
        dropPanel.setLayout(new GridLayout(4,0));
        courseDropdown = new JComboBox<>(allCourses.toArray(new Course[0]));
        dropPanel.add(courseDropdown);
        courseDropdown.addActionListener(new courseDropListener());
        courseDropdown.setFont(GUI.defaultFont);
        courseDropdown.setPreferredSize(new Dimension(200, 30));
        dropPanel.add(new JPanel());
        dropPanel.add(showSteps);
        showSteps.addActionListener(e -> {
            StringBuilder message = new StringBuilder();
            count = 1;
            message.append("<html>");
            for (String step : currentCourse.getCookingSteps()) {
                message.append("<br>").append(count).append(": ").append(step).append("</br>");
                count++;
            }
            message.append("</html>");
            JLabel messageLabel = new JLabel(String.valueOf(message));
            messageLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 18));
            JOptionPane.showMessageDialog(frame, messageLabel);
        });
        showSteps.setPreferredSize(new Dimension(120, 30));
        panel.add(dropPanel);
    }

    //0,1
    public void secondBox() {
        JPanel secondBoxPanel = new JPanel();
        secondBoxPanel.setLayout(new GridLayout(4,1));
        //0,0
        tagDropDown = new JComboBox<>(this.tags.toArray(new String[0]));
        secondBoxPanel.add(tagDropDown);
        tagDropDown.addActionListener(new tagDropListener());
        tagDropDown.setFont(GUI.defaultFont);
        tagDropDown.setPreferredSize(new Dimension(200, 30));

        //1,0
        secondBoxPanel.add(new JPanel());

        //2,0
        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new GridLayout(3,3));
        checkBoxPanel.add(new JPanel());
        checkBoxPanel.add(new JPanel());
        checkBoxPanel.add(new JPanel());
        checkBoxPanel.add(foodCheck);
        foodCheck.addActionListener(e -> {
            if (spiceCheck.isSelected()) {
                spiceCheck.setSelected(false);
            }
            updateIngredientTable();
        });
        foodCheck.setFocusable(false);
        checkBoxPanel.add(new JPanel());
        checkBoxPanel.add(spiceCheck);
        spiceCheck.addActionListener(e -> {
            if (foodCheck.isSelected()) {
                foodCheck.setSelected(false);
            }
            updateIngredientTable();
        });
        spiceCheck.setFocusable(false);
        checkBoxPanel.add(new JPanel());
        checkBoxPanel.add(new JPanel());
        checkBoxPanel.add(new JPanel());
        secondBoxPanel.add(checkBoxPanel);
        panel.add(secondBoxPanel);
    }

    //1,0
    public void thirdBox() {
        ingredientPane.setFont(GUI.defaultFont);
        ingredientPane.setPreferredSize(new Dimension(150, 150));
        panel.add(ingredientPane);
    }

    //1,1
    public void fourthBox() {
        JPanel countPanel = new JPanel();
        countPanel.setLayout(new GridLayout(4,3));
        countPanel.add(new JPanel());
        countPanel.add(new JPanel());
        countPanel.add(new JPanel());
        countPanel.add(incButton);
        incButton.addActionListener(new countListener());
        incButton.setFocusable(false);
        countPanel.add(countLabel);
        countPanel.add(decButton);
        decButton.addActionListener(new countListener());
        decButton.setFocusable(false);
        countPanel.add(new JPanel());
        countPanel.add(new JPanel());
        countPanel.add(new JPanel());
        countPanel.add(new JPanel());
        countPanel.add(new JPanel());
        countPanel.add(backButton);
        backButton.setFont(GUI.defaultFont);
        backButton.addActionListener(e -> {
            Main.mainGUI.setVisible(true);
            Main.courseGUI.setVisible(false);
        });
        panel.add(countPanel);
    }

    public void importAllCourse() {
        File folder = new File("Menu");
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                this.allCourses.add(new GeneralCourse(file));
            }
        } else {
            JOptionPane.showMessageDialog(frame, "餐单里(Menu)没有菜谱");
        }
    }

    public void getAllTags() {
        for (Course course : allCourses) {
            if (course.getTags() != null) {
                for (String tag : course.getTags()) {
                    if (!this.tags.contains(tag)) {
                        this.tags.add(tag);
                    }
                }
            }
        }

        this.tags.sort(new Comparator<>() {
            final Collator collator = Collator.getInstance(Locale.CHINA);
            @Override
            public int compare(String o1, String o2) {
                if (o1.equals("全部")) {
                    return -1;
                } else if (o2.equals("全部")) {
                    return 1;
                } else {
                    return collator.compare(o1, o2);
                }
            }
        });
    }

    public void updateIngredientTable() {
        JTable ingredientTable = null;
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("名字");
        model.addColumn("量");

        Map<String, Double> currentIngredients = currentCourse.getAllIngredient();
        if (foodCheck.isSelected()) {
            if (!spiceCheck.isSelected()) {
                currentIngredients = currentCourse.getFoodIngredient();
            }
        }
        if (spiceCheck.isSelected()) {
            currentIngredients = currentCourse.getSpiceIngredient();
        }

        for (String key : currentIngredients.keySet()) {
            String value = (currentIngredients.get(key) * count) + " g";
            model.addRow(new Object[]{key, value});
            ingredientTable = new JTable(model);
        }

        ingredientPane.setViewportView(ingredientTable);
    }

    private class courseDropListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //JComboBox<Course> dropdown = (JComboBox<Course>)e.getSource();
            currentCourse = ((Course) Objects.requireNonNull(courseDropdown.getSelectedItem()));
            updateIngredientTable();
        }
    }

    private class tagDropListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Course> newCourses = new ArrayList<>();
            String currentTag = (String) tagDropDown.getSelectedItem();
            if (Objects.equals(currentTag, "全部")) {
                newCourses = allCourses;
            } else {
                for (Course course : allCourses) {
                    boolean find = false;
                    if (course.getTags() != null) {
                        for (String tag: course.getTags()) {
                            if (Objects.equals(tag, currentTag)) {
                                find = true;
                            }
                        }
                    }
                    if (find) {
                        newCourses.add(course);
                    }
                }
            }
            courseDropdown.setModel(new DefaultComboBoxModel<>(newCourses.toArray(new Course[0])));
            currentCourse = (Course) courseDropdown.getSelectedItem();
            updateIngredientTable();
        }
    }

    private class countListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("+")) {

                count++;
            } else if (e.getActionCommand().equals("-")) {
                if (count > 1) {
                    count--;
                }
            }
            countLabel.setText(count + " People");
            updateIngredientTable();
        }
    }

    @Override
    public void setVisible(boolean visible) {
        frame.setVisible(visible);

        //refresh
        this.allCourses.clear();
        this.importAllCourse();
        courseDropdown.setModel(new DefaultComboBoxModel<>(this.allCourses.toArray(new Course[0])));
        currentCourse = (Course) courseDropdown.getSelectedItem();
        this.tags.clear();
        this.tags.add("全部");
        this.getAllTags();
        this.tagDropDown.setModel(new DefaultComboBoxModel<>(this.tags.toArray(new String[0])));
        this.updateIngredientTable();
    }
}
