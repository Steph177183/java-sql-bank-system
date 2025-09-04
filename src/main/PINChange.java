package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;

public class PINChange extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfOldPin;
	private JTextField tfNPin;
	private JTextField tfCNPin;
	private int formNo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PINChange frame = new PINChange(0);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PINChange(int formNo) {
		this.formNo = formNo;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("CHANGE PIN");
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(202, 93, 200, 32);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Enter Old PIN");
		lblNewLabel_1.setForeground(SystemColor.text);
		lblNewLabel_1.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblNewLabel_1.setBounds(187, 169, 96, 14);
		contentPane.add(lblNewLabel_1);
		
		tfOldPin = new JTextField();
		tfOldPin.setFont(new Font("SansSerif", Font.PLAIN, 14));
		tfOldPin.setBounds(188, 196, 223, 20);
		contentPane.add(tfOldPin);
		tfOldPin.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Enter New PIN");
		lblNewLabel_2.setForeground(SystemColor.text);
		lblNewLabel_2.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblNewLabel_2.setBounds(187, 227, 106, 14);
		contentPane.add(lblNewLabel_2);
		
		tfNPin = new JTextField();
		tfNPin.setFont(new Font("SansSerif", Font.PLAIN, 14));
		tfNPin.setBounds(187, 252, 223, 20);
		contentPane.add(tfNPin);
		tfNPin.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Confirm New PIN");
		lblNewLabel_3.setForeground(SystemColor.text);
		lblNewLabel_3.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblNewLabel_3.setBounds(187, 283, 154, 14);
		contentPane.add(lblNewLabel_3);
		
		tfCNPin = new JTextField();
		tfCNPin.setFont(new Font("SansSerif", Font.PLAIN, 14));
		tfCNPin.setBounds(187, 308, 224, 20);
		contentPane.add(tfCNPin);
		tfCNPin.setColumns(10);
		
		JButton btnChangePIN = new JButton("Change PIN");
		btnChangePIN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changePin();
			}
		});
		btnChangePIN.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnChangePIN.setBounds(187, 368, 224, 23);
		contentPane.add(btnChangePIN);
		
		JButton btnExit = new JButton("Back to Home");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Home home = new Home(formNo);
				home.setVisible(true);
				dispose();
			}
		});
		btnExit.setBounds(629, 529, 125, 23);
		contentPane.add(btnExit);
		
		JLabel lblNewLabel_4 = new JLabel("New label");
		lblNewLabel_4.setIcon(new ImageIcon("C:\\Users\\63930\\Downloads\\Java\\atm_screen (1).png"));
		lblNewLabel_4.setBounds(0, 0, 786, 563);
		contentPane.add(lblNewLabel_4);
	}
	
	private void changePin() {
        String oldPin = tfOldPin.getText();
        String newPin = tfNPin.getText();
        String confirmPin = tfCNPin.getText();

        if (oldPin.isEmpty() || newPin.isEmpty() || confirmPin.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!newPin.equals(confirmPin)) {
            JOptionPane.showMessageDialog(this, "New PIN and Confirm PIN do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            // Verify old PIN
            String sql = "SELECT pin FROM users WHERE form_no = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, formNo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedPin = rs.getString("pin");
                if (!oldPin.equals(storedPin)) {
                    JOptionPane.showMessageDialog(this, "Incorrect old PIN!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(this, "User not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Update PIN
            String updateSql = "UPDATE users SET pin = ? WHERE form_no = ?";
            PreparedStatement updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setString(1, newPin);
            updateStmt.setInt(2, formNo);
            updateStmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "PIN changed successfully!");
            tfOldPin.setText("");
            tfNPin.setText("");
            tfCNPin.setText("");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
	
}