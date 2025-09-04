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
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.SwingConstants;
import java.awt.SystemColor;

public class Deposit extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfAmount;
	private BigDecimal currentBalance;;
	private JLabel lblUpdateBalance;
	private int formNo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Deposit frame = new Deposit(0);
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
	public Deposit(int formNo) {
		this.formNo = formNo;
		setTitle("Deposit");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("DEPOSIT");
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
		lblNewLabel.setBounds(225, 92, 141, 30);
		contentPane.add(lblNewLabel);
		
		lblUpdateBalance = new JLabel("P 0.00");
		lblUpdateBalance.setForeground(SystemColor.text);
		lblUpdateBalance.setHorizontalAlignment(SwingConstants.CENTER);
		lblUpdateBalance.setFont(new Font("Sans Serif Collection", Font.BOLD, 30));
		lblUpdateBalance.setBounds(156, 165, 287, 30);
		contentPane.add(lblUpdateBalance);
		
		JLabel lblNewLabel_2 = new JLabel("Enter Amount");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(156, 233, 117, 19);
		contentPane.add(lblNewLabel_2);
		
		tfAmount = new JTextField();
		tfAmount.setFont(new Font("SansSerif", Font.PLAIN, 16));
		tfAmount.setBounds(156, 259, 287, 30);
		contentPane.add(tfAmount);
		tfAmount.setColumns(10);
		
		JButton btnDeposit = new JButton("Deposit");
		btnDeposit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				depositMoney();
			}
		});
		btnDeposit.setFont(new Font("SansSerif", Font.BOLD, 18));
		btnDeposit.setBackground(new Color(85, 221, 51));
		btnDeposit.setBounds(156, 324, 287, 30);
		contentPane.add(btnDeposit);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tfAmount.setText("");
			}
		});
		btnClear.setFont(new Font("SansSerif", Font.BOLD, 18));
		btnClear.setBounds(156, 366, 287, 30);
		contentPane.add(btnClear);
		
		JButton btnHome = new JButton("Back to Home");
		btnHome.setBackground(new Color(224, 255, 255));
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Home home = new Home(formNo);
				home.setVisible(true);
				dispose();
			}
		});
		btnHome.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnHome.setBounds(582, 516, 152, 36);
		contentPane.add(btnHome);
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.setIcon(new ImageIcon("C:\\Users\\63930\\Downloads\\Java\\atm_screen (1).png"));
		lblNewLabel_3.setBounds(0, 0, 786, 563);
		contentPane.add(lblNewLabel_3);
		
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
	            lblUpdateBalance.setText("P " + currentBalance.toString());
	        }
	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(this, "Error loading balance: " + ex.getMessage());
	    }
	}

	private void depositMoney() {
	    try {
	        BigDecimal depositAmount = new BigDecimal(tfAmount.getText());
	        if (depositAmount.compareTo(BigDecimal.ZERO) <= 0) {
	            JOptionPane.showMessageDialog(this, "Invalid amount!", "Error", JOptionPane.WARNING_MESSAGE);
	            return;
	        }

	        try (Connection conn = DBConnection.getConnection()) {
	            // Update balance in users table
	            String sql = "UPDATE users SET balance = balance + ? WHERE form_no = ?";
	            PreparedStatement stmt = conn.prepareStatement(sql);
	            stmt.setBigDecimal(1, depositAmount);
	            stmt.setInt(2, formNo);
	            stmt.executeUpdate();

	            // Log transaction
	            String transSql = "INSERT INTO transactions (card_number, type, amount, balance, date) " +
	                              "SELECT card_number, 'Deposit', ?, balance, CURRENT_TIMESTAMP FROM users WHERE form_no = ?";
	            PreparedStatement transStmt = conn.prepareStatement(transSql);
	            transStmt.setBigDecimal(1, depositAmount);
	            transStmt.setInt(2, formNo);
	            transStmt.executeUpdate();
	        }

	        loadBalance(); // Refresh balance
	        JOptionPane.showMessageDialog(this, "Deposit successful! New Balance: P " + currentBalance);
	        tfAmount.setText("");

	    } catch (NumberFormatException ex) {
	        JOptionPane.showMessageDialog(this, "Please enter a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
}
