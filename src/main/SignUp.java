package main;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class SignUp extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField tfFname;
    private JTextField tfAddress;
    private JTextField tfPnumber;
    private JTextField tfEmail;
    private JTextField tfBirth;
    private JPasswordField pfPin;
    private JPasswordField pfCPin;
    private JButton btnRegister;
    private JButton btnClear;
    private JLabel lblNewLabel_8;
    private JButton btnExit;
    private JLabel lblNewLabel_9;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SignUp frame = new SignUp();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public SignUp() {
        setTitle("Sign Up");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setLocationRelativeTo(null);
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel_1 = new JLabel("Full Name");
        lblNewLabel_1.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblNewLabel_1.setBounds(173, 138, 90, 14);
        contentPane.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Address");
        lblNewLabel_2.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblNewLabel_2.setBounds(173, 181, 90, 14);
        contentPane.add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Phone Number");
        lblNewLabel_3.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblNewLabel_3.setBounds(173, 231, 119, 14);
        contentPane.add(lblNewLabel_3);

        JLabel lblNewLabel_4 = new JLabel("Email");
        lblNewLabel_4.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblNewLabel_4.setBounds(173, 285, 90, 14);
        contentPane.add(lblNewLabel_4);

        JLabel lblNewLabel_5 = new JLabel("Date of Birth");
        lblNewLabel_5.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblNewLabel_5.setBounds(173, 328, 90, 14);
        contentPane.add(lblNewLabel_5);

        JLabel lblNewLabel_6 = new JLabel("PIN");
        lblNewLabel_6.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblNewLabel_6.setBounds(173, 379, 90, 14);
        contentPane.add(lblNewLabel_6);

        JLabel lblNewLabel_7 = new JLabel("Confirm PIN");
        lblNewLabel_7.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblNewLabel_7.setBounds(173, 429, 90, 14);
        contentPane.add(lblNewLabel_7);

        tfFname = new JTextField();
        tfFname.setBounds(293, 137, 312, 20);
        contentPane.add(tfFname);
        tfFname.setColumns(10);

        tfAddress = new JTextField();
        tfAddress.setBounds(293, 180, 312, 20);
        contentPane.add(tfAddress);
        tfAddress.setColumns(10);

        tfPnumber = new JTextField();
        tfPnumber.setBounds(293, 230, 312, 20);
        contentPane.add(tfPnumber);
        tfPnumber.setColumns(10);

        tfEmail = new JTextField();
        tfEmail.setBounds(293, 284, 312, 20);
        contentPane.add(tfEmail);
        tfEmail.setColumns(10);

        tfBirth = new JTextField();
        tfBirth.setBounds(293, 327, 312, 20);
        contentPane.add(tfBirth);
        tfBirth.setColumns(10);

        pfPin = new JPasswordField();
        pfPin.setBounds(293, 378, 312, 20);
        contentPane.add(pfPin);

        pfCPin = new JPasswordField();
        pfCPin.setBounds(293, 428, 312, 20);
        contentPane.add(pfCPin);

        btnRegister = new JButton("Register");
        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String fullName = tfFname.getText();
                String address = tfAddress.getText();
                String phone = tfPnumber.getText();
                String email = tfEmail.getText();
                String birth = tfBirth.getText();
                String pin = new String(pfPin.getPassword());
                String confirmPin = new String(pfCPin.getPassword());

                if (fullName.isEmpty() || address.isEmpty() || phone.isEmpty() ||
                        email.isEmpty() || birth.isEmpty() || pin.isEmpty() || confirmPin.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "All fields are required. Please complete the form.",
                            "Validation Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (!pin.equals(confirmPin)) {
                    JOptionPane.showMessageDialog(null, "PINs do not match!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validate date format
                try {
                    java.sql.Date.valueOf(birth);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid date format! Use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String cardNo = String.format("%012d", (long)(Math.random() * 1_000_000_000_000L));

                try (Connection conn = DBConnection.getConnection()) {
                    String sql = "INSERT INTO users (full_name, address, phone_number, email, date_of_birth, card_number, pin) VALUES (?,?,?,?,?,?,?)";
                    PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                    pst.setString(1, fullName);
                    pst.setString(2, address);
                    pst.setString(3, phone);
                    pst.setString(4, email);
                    pst.setDate(5, java.sql.Date.valueOf(birth));
                    pst.setString(6, cardNo);
                    pst.setString(7, pin);

                    pst.executeUpdate();

                    ResultSet rs = pst.getGeneratedKeys();
                    int formNo = -1;
                    if (rs.next()) {
                        formNo = rs.getInt(1);
                    }

                    String fetchSql = "SELECT * FROM users WHERE form_no = ?";
                    PreparedStatement fetchPst = conn.prepareStatement(fetchSql);
                    fetchPst.setInt(1, formNo);
                    ResultSet userRs = fetchPst.executeQuery();

                    if (userRs.next()) {
                        AccountDetails acc = new AccountDetails(
                                userRs.getInt("form_no"),
                                userRs.getString("full_name"),
                                userRs.getString("address"),
                                userRs.getString("phone_number"),
                                userRs.getString("email"),
                                userRs.getDate("date_of_birth").toString(),
                                userRs.getString("card_number"),
                                userRs.getBigDecimal("balance").toString(),
                                userRs.getTimestamp("registration_date").toString()
                        );
                        acc.setVisible(true);
                        dispose();
                    }

                    JOptionPane.showMessageDialog(null, "Registration Successful!\nYour Card Number: " + cardNo);

                } catch (SQLIntegrityConstraintViolationException ex) {
                    JOptionPane.showMessageDialog(null,
                            "This email or phone number is already registered.",
                            "Registration Error", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,
                            "A database error occurred while processing your request. Please try again.",
                            "Database Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "Something went wrong. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        btnRegister.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnRegister.setBounds(173, 474, 195, 33);
        contentPane.add(btnRegister);

        btnClear = new JButton("Clear");
        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tfFname.setText("");
                tfAddress.setText("");
                tfPnumber.setText("");
                tfEmail.setText("");
                tfBirth.setText("");
                pfPin.setText("");
                pfCPin.setText("");
            }
        });
        btnClear.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnClear.setBounds(421, 474, 184, 33);
        contentPane.add(btnClear);

        lblNewLabel_8 = new JLabel("APPLICATION FORM");
        lblNewLabel_8.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblNewLabel_8.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_8.setBounds(173, 61, 432, 38);
        contentPane.add(lblNewLabel_8);

        btnExit = new JButton("Exit");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EntryPin entryPin = new EntryPin();
                entryPin.setVisible(true);
                dispose();
            }
        });
        btnExit.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnExit.setBounds(673, 529, 89, 23);
        contentPane.add(btnExit);

        lblNewLabel_9 = new JLabel("New label");
        try {
            lblNewLabel_9.setIcon(new ImageIcon("C:\\Users\\63930\\Downloads\\Java\\form.png"));
        } catch (Exception ex) {
            System.err.println("Error loading image: " + ex.getMessage());
        }
        lblNewLabel_9.setBounds(0, 0, 786, 563);
        contentPane.add(lblNewLabel_9);
    }
}