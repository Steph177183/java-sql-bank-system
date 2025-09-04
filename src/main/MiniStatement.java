package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.sql.*;
public class MiniStatement extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tblTransaction;
	private int formNo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MiniStatement frame = new MiniStatement(0);
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
	public MiniStatement(int formNo) {
		this.formNo = formNo;
		setTitle("Mini Statement");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("TRANSACTION HISTORY");
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(186, 59, 405, 42);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Back to Home");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Home home = new Home(formNo);
				home.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setBounds(623, 529, 126, 23);
		contentPane.add(btnNewButton);
		
		JPanel panel = new JPanel();
		panel.setBounds(64, 127, 656, 248);
		contentPane.add(panel);
		panel.setLayout(null);
		
		tblTransaction = new JTable();
		
		tblTransaction.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Transaction ID", "Date", "Time", "Type", "Amount", "Balance"
			}
		));
		tblTransaction.getColumnModel().getColumn(0).setPreferredWidth(83);
		tblTransaction.getColumnModel().getColumn(1).setPreferredWidth(99);
		tblTransaction.getColumnModel().getColumn(2).setPreferredWidth(106);
		tblTransaction.getColumnModel().getColumn(3).setPreferredWidth(104);
		tblTransaction.getColumnModel().getColumn(4).setPreferredWidth(127);
		tblTransaction.getColumnModel().getColumn(5).setPreferredWidth(129);
		
		JScrollPane scrollPane = new JScrollPane(tblTransaction);
		scrollPane.setBounds(0, 0, 656, 248);
		panel.add(scrollPane);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\63930\\Downloads\\Java\\atm_bg.png"));
		lblNewLabel_1.setBounds(0, 0, 786, 563);
		contentPane.add(lblNewLabel_1);
		
		loadTransactions();
	}
	
	private void loadTransactions() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT transaction_id, date, type, amount, balance " +
                        "FROM transactions WHERE card_number = (SELECT card_number FROM users WHERE form_no = ?) " +
                        "ORDER BY date DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, formNo);
            ResultSet rs = stmt.executeQuery();

            DefaultTableModel model = (DefaultTableModel) tblTransaction.getModel();
            model.setRowCount(0); // Clear existing rows

            while (rs.next()) {
                String dateTime = rs.getTimestamp("date").toString();
                String[] dateTimeSplit = dateTime.split(" ");
                String date = dateTimeSplit[0];
                String time = dateTimeSplit[1].substring(0, 8);

                model.addRow(new Object[] {
                    rs.getInt("transaction_id"),
                    date,
                    time,
                    rs.getString("type"),
                    rs.getBigDecimal("amount").toString(),
                    rs.getBigDecimal("balance").toString()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading transactions: " + ex.getMessage());
        }
    }
	
}
