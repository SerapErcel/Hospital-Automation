package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.Helper;
import Model.Hasta;
import Model.User;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegisterGUI extends JFrame {

	private JPanel w_pane;
	private JTextField fld_name;
	private JTextField fld_tcno;
	private JPasswordField fld_pass;
	private Hasta hasta = new Hasta();
	private Color c1=new Color(163, 228, 215);
	private Color c0 = new Color(209, 242, 235);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterGUI frame = new RegisterGUI();
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
	public RegisterGUI() {
		setTitle("Hastane Yonetim Sistemi");
		setBackground(c0);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 312, 342);
		w_pane = new JPanel();
		w_pane.setBackground(c1);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);

		JLabel label = new JLabel("Ad Soyad");
		label.setFont(new Font("Tahoma", Font.BOLD, 14));
		label.setBounds(10, 24, 274, 14);
		w_pane.add(label);

		fld_name = new JTextField();
		fld_name.setText("");
		fld_name.setColumns(10);
		fld_name.setBounds(10, 49, 274, 20);
		w_pane.add(fld_name);

		JLabel lblTcNumarasi = new JLabel("T.C. Numarasi");
		lblTcNumarasi.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTcNumarasi.setBounds(10, 80, 274, 14);
		w_pane.add(lblTcNumarasi);

		fld_tcno = new JTextField();
		fld_tcno.setText("");
		fld_tcno.setColumns(10);
		fld_tcno.setBounds(10, 105, 274, 20);
		w_pane.add(fld_tcno);

		JLabel lblSifre = new JLabel("Sifre");
		lblSifre.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSifre.setBounds(10, 136, 274, 14);
		w_pane.add(lblSifre);

		fld_pass = new JPasswordField();
		fld_pass.setBounds(10, 161, 274, 23);
		w_pane.add(fld_pass);

		JButton btnRegister = new JButton("Kayit Ol");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_tcno.getText().length() == 0 || fld_pass.getText().length() == 0
						|| fld_name.getText().length() == 0) {
					Helper.showMsg("fill");
				} else {
					boolean control = hasta.register(fld_tcno.getText(), fld_pass.getText(), fld_name.getText());
					if (control) {
						Helper.showMsg("success");
						LoginGUI login = new LoginGUI();
						login.setVisible(true);
						dispose();
					} else {
						Helper.showMsg("error");
					}
				}
			}
		});
		btnRegister.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnRegister.setBounds(10, 233, 274, 23);
		w_pane.add(btnRegister);

		JButton btnBackto = new JButton("Geri Don");
		btnBackto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI login = new LoginGUI();
				login.setVisible(true);
				dispose();
			}
		});
		btnBackto.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnBackto.setBounds(10, 267, 274, 23);
		w_pane.add(btnBackto);
	}
}
