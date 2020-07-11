package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import Helper.*;
import Model.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class LoginGUI extends JFrame {

	private JPanel w_pane;
	private JTextField fld_hastaTc;
	private JTextField fld_doktorTc;
	private JPasswordField fld_doktorPass;
	private JPasswordField fld_hastaPass;
	private DBConnection conn = new DBConnection();
	private Color c0 = new Color(209, 242, 235);
	private Color c1 = new Color(163, 228, 215);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
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
	public LoginGUI() {
		setResizable(false);
		setTitle("Hastane Yonetim Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 509, 420);
		w_pane = new JPanel();
		w_pane.setBackground(c1);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);

		JLabel lbl_logo = new JLabel(new ImageIcon(LoginGUI.class.getResource("/View/hospital.png")));
		lbl_logo.setBounds(188, 11, 122, 123);
		w_pane.add(lbl_logo);

		JLabel lblHastaneYnetimSistemine = new JLabel("Hastane Yonetim Sistemine Hosgeldiniz");
		lblHastaneYnetimSistemine.setFont(new Font("Verdana", Font.BOLD, 17));
		lblHastaneYnetimSistemine.setBounds(49, 132, 380, 22);
		w_pane.add(lblHastaneYnetimSistemine);

		JTabbedPane w_tabpane = new JTabbedPane(JTabbedPane.TOP);
		w_tabpane.setBounds(10, 165, 474, 195);
		w_pane.add(w_tabpane);

		JPanel w_hastaLogin = new JPanel();
		w_hastaLogin.setBackground(c0);
		w_tabpane.addTab("Hasta Girisi", null, w_hastaLogin, null);
		w_hastaLogin.setLayout(null);

		JLabel lblTcGirisi = new JLabel("TC Numaraniz:");
		lblTcGirisi.setFont(new Font("Verdana", Font.BOLD, 17));
		lblTcGirisi.setBounds(61, 21, 168, 22);
		w_hastaLogin.add(lblTcGirisi);

		JLabel lblSifre = new JLabel("Sifre:");
		lblSifre.setFont(new Font("Verdana", Font.BOLD, 17));
		lblSifre.setBounds(61, 54, 168, 20);
		w_hastaLogin.add(lblSifre);

		fld_hastaTc = new JTextField();
		fld_hastaTc.setFont(new Font("Tahoma", Font.PLAIN, 17));
		fld_hastaTc.setBounds(239, 21, 195, 20);
		w_hastaLogin.add(fld_hastaTc);
		fld_hastaTc.setColumns(10);

		JButton btn_register = new JButton("Kayit Ol");
		btn_register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterGUI rGUI = new RegisterGUI();
				rGUI.setVisible(true);
				dispose();
			}
		});
		btn_register.setFont(new Font("Tahoma", Font.BOLD, 14));
		btn_register.setBounds(10, 102, 219, 54);
		w_hastaLogin.add(btn_register);

		JButton btn_hastaLogin = new JButton("Giris Yap");
		btn_hastaLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_hastaTc.getText().length() == 0 || fld_hastaPass.getText().length() == 0) {
					Helper.showMsg("fill");
				} else {
					boolean key = true;

					try {
						Connection con = conn.connDb();
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT *FROM user");
						while (rs.next()) {
							if (fld_hastaTc.getText().equals(rs.getString("tcno"))
									&& fld_hastaPass.getText().equalsIgnoreCase(rs.getString("pasword"))) {
								if (rs.getString("type").equals("hasta")) {
									Hasta hasta = new Hasta();
									hasta.setId(rs.getInt("id"));
									hasta.setPasword("pasword");
									hasta.setTcno(rs.getString("tcno"));
									hasta.setName(rs.getString("name"));
									hasta.setType(rs.getString("type"));
									HastaGUI hGUI = new HastaGUI(hasta);
									hGUI.setVisible(true);
									dispose();
									key = false;
								}
							}
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					if (key)
						Helper.showMsg("Boyle bir hasta bulunamadi lutfen kayit olunuz.");

				}
			}
		});
		btn_hastaLogin.setFont(new Font("Tahoma", Font.BOLD, 14));
		btn_hastaLogin.setBounds(239, 102, 220, 54);
		w_hastaLogin.add(btn_hastaLogin);

		fld_hastaPass = new JPasswordField();
		fld_hastaPass.setBounds(239, 57, 195, 20);
		w_hastaLogin.add(fld_hastaPass);

		JPanel w_doctorLogin = new JPanel();
		w_doctorLogin.setBackground(c0);
		w_tabpane.addTab("Doktor Girisi", null, w_doctorLogin, null);
		w_doctorLogin.setLayout(null);

		JLabel label = new JLabel("Sifre:");
		label.setFont(new Font("Verdana", Font.BOLD, 17));
		label.setBounds(61, 54, 168, 20);
		w_doctorLogin.add(label);

		JLabel label_1 = new JLabel("TC Numaraniz:");
		label_1.setFont(new Font("Verdana", Font.BOLD, 17));
		label_1.setBounds(61, 21, 168, 22);
		w_doctorLogin.add(label_1);

		fld_doktorTc = new JTextField();
		fld_doktorTc.setFont(new Font("Tahoma", Font.PLAIN, 17));
		fld_doktorTc.setColumns(10);
		fld_doktorTc.setBounds(239, 21, 195, 20);
		w_doctorLogin.add(fld_doktorTc);

		JButton btn_doktorLogin = new JButton("Giris Yap");
		btn_doktorLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_doktorTc.getText().length() == 0 || fld_doktorPass.getText().length() == 0) {
					Helper.showMsg("fill");
				} else {
					try {
						Connection con = conn.connDb();
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT *FROM user");
						while (rs.next()) {
							if (fld_doktorTc.getText().equals(rs.getString("tcno"))
									&& fld_doktorPass.getText().equalsIgnoreCase(rs.getString("pasword"))) {
								if (rs.getString("type").equals("bashekim")) {
									Bashekim bhekim = new Bashekim();
									bhekim.setId(rs.getInt("id"));
									bhekim.setPasword("pasword");
									bhekim.setTcno(rs.getString("tcno"));
									bhekim.setName(rs.getString("name"));
									bhekim.setType(rs.getString("type"));
									BashekimGUI bGUI = new BashekimGUI(bhekim);
									bGUI.setVisible(true);
									dispose();
								}
								if (rs.getString("type").equals("doktor")) {
									Doctor doctor = new Doctor();
									doctor.setId(rs.getInt("id"));
									doctor.setPasword("pasword");
									doctor.setTcno(rs.getString("tcno"));
									doctor.setName(rs.getString("name"));
									doctor.setType(rs.getString("type"));
									DoctorGUI dGUI = new DoctorGUI(doctor);
									dGUI.setVisible(true);
									dispose();
								}
							}
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}
			}
		});
		btn_doktorLogin.setFont(new Font("Tahoma", Font.BOLD, 14));
		btn_doktorLogin.setBounds(10, 102, 449, 54);
		w_doctorLogin.add(btn_doktorLogin);

		fld_doktorPass = new JPasswordField();
		fld_doktorPass.setBounds(239, 57, 195, 20);
		w_doctorLogin.add(fld_doktorPass);
		
		JLabel lblNewLabel = new JLabel("\u00A9 2020  Created by Serap Er\u00E7el");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblNewLabel.setBounds(179, 366, 141, 14);
		w_pane.add(lblNewLabel);
	}
}
