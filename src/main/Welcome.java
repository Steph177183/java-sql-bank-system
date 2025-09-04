package main;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

public class Welcome extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Welcome frame = new Welcome();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Welcome() {
        setTitle("Welcome");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("WELCOME");
        lblNewLabel.setBounds(73, 148, 184, 39);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 32));
        lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
        contentPane.add(lblNewLabel);

        JButton btnStart = new JButton("Start");
        btnStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    EntryPin entrypin = new EntryPin();
                    entrypin.setVisible(true);
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(Welcome.this, "Error opening EntryPin: " + ex.getMessage());
                }
            }
        });
        btnStart.setForeground(SystemColor.text);
        btnStart.setBackground(Color.DARK_GRAY);
        btnStart.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnStart.setBounds(73, 377, 103, 33);
        contentPane.add(btnStart);

        JLabel lblNewLabel_1 = new JLabel("Please click 'Start' to continue.");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_1.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblNewLabel_1.setBounds(73, 347, 191, 19);
        contentPane.add(lblNewLabel_1);

        JLabel lblNewLabel_4 = new JLabel("TO XYZ ATM");
        lblNewLabel_4.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 32));
        lblNewLabel_4.setBounds(73, 186, 229, 39);
        contentPane.add(lblNewLabel_4);

        JLabel lblNewLabel_5 = new JLabel("Banking made easy —");
        lblNewLabel_5.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_5.setFont(new Font("SansSerif", Font.ITALIC, 20));
        lblNewLabel_5.setBounds(73, 236, 215, 39);
        contentPane.add(lblNewLabel_5);

        JLabel lblNewLabel_6 = new JLabel("anytime, anywhere.");
        lblNewLabel_6.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_6.setFont(new Font("SansSerif", Font.ITALIC, 20));
        lblNewLabel_6.setBounds(73, 271, 229, 19);
        contentPane.add(lblNewLabel_6);

        JLabel lblNewLabel_2 = new JLabel("New label");
        try {
            lblNewLabel_2.setIcon(new ImageIcon("C:\\Users\\63930\\Downloads\\Java\\atm_bg.png"));
        } catch (Exception ex) {
            System.err.println("Error loading image: " + ex.getMessage());
        }
        lblNewLabel_2.setBounds(0, 0, 786, 563);
        contentPane.add(lblNewLabel_2);
    }
}