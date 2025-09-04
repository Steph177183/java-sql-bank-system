package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.SystemColor;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;

public class CheckBalance extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblCBalance = new JLabel();
	private int formNo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CheckBalance frame = new CheckBalance(0);
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
	public CheckBalance(int formNo) {
		this.formNo = formNo;	
		setTitle("Check Balance");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblCBalance = new JLabel("Your current balance is P 0.00");
		lblCBalance.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblCBalance.setHorizontalAlignment(SwingConstants.CENTER);
		lblCBalance.setForeground(SystemColor.text);
		lblCBalance.setBounds(134, 176, 328, 23);
		contentPane.add(lblCBalance);
		
		JButton btnExit = new JButton("Back to Home");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Home home = new Home(formNo);
				home.setVisible(true);
				dispose();
			}
		});
		btnExit.setBackground(new Color(224, 255, 255));
		btnExit.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnExit.setBounds(238, 359, 131, 29);
		contentPane.add(btnExit);
		
		JLabel lblNewLabel_1 = new JLabel("CHECK BALANCE");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("SansSerif", Font.BOLD, 30));
		lblNewLabel_1.setBounds(167, 95, 261, 29);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\63930\\Downloads\\Java\\atm_screen (1).png"));
		lblNewLabel.setBounds(0, 0, 786, 563);
		contentPane.add(lblNewLabel);
		
		loadBalance(lblCBalance);
	}
	
	private void loadBalance(JLabel lblCBalance) {
		try(Connection conn = DBConnection.getConnection()) {
			String sql = "SELECT balance FROM users WHERE form_no = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, formNo);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				lblCBalance.setText("Your current balance is P" + rs.getBigDecimal("balance").toString());
			}
		}catch(SQLException ex) {
			JOptionPane.showMessageDialog(this, "Erro loading balance " + ex.getMessage());
		}
	}
	
	
}