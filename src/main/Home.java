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
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.UIManager;

public class Home extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private int formNo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home frame = new Home(0);
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
	public Home(int formNo) {
		this.formNo = formNo;
		setTitle("Home");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("XYZ ATM");
		lblNewLabel.setBackground(new Color(240, 240, 240));
		lblNewLabel.setForeground(new Color(0, 255, 0));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Sans Serif Collection", Font.BOLD, 20));
		lblNewLabel.setBounds(241, 171, 126, 33);
		contentPane.add(lblNewLabel);
		
		JButton btnWithdraw = new JButton("WITHDRAW");
		btnWithdraw.setForeground(Color.BLACK);
		btnWithdraw.setBackground(new Color(85, 221, 51));
		btnWithdraw.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnWithdraw.setBounds(146, 293, 140, 33);
		contentPane.add(btnWithdraw);
		btnWithdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Withdraw withdraw = new Withdraw(formNo);
				withdraw.setVisible(true);
				dispose();
			}
		});
		
		
		JButton btnDeposit = new JButton("DEPOSIT");
		btnDeposit.setBackground(new Color(85, 221, 51));
		btnDeposit.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnDeposit.setBounds(146, 234, 140, 33);
		contentPane.add(btnDeposit);
		btnDeposit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Deposit deposit = new Deposit(formNo);
				deposit.setVisible(true);
				dispose();
			}
		});
		
		
		JButton btnBalance = new JButton("CHECK BALANCE");
		btnBalance.setBackground(new Color(85, 221, 51));
		btnBalance.setFont(new Font("SansSerif", Font.BOLD, 11));
		btnBalance.setBounds(146, 353, 140, 33);
		contentPane.add(btnBalance);
		btnBalance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CheckBalance chckbalance = new CheckBalance(formNo);
				chckbalance.setVisible(true);
				dispose();
			}
		});
		
		JButton btnPinChange = new JButton("PIN CHANGE");
		btnPinChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PINChange pinChange = new PINChange(formNo); // Pass formNo
                pinChange.setVisible(true);
                dispose();
			}
		});
		btnPinChange.setBackground(new Color(85, 221, 51));
		btnPinChange.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnPinChange.setBounds(313, 293, 140, 33);
		contentPane.add(btnPinChange);
		
		JButton btnStatement = new JButton("BANK STATEMENT");
		btnStatement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MiniStatement miniStatement = new MiniStatement(formNo); // Pass formNo
                miniStatement.setVisible(true);
                dispose();
			}
		});
		btnStatement.setBackground(new Color(85, 221, 51));
		btnStatement.setFont(new Font("SansSerif", Font.BOLD, 11));
		btnStatement.setBounds(313, 234, 140, 33);
		contentPane.add(btnStatement);
		
		JButton btnExit = new JButton("EXIT");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Welcome welcome = new Welcome();
				welcome.setVisible(true);
				dispose();
			}
		});
		btnExit.setBackground(new Color(85, 221, 51));
		btnExit.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnExit.setBounds(313, 353, 140, 33);
		contentPane.add(btnExit);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\63930\\Downloads\\Java\\atm_screen (1).png"));
		lblNewLabel_1.setBounds(0, 0, 786, 563);
		contentPane.add(lblNewLabel_1);
	}

}