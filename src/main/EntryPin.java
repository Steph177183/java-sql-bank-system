package main;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class EntryPin extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPasswordField jpfPin;
    private JTextField tfCardNo;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    EntryPin frame = new EntryPin();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public EntryPin() {
        setTitle("ATM Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Enter PIN");
        lblNewLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblNewLabel.setBounds(102, 253, 108, 14);
        contentPane.add(lblNewLabel);

        jpfPin = new JPasswordField();
        jpfPin.setFont(new Font("SansSerif", Font.PLAIN, 14));
        jpfPin.setBounds(102, 275, 275, 23);
        contentPane.add(jpfPin);

        JButton btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnLogin.setBounds(102, 342, 116, 29);
        contentPane.add(btnLogin);
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String enteredPin = new String(jpfPin.getPassword());
                String enteredCard = tfCardNo.getText();

                if (enteredCard.isEmpty() || enteredPin.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Card Number and PIN are required!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try (Connection conn = DBConnection.getConnection()) {
                    String sql = "SELECT form_no FROM users WHERE card_number = ? AND pin = ?";
                    PreparedStatement pst = conn.prepareStatement(sql);
                    pst.setString(1, enteredCard);
                    pst.setString(2, enteredPin);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next()) {
                        JOptionPane.showMessageDialog(null, "Login Successful!");
                        int formNo = rs.getInt("form_no");
                        Home home = new Home(formNo);
                        home.setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid Card Number or PIN!", "Error", JOptionPane.ERROR_MESSAGE);
                        jpfPin.setText("");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
            }
        });

        JButton btnClearPin = new JButton("Clear");
        btnClearPin.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnClearPin.setBounds(261, 342, 116, 29);
        contentPane.add(btnClearPin);
        btnClearPin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jpfPin.setText("");
                tfCardNo.setText("");
            }
        });

        JButton btnExit = new JButton("Exit");
        btnExit.setBounds(687, 529, 89, 23);
        btnExit.setFont(new Font("SansSerif", Font.BOLD, 14));
        contentPane.add(btnExit);
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Welcome welcome = new Welcome();
                welcome.setVisible(true);
                dispose();
            }
        });

        JCheckBox chckPin = new JCheckBox("Show PIN");
        chckPin.setBounds(278, 305, 99, 23);
        contentPane.add(chckPin);
        chckPin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (chckPin.isSelected()) {
                    jpfPin.setEchoChar('\0');
                } else {
                    jpfPin.setEchoChar('•');
                }
            }
        });

        JLabel lblNewLabel_2 = new JLabel("AUTHENTICATION");
        lblNewLabel_2.setFont(new Font("SansSerif", Font.BOLD, 32));
        lblNewLabel_2.setBounds(102, 107, 306, 42);
        contentPane.add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Enter Card No");
        lblNewLabel_3.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblNewLabel_3.setBounds(102, 199, 99, 14);
        contentPane.add(lblNewLabel_3);

        tfCardNo = new JTextField();
        tfCardNo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tfCardNo.setBounds(102, 219, 275, 23);
        contentPane.add(tfCardNo);
        tfCardNo.setColumns(10);

        JButton btnSignUp = new JButton("Sign Up");
        btnSignUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SignUp signup = new SignUp();
                signup.setVisible(true);
                dispose();
            }
        });
        btnSignUp.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnSignUp.setBounds(100, 381, 277, 29);
        contentPane.add(btnSignUp);

        JLabel lblNewLabel_1 = new JLabel("");
        try {
            lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\63930\\Downloads\\Java\\atm_bg.png"));
        } catch (Exception ex) {
            System.err.println("Error loading image: " + ex.getMessage());
        }
        lblNewLabel_1.setBounds(0, 0, 786, 563);
        contentPane.add(lblNewLabel_1);
    }
}