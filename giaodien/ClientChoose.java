package giaodien;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextPane;
import javax.swing.JTextArea;

import connect.*;
import java.awt.SystemColor;


public class ClientChoose implements ActionListener {

	private JFrame frame;
	private int cbb;
	private JComboBox comboBox = new JComboBox();
	private JButton btnOk;
	private JButton btnExit;
	private JTable table;
	private JTextField textField;
	private String tableSelected = "nuocdongchai";
	private int idSelected = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientChoose window = new ClientChoose();
					window.loadTable();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientChoose() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 11));
		frame.setBounds(100, 100, 447, 570);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(0, 0, 431, 47);
		comboBox.setBounds(23, 75, 153, 20);
		comboBox.addActionListener(this);
		
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"\u0110\u1ED3 u\u1ED1ng \u0111\u00F3ng chai", "M\u00EC, ph\u1EDF"}));
		
		btnOk = new JButton("Đặt hàng");
		btnOk.setBounds(85, 471, 91, 23);
		btnOk.addActionListener(this);
		btnExit = new JButton("Thoát");
		btnExit.setBounds(265, 471, 84, 23);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(54, 113, 330, 313);
		
		JTextArea txtrQuKhchVui = new JTextArea();
		txtrQuKhchVui.setBounds(75, 437, 210, 21);
		txtrQuKhchVui.setBackground(Color.WHITE);
		txtrQuKhchVui.setFont(new Font("Times New Roman", Font.ITALIC, 12));
		txtrQuKhchVui.setText("Quý khách vui lòng nhập ID để đặt hàng");
		
		table = new JTable();
		table.setEnabled(false);
		scrollPane.setViewportView(table);
		table.setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(lblNewLabel);
		frame.getContentPane().add(btnOk);
		frame.getContentPane().add(btnExit);
		frame.getContentPane().add(comboBox);
		frame.getContentPane().add(scrollPane);
		frame.getContentPane().add(txtrQuKhchVui);
		
		textField = new JTextField();
		textField.setBackground(SystemColor.textHighlight);
		textField.setForeground(SystemColor.activeCaptionText);
		textField.setBounds(283, 437, 49, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
	}
	
	public void loadTable(){
		FoodData getData = new FoodData();
		getData.getData(tableSelected);
		
		DefaultTableModel tableModel = new DefaultTableModel();
		try {
			ResultSetMetaData rsMD = getData.rs.getMetaData();
			int colNumber = rsMD.getColumnCount();
			String arr[] = new String[colNumber];
			String a[] = {"ID","Tên","Giá","Số lượng"}; 
			tableModel.setColumnIdentifiers(a);
			while (getData.rs.next()){
				for (int i=0;i<colNumber;i++){
					arr[i]=getData.rs.getString(i+1);
				}
				tableModel.addRow(arr);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		table.setModel(tableModel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == comboBox){
			cbb = comboBox.getSelectedIndex();
			switch (cbb) {
				case 0:tableSelected = "nuocdongchai";break;
				case 1:tableSelected = "mi";break;
			}
			loadTable();
		}
		if (e.getSource() == btnOk){
			idSelected =Integer.parseInt(textField.getText());
			Confirm fr = new Confirm(idSelected, tableSelected);
			fr.frame.setVisible(true);
		}
		if (e.getSource() == btnExit){
			
		}
	}
}
