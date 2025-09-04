package main;

import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.awt.SystemColor;
import java.sql.*;


public class Withdraw extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfAmount;
	private int formNo;
	private BigDecimal currentBalance;
	private JLabel lblcurrentBalance;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Withdraw frame = new Withdraw(0);
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
	public Withdraw(int formNo) {
		this.formNo = formNo;
		setTitle("Withdraw");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("WITHDRAW");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
		lblNewLabel.setBounds(213, 84, 181, 47);
		contentPane.add(lblNewLabel);
		
		lblcurrentBalance = new JLabel("Maximum withdrawal is P 0.00");
		lblcurrentBalance.setForeground(SystemColor.text);
		lblcurrentBalance.setHorizontalAlignment(SwingConstants.CENTER);
		lblcurrentBalance.setFont(new Font("Sans Serif Collection", Font.BOLD, 14));
		lblcurrentBalance.setBounds(163, 162, 286, 33);
		contentPane.add(lblcurrentBalance);
		
		JLabel lblNewLabel_2 = new JLabel("Enter amount ");
		lblNewLabel_2.setForeground(SystemColor.text);
		lblNewLabel_2.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(162, 218, 191, 20);
		contentPane.add(lblNewLabel_2);
		
		tfAmount = new JTextField();
		tfAmount.setFont(new Font("SansSerif", Font.PLAIN, 16));
		tfAmount.setBounds(162, 242, 276, 25);
		contentPane.add(tfAmount);
		tfAmount.setColumns(10);
		
		JButton btnExit = new JButton("Back to Home");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Home home = new Home(formNo);
				home.setVisible(true);
				dispose();
			}
		});
		btnExit.setBounds(612, 513, 128, 39);
		btnExit.setFont(new Font("SansSerif", Font.BOLD, 14));
		contentPane.add(btnExit);
		
		JButton btnWithdraw = new JButton("Withdraw");
		btnWithdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				withdrawMoney();
			}
		});
		btnWithdraw.setFont(new Font("SansSerif", Font.BOLD, 18));
		btnWithdraw.setBounds(162, 303, 276, 33);
		contentPane.add(btnWithdraw);

		JButton btnClear = new JButton("New button");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tfAmount.setText("");
			}
		});
		btnClear.setFont(new Font("SansSerif", Font.BOLD, 18));
		btnClear.setText("Clear");
		btnClear.setBounds(162, 347, 276, 33);
		contentPane.add(btnClear);
		
		
		JLabel lblcurrentAmount = new JLabel("New label");
		lblcurrentAmount.setIcon(new ImageIcon("C:\\Users\\63930\\Downloads\\Java\\atm_screen (1).png"));
		lblcurrentAmount.setBounds(0, 0, 786, 563);
		contentPane.add(lblcurrentAmount);
		
		loadBalance();
		
	}
	
	private void loadBalance() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT balance FROM users WHERE form_no = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, formNo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                currentBalance = rs.getBigDecimal("balance");
                lblcurrentBalance.setText("Maximum withdrawal is P " + currentBalance.toString());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading balance: " + ex.getMessage());
        }
    }

    private void withdrawMoney() {
        try {
            BigDecimal withdrawAmount = new BigDecimal(tfAmount.getText());
            if (withdrawAmount.compareTo(BigDecimal.ZERO) <= 0) {
                JOptionPane.showMessageDialog(this, "Invalid amount!", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (withdrawAmount.compareTo(currentBalance) > 0) {
                JOptionPane.showMessageDialog(this, "Insufficient balance!", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try (Connection conn = DBConnection.getConnection()) {
                // Update balance in users table
                String sql = "UPDATE users SET balance = balance - ? WHERE form_no = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setBigDecimal(1, withdrawAmount);
                stmt.setInt(2, formNo);
                stmt.executeUpdate();

                // Log transaction
                String transSql = "INSERT INTO transactions (card_number, type, amount, balance, date) " +
                        "SELECT card_number, 'Withdraw', ?, balance, CURRENT_TIMESTAMP FROM users WHERE form_no = ?";
                PreparedStatement transStmt = conn.prepareStatement(transSql);
                transStmt.setBigDecimal(1, withdrawAmount);
                transStmt.setInt(2, formNo);
                transStmt.executeUpdate();
            }

            loadBalance(); // Refresh balance
            JOptionPane.showMessageDialog(this, "Withdrawal successful! New Balance: P " + currentBalance);
            tfAmount.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}