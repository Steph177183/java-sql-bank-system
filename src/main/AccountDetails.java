package main;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AccountDetails extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public AccountDetails(int formNo, String fullName, String address, String phone,
                         String email, String birthdate, String cardNo,
                         String balance, String regDate) {
        setTitle("Account Details");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setLocationRelativeTo(null);
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("ACCOUNT DETAILS");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblNewLabel.setBounds(263, 34, 267, 25);
        contentPane.add(lblNewLabel);

        JLabel lblFname = new JLabel("Full Name: " + fullName);
        lblFname.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblFname.setBounds(169, 88, 460, 14);
        contentPane.add(lblFname);

        JLabel lblAddress = new JLabel("Address: " + address);
        lblAddress.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblAddress.setBounds(169, 131, 460, 14);
        contentPane.add(lblAddress);

        JLabel lblPnumber = new JLabel("Phone Number: " + phone);
        lblPnumber.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblPnumber.setBounds(169, 175, 460, 14);
        contentPane.add(lblPnumber);

        JLabel lblEmail = new JLabel("Email: " + email);
        lblEmail.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblEmail.setBounds(169, 219, 460, 14);
        contentPane.add(lblEmail);

        JLabel lblBirthdate = new JLabel("Birthdate: " + birthdate);
        lblBirthdate.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblBirthdate.setBounds(169, 262, 460, 14);
        contentPane.add(lblBirthdate);

        JLabel lblCardno = new JLabel("Card Number: " + cardNo);
        lblCardno.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblCardno.setBounds(169, 305, 460, 14);
        contentPane.add(lblCardno);

        JLabel lblABalance = new JLabel("Account Balance: P" + balance);
        lblABalance.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblABalance.setBounds(169, 353, 460, 14);
        contentPane.add(lblABalance);

        JLabel lblRegDate = new JLabel("Registration Date: " + regDate);
        lblRegDate.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblRegDate.setBounds(169, 394, 460, 14);
        contentPane.add(lblRegDate);

        JLabel lblNewLabel_9 = new JLabel("For security reasons, your PIN is not displayed here. Please use your Card Number + PIN to login.");
        lblNewLabel_9.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_9.setFont(new Font("Tahoma", Font.ITALIC, 14));
        lblNewLabel_9.setBounds(95, 448, 623, 14);
        contentPane.add(lblNewLabel_9);

        JButton btnSignIn = new JButton("Sign In");
        btnSignIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EntryPin signIn = new EntryPin();
                signIn.setVisible(true);
                dispose();
            }
        });
        btnSignIn.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnSignIn.setBounds(263, 484, 267, 33);
        contentPane.add(btnSignIn);

        JButton btnExit = new JButton("Exit");
        btnExit.setBounds(670, 529, 89, 23);
        btnExit.setFont(new Font("SansSerif", Font.BOLD, 14));
        contentPane.add(btnExit);
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Welcome welcome = new Welcome();
                welcome.setVisible(true);
                dispose();
            }
        });

        JLabel lblFormNo = new JLabel("Form No." + formNo);
        lblFormNo.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblFormNo.setBounds(619, 11, 167, 14);
        contentPane.add(lblFormNo);

        JLabel lblNewLabel_10 = new JLabel("New label");
        try {
            lblNewLabel_10.setIcon(new ImageIcon("C:\\Users\\63930\\Downloads\\Java\\form.png"));
        } catch (Exception ex) {
            System.err.println("Error loading image: " + ex.getMessage());
        }
        lblNewLabel_10.setBounds(0, 0, 786, 563);
        contentPane.add(lblNewLabel_10);
    }
}