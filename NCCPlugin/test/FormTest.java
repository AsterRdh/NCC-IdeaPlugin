import javax.swing.*;
import java.awt.*;

public class FormTest {
    private JPanel panel;

    private JButton Button1;
    private JButton Button2;
    private JButton Button3;

    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;

    public FormTest() {
        panel = new JPanel();
        panel.setBackground(Color.CYAN);
        GridBagLayout gridLayout = new GridBagLayout();
        panel.setLayout(gridLayout);

        JPanel panel1 = new JPanel(new GridLayout(3,3));

        textField1 = new JTextField();
        textField2 = new JTextField();
        textField3 = new JTextField();
        Button1 = new JButton("预览");
        Button2 = new JButton("预览");
        Button3 = new JButton("预览");
        JLabel javaCodePathLabel = new JLabel("后端补丁地址");
        JLabel jsCodePathLabel = new JLabel("前端编译文件地址");
        JLabel patchLibPathLabel = new JLabel("补丁库地址");

        panel1.add(javaCodePathLabel);
        panel1.add(textField1);
        panel1.add(Button1);

        panel1.add(jsCodePathLabel);
        panel1.add(textField2);
        panel1.add(Button2);

        panel1.add(patchLibPathLabel);
        panel1.add(textField3);
        panel1.add(Button3);

        GridBagConstraints c1 = new GridBagConstraints();
        c1.gridwidth=1;c1.gridheight=1;
        gridLayout.setConstraints(panel1, c1);
        panel.add(panel1);

        //GridBagConstraints c2 = new GridBagConstraints();
        c1.gridwidth=1;c1.gridheight=1;
        c1.fill=GridBagConstraints.BOTH;
        JPanel panel2=new JPanel();
        panel2.add(new JLabel("123"));
        gridLayout.setConstraints(panel2, c1);
        panel.add(panel2);

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("AsterToolWindowContent");
        frame.setContentPane(new FormTest().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
