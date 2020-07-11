package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import Helper.*;
import Model.*;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JMenuItem;

import java.awt.Font;
import java.awt.Point;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class BashekimGUI extends JFrame {

	static Bashekim bashekim = new Bashekim();
	Clinic clinic = new Clinic();
	private JPanel w_pane;
	private JTextField fld_dName;
	private JTextField fld_dTcno;
	private JPasswordField fld_dPass;
	private JTextField fld_doktorID;
	private JTable table_doktor;
	private DefaultTableModel doktorModel = null;
	private Object[] doktorData = null;
	private JTable table_clinic;
	private JTextField fld_clinicName;
	private DefaultTableModel clinicModel = null;
	private Object[] clinicData = null;
	private JPopupMenu clinicMenu;
	private JTable table_worker;
	private Color c0=new Color(209, 242, 235);
	private Color c1=new Color(163, 228, 215);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BashekimGUI frame = new BashekimGUI(bashekim);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 */
	public BashekimGUI(Bashekim bashekim) throws SQLException {
		// doktor model
		doktorModel = new DefaultTableModel();
		Object[] colDoktorName = new Object[4];

		colDoktorName[0] = "ID";
		colDoktorName[1] = "Ad Soyad";
		colDoktorName[2] = "TC No";
		colDoktorName[3] = "Sifre";
		doktorModel.setColumnIdentifiers(colDoktorName);
		doktorData = new Object[4];
		for (int i = 0; i < bashekim.getDoktorList().size(); i++) {
			doktorData[0] = bashekim.getDoktorList().get(i).getId();
			doktorData[1] = bashekim.getDoktorList().get(i).getName();
			doktorData[2] = bashekim.getDoktorList().get(i).getTcno();
			doktorData[3] = bashekim.getDoktorList().get(i).getPasword();
			doktorModel.addRow(doktorData);
		}
		// WorkerModel
		DefaultTableModel workerModel = new DefaultTableModel();
		Object[] colWorker = new Object[2];
		colWorker[0] = "ID";
		colWorker[1] = "Ad Soyad";
		workerModel.setColumnIdentifiers(colWorker);
		Object[] workerData = new Object[2];

		// Clinic model
		clinicModel = new DefaultTableModel();
		Object[] colClinic = new Object[2];
		colClinic[0] = "ID";
		colClinic[1] = "Poliklinik Adi";
		clinicModel.setColumnIdentifiers(colClinic);
		clinicData = new Object[2];
		for (int i = 0; i < clinic.getList().size(); i++) {
			clinicData[0] = clinic.getList().get(i).getId();
			clinicData[1] = clinic.getList().get(i).getName();
			clinicModel.addRow(clinicData);
		}

		setTitle("Hastane Yonetim Sistemi");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 450);

		w_pane = new JPanel();
		w_pane.setBackground(c1);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Hosgeldiniz Sayin " + bashekim.getName());
		lblNewLabel.setBounds(10, 11, 278, 21);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		w_pane.add(lblNewLabel);

		JButton btnNewButton = new JButton("CIKIS");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI login=new LoginGUI();
				login.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setBounds(595, 12, 89, 23);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		w_pane.add(btnNewButton);

		JTabbedPane w_tab = new JTabbedPane(JTabbedPane.TOP);
		w_tab.setFont(new Font("Tahoma", Font.BOLD, 14));
		w_tab.setBounds(10, 61, 674, 349);
		w_pane.add(w_tab);

		JPanel w_doktor = new JPanel();
		w_doktor.setBackground(c0);
		w_tab.addTab("Doktor Yonetimi", null, w_doktor, null);
		w_doktor.setLayout(null);

		JLabel lblAdSoyad = new JLabel("Ad Soyad");
		lblAdSoyad.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblAdSoyad.setBounds(541, 11, 118, 14);
		w_doktor.add(lblAdSoyad);

		JLabel lblTcNo = new JLabel("T.C. No");
		lblTcNo.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTcNo.setBounds(541, 67, 118, 14);
		w_doktor.add(lblTcNo);

		JLabel lblSifre = new JLabel("Sifre");
		lblSifre.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSifre.setBounds(541, 120, 118, 14);
		w_doktor.add(lblSifre);

		JLabel lblKullaniciId = new JLabel("Kullanici ID");
		lblKullaniciId.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblKullaniciId.setBounds(541, 212, 118, 14);
		w_doktor.add(lblKullaniciId);

		fld_dName = new JTextField();
		fld_dName.setText("");
		fld_dName.setBounds(541, 36, 118, 20);
		w_doktor.add(fld_dName);
		fld_dName.setColumns(10);

		fld_dTcno = new JTextField();
		fld_dTcno.setText("");
		fld_dTcno.setColumns(10);
		fld_dTcno.setBounds(541, 89, 118, 20);
		w_doktor.add(fld_dTcno);

		fld_dPass = new JPasswordField();
		fld_dPass.setBounds(541, 145, 118, 20);
		w_doktor.add(fld_dPass);

		JButton btnNewButton_1 = new JButton("Ekle");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_dName.getText().length() == 0 || fld_dPass.getText().length() == 0
						|| fld_dTcno.getText().length() == 0) {
					Helper.showMsg("fill");

				} else {
					try {
						boolean control = bashekim.addDoktor(fld_dTcno.getText(), fld_dPass.getText(),
								fld_dName.getText());
						if (control) {
							Helper.showMsg("success");
							fld_dName.setText(null);
							fld_dTcno.setText(null);
							fld_dPass.setText(null);
							updateDoctorModel();
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton_1.setBounds(541, 176, 118, 23);
		w_doktor.add(btnNewButton_1);

		fld_doktorID = new JTextField();
		fld_doktorID.setText("");
		fld_doktorID.setColumns(10);
		fld_doktorID.setBounds(541, 237, 118, 20);
		w_doktor.add(fld_doktorID);

		JButton btnSil = new JButton("Sil");
		btnSil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_doktorID.getText().length() == 0) {
					Helper.showMsg("Lutfen gecerli bir doktor seciniz");

				} else {
					if (Helper.confirm("sure")) {

						int selectID = Integer.parseInt(fld_doktorID.getText());
						try {
							boolean control = bashekim.deleteDoktor(selectID);
							if (control) {
								Helper.showMsg("succes");
								fld_doktorID.setText(null);
								updateDoctorModel();
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

				}
			}
		});
		btnSil.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnSil.setBounds(541, 268, 118, 23);
		w_doktor.add(btnSil);

		JScrollPane w_scrolldoktor = new JScrollPane();
		w_scrolldoktor.setBounds(10, 11, 519, 296);
		w_doktor.add(w_scrolldoktor);

		table_doktor = new JTable(doktorModel);
		w_scrolldoktor.setViewportView(table_doktor);

		table_doktor.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				try {
					fld_doktorID.setText(table_doktor.getValueAt(table_doktor.getSelectedRow(), 0).toString());
				} catch (Exception ex) {

				}
			}
		});
		table_doktor.getModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE) {
					int selectID = Integer
							.parseInt(table_doktor.getValueAt(table_doktor.getSelectedRow(), 0).toString());
					String selectName = table_doktor.getValueAt(table_doktor.getSelectedRow(), 1).toString();
					String selectTcno = table_doktor.getValueAt(table_doktor.getSelectedRow(), 2).toString();
					String selectPass = table_doktor.getValueAt(table_doktor.getSelectedRow(), 3).toString();

					try {
						boolean control = bashekim.updateDoktor(selectID, selectTcno, selectName, selectPass);
						if (control) {
							// Helper.showMsg("success");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

		});

		JPanel w_clinic = new JPanel();
		w_clinic.setBackground(c0);
		w_tab.addTab("Poliklinikler", null, w_clinic, null);
		w_clinic.setLayout(null);

		JScrollPane w_scrollClinic = new JScrollPane();
		w_scrollClinic.setBounds(10, 11, 253, 296);
		w_clinic.add(w_scrollClinic);

		clinicMenu = new JPopupMenu();
		JMenuItem updateMenu = new JMenuItem("Guncelle");
		JMenuItem deleteMenu = new JMenuItem("Sil");
		clinicMenu.add(updateMenu);
		clinicMenu.add(deleteMenu);

		updateMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selID = Integer.parseInt(table_clinic.getValueAt(table_clinic.getSelectedRow(), 0).toString());
				Clinic selectClinic = clinic.getFech(selID);
				UpdateClinicGUI updateGUI = new UpdateClinicGUI(selectClinic);
				updateGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				updateGUI.setVisible(true);
				updateGUI.addWindowListener(new WindowAdapter() {
					public void windowClosed(WindowEvent e) {
						try {
							updateClinicModel();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
			}

		});

		deleteMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Helper.confirm("sure")) {
					int selID = Integer.parseInt(table_clinic.getValueAt(table_clinic.getSelectedRow(), 0).toString());
					try {
						if (clinic.deleteClinic(selID)) {
							Helper.showMsg("success");
							updateClinicModel();

						} else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});

		table_clinic = new JTable(clinicModel);
		table_clinic.setComponentPopupMenu(clinicMenu);
		table_clinic.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Point point = e.getPoint();
				int selectedRow = table_clinic.rowAtPoint(point);
				table_clinic.setRowSelectionInterval(selectedRow, selectedRow);
			}
		});
		w_scrollClinic.setViewportView(table_clinic);

		JLabel lblPoliklinikAdi = new JLabel("Poliklinik Adi");
		lblPoliklinikAdi.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPoliklinikAdi.setBounds(273, 10, 129, 14);
		w_clinic.add(lblPoliklinikAdi);

		fld_clinicName = new JTextField();
		fld_clinicName.setText("");
		fld_clinicName.setColumns(10);
		fld_clinicName.setBounds(273, 35, 129, 20);
		w_clinic.add(fld_clinicName);

		JButton btn_addClinic = new JButton("Ekle");
		btn_addClinic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_clinicName.getText().length() == 0) {
					Helper.showMsg("fill");
				} else {
					try {
						if (clinic.addClinic(fld_clinicName.getText())) {
							Helper.showMsg("success");
							fld_clinicName.setText(null);
							updateClinicModel();
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btn_addClinic.setFont(new Font("Tahoma", Font.BOLD, 14));
		btn_addClinic.setBounds(273, 67, 129, 23);
		w_clinic.add(btn_addClinic);

		JScrollPane w_scrollWorker = new JScrollPane();
		w_scrollWorker.setBounds(412, 11, 247, 296);
		w_clinic.add(w_scrollWorker);

		table_worker = new JTable();
		w_scrollWorker.setViewportView(table_worker);

		JComboBox select_doctor = new JComboBox();
		select_doctor.setBounds(273, 251, 129, 22);
		for (int i = 0; i < bashekim.getDoktorList().size(); i++) {
			select_doctor.addItem(
					new Item(bashekim.getDoktorList().get(i).getId(), bashekim.getDoktorList().get(i).getName()));

		}
		select_doctor.addActionListener(e -> {
			JComboBox c = (JComboBox) e.getSource();
			Item item = (Item) c.getSelectedItem();
			System.out.println(item.getKey() + " : " + item.getValue());
		});
		w_clinic.add(select_doctor);

		JButton btn_addWorker = new JButton("Ekle");
		btn_addWorker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = table_clinic.getSelectedRow();
				if (selRow >= 0) {
					String selClinic = table_clinic.getModel().getValueAt(selRow, 0).toString();
					int selClinicID = Integer.parseInt(selClinic);
					Item doctorItem = (Item) select_doctor.getSelectedItem();
					try {
						boolean control;
						control = bashekim.addWorker(doctorItem.getKey(), selClinicID);
						if (control) {
							Helper.showMsg("success");
							DefaultTableModel clearModel = (DefaultTableModel) table_worker.getModel();
							clearModel.setRowCount(0);
							for (int i = 0; i < bashekim.getClinicDoctorList(selClinicID).size(); i++) {
								workerData[0] = bashekim.getClinicDoctorList(selClinicID).get(i).getId();
								workerData[1] = bashekim.getClinicDoctorList(selClinicID).get(i).getName();
								workerModel.addRow(workerData);
							}
						} else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					table_worker.setModel(workerModel);

				} else {
					Helper.showMsg("Lutfen bir poliklinik seciniz.");
				}
			}
		});
		btn_addWorker.setFont(new Font("Tahoma", Font.BOLD, 14));
		btn_addWorker.setBounds(273, 284, 129, 23);
		w_clinic.add(btn_addWorker);

		JLabel label = new JLabel("Poliklinik Adi");
		label.setFont(new Font("Tahoma", Font.BOLD, 14));
		label.setBounds(273, 133, 129, 14);
		w_clinic.add(label);

		JButton btn_workerselect = new JButton("Sec");
		btn_workerselect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = table_clinic.getSelectedRow();
				if (selRow >= 0) {
					String selClinic = table_clinic.getModel().getValueAt(selRow, 0).toString();
					int selClinicID = Integer.parseInt(selClinic);
					DefaultTableModel clearModel = (DefaultTableModel) table_worker.getModel();
					clearModel.setRowCount(0);
					try {
						for (int i = 0; i < bashekim.getClinicDoctorList(selClinicID).size(); i++) {
							workerData[0] = bashekim.getClinicDoctorList(selClinicID).get(i).getId();
							workerData[1] = bashekim.getClinicDoctorList(selClinicID).get(i).getName();
							workerModel.addRow(workerData);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					table_worker.setModel(workerModel);

				} else {
					Helper.showMsg("lutfen bir poliklinik secin");

				}
			}
		});
		btn_workerselect.setFont(new Font("Tahoma", Font.BOLD, 14));
		btn_workerselect.setBounds(273, 158, 129, 23);
		w_clinic.add(btn_workerselect);

	}

	public void updateDoctorModel() throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_doktor.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < bashekim.getDoktorList().size(); i++) {
			doktorData[0] = bashekim.getDoktorList().get(i).getId();
			doktorData[1] = bashekim.getDoktorList().get(i).getName();
			doktorData[2] = bashekim.getDoktorList().get(i).getTcno();
			doktorData[3] = bashekim.getDoktorList().get(i).getPasword();
			doktorModel.addRow(doktorData);
		}
	}

	public void updateClinicModel() throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_clinic.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < clinic.getList().size(); i++) {
			clinicData[0] = clinic.getList().get(i).getId();
			clinicData[1] = clinic.getList().get(i).getName();
			clinicModel.addRow(clinicData);
		}
	}
}
