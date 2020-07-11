package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Helper.Helper;
import Helper.Item;
import Model.Appointment;
import Model.Clinic;
import Model.Hasta;
import Model.Whour;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JMenuItem;

import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JTable;

public class HastaGUI extends JFrame {

	private JPanel w_pane;
	private static Hasta hasta = new Hasta();
	private Clinic clinic = new Clinic();
	private JTable table_doctor;
	private DefaultTableModel doctorModel;
	private Object[] doctorData = null;
	private JTable table_whour;
	private Whour whour = new Whour();
	private DefaultTableModel whourModel;
	private Object[] whourData = null;
	private int selectDoctorID = 0;
	private String selectDoctorName = null;
	private JTable table_appoint;
	private DefaultTableModel appointModel;
	private Object[] appointData = null;
	private Appointment appoint = new Appointment();
	private Color c0 = new Color(209, 242, 235);
	private Color c1 = new Color(163, 228, 215);
	private JPopupMenu appointMenu;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HastaGUI frame = new HastaGUI(hasta);
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
	public HastaGUI(Hasta hasta) throws SQLException {

		doctorModel = new DefaultTableModel();
		Object[] colDoktor = new Object[2];
		colDoktor[0] = "ID";
		colDoktor[1] = "Ad Soyad";
		doctorModel.setColumnIdentifiers(colDoktor);
		doctorData = new Object[2];

		whourModel = new DefaultTableModel();
		Object[] colWhour = new Object[2];
		colWhour[0] = "ID";
		colWhour[1] = "Tarih";
		whourModel.setColumnIdentifiers(colWhour);
		whourData = new Object[2];

		appointModel = new DefaultTableModel();
		Object[] colAppoint = new Object[3];
		colAppoint[0] = "ID";
		colAppoint[1] = "Doktor";
		colAppoint[2] = "Tarih";
		appointModel.setColumnIdentifiers(colAppoint);
		appointData = new Object[3];
		for (int i = 0; i < appoint.getHastaList(hasta.getId()).size(); i++) {
			appointData[0] = appoint.getHastaList(hasta.getId()).get(i).getId();
			appointData[1] = appoint.getHastaList(hasta.getId()).get(i).getDoctorName();
			appointData[2] = appoint.getHastaList(hasta.getId()).get(i).getAppDate();
			appointModel.addRow(appointData);
		}

		setTitle("Hastane Yonetim Sistemi");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 713, 463);
		w_pane = new JPanel();
		w_pane.setBackground(c1);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);

		JLabel label = new JLabel("Hosgeldiniz Sayin " + hasta.getName());
		label.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label.setBounds(10, 11, 278, 21);
		w_pane.add(label);

		JButton button = new JButton("CIKIS");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI login = new LoginGUI();
				login.setVisible(true);
				dispose();
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 16));
		button.setBounds(595, 12, 89, 23);
		w_pane.add(button);

		JTabbedPane w_tab = new JTabbedPane(JTabbedPane.TOP);
		w_tab.setFont(new Font("Tahoma", Font.BOLD, 14));
		w_tab.setBounds(10, 61, 674, 349);
		w_pane.add(w_tab);

		JPanel w_appointment = new JPanel();
		w_appointment.setBackground(c0);
		w_tab.addTab("Randevu Sistemi", null, w_appointment, null);
		w_appointment.setLayout(null);

		JScrollPane w_scrollDoctor = new JScrollPane();
		w_scrollDoctor.setBounds(10, 23, 239, 284);
		w_appointment.add(w_scrollDoctor);

		table_doctor = new JTable(doctorModel);
		w_scrollDoctor.setViewportView(table_doctor);

		JLabel lblNewLabel = new JLabel("Doktor Listesi");
		lblNewLabel.setBounds(10, 0, 126, 25);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		w_appointment.add(lblNewLabel);

		JLabel lblPoliklinikAdi = new JLabel("Poliklinik Adi");
		lblPoliklinikAdi.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPoliklinikAdi.setBounds(270, 0, 126, 25);
		w_appointment.add(lblPoliklinikAdi);

		JComboBox select_Clinic = new JComboBox();
		select_Clinic.setBounds(270, 23, 126, 22);
		select_Clinic.addItem("Poliklinik sec");
		for (int i = 0; i < clinic.getList().size(); i++) {
			select_Clinic.addItem(new Item(clinic.getList().get(i).getId(), clinic.getList().get(i).getName()));

		}
		select_Clinic.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (select_Clinic.getSelectedIndex() != 0) {
					JComboBox c = (JComboBox) e.getSource();
					Item item = (Item) c.getSelectedItem();
					DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
					clearModel.setRowCount(0);
					try {
						for (int i = 0; i < clinic.getClinicDoctorList(item.getKey()).size(); i++) {
							doctorData[0] = clinic.getClinicDoctorList(item.getKey()).get(i).getId();
							doctorData[1] = clinic.getClinicDoctorList(item.getKey()).get(i).getName();
							doctorModel.addRow(doctorData);

						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				} else {
					DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
					clearModel.setRowCount(0);
				}
			}

		});
		w_appointment.add(select_Clinic);

		JLabel lbldoctorsec = new JLabel("Doktor Sec");
		lbldoctorsec.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbldoctorsec.setBounds(270, 70, 129, 14);
		w_appointment.add(lbldoctorsec);

		JButton btn_selDoc = new JButton("Sec");
		btn_selDoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table_doctor.getSelectedRow();
				if (row >= 0) {
					String value = table_doctor.getModel().getValueAt(row, 0).toString();
					int id = Integer.parseInt(value);
					DefaultTableModel clearModel = (DefaultTableModel) table_whour.getModel();
					clearModel.setRowCount(0);
					try {
						for (int i = 0; i < whour.getWhourList(id).size(); i++) {
							whourData[0] = whour.getWhourList(id).get(i).getId();
							whourData[1] = whour.getWhourList(id).get(i).getWdate();
							whourModel.addRow(whourData);

						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					table_whour.setModel(whourModel);
					selectDoctorID = id;
					selectDoctorName = table_doctor.getModel().getValueAt(row, 1).toString();
				} else {
					Helper.showMsg("Lutfen bir doktor seciniz!");
				}
			}
		});
		btn_selDoc.setFont(new Font("Tahoma", Font.BOLD, 14));
		btn_selDoc.setBounds(270, 95, 129, 23);
		w_appointment.add(btn_selDoc);

		JScrollPane w_scrollWhour = new JScrollPane();
		w_scrollWhour.setBounds(420, 23, 239, 284);
		w_appointment.add(w_scrollWhour);

		table_whour = new JTable(whourModel);
		w_scrollWhour.setViewportView(table_whour);
		table_whour.getColumnModel().getColumn(0).setPreferredWidth(5);

		JLabel lblUygunSaatler = new JLabel("Uygun Saatler");
		lblUygunSaatler.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblUygunSaatler.setBounds(420, 0, 126, 25);
		w_appointment.add(lblUygunSaatler);

		JLabel lblRandevu = new JLabel("Randevu");
		lblRandevu.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblRandevu.setBounds(267, 143, 129, 14);
		w_appointment.add(lblRandevu);

		JButton btn_addAppoint = new JButton("Randevu Al");
		btn_addAppoint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = table_whour.getSelectedRow();
				if (selRow >= 0) {
					String date = table_whour.getModel().getValueAt(selRow, 1).toString();
					try {
						boolean control = hasta.addAppointment(selectDoctorID, hasta.getId(), selectDoctorName,
								hasta.getName(), date);
						if (control) {
							Helper.showMsg("success");
							hasta.updateWhourStatus(selectDoctorID, date);
							updateWhourModel(selectDoctorID);
							updateAppointModel(hasta.getId());

						} else {
							Helper.showMsg("error");
						}

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					Helper.showMsg("Lutfen gecerli bir tarih giriniz!");
				}
			}
		});
		btn_addAppoint.setFont(new Font("Tahoma", Font.BOLD, 14));
		btn_addAppoint.setBounds(267, 168, 129, 23);
		w_appointment.add(btn_addAppoint);

		JPanel w_appoint = new JPanel();
		w_tab.addTab("Randevularim", null, w_appoint, null);
		w_appoint.setLayout(null);

		JScrollPane w_scrollAppoint = new JScrollPane();
		w_scrollAppoint.setBounds(10, 11, 649, 296);
		w_appoint.add(w_scrollAppoint);

		appointMenu = new JPopupMenu();
		JMenuItem deleteMenu = new JMenuItem("Sil");
		appointMenu.add(deleteMenu);

		deleteMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String selDate = (String) table_appoint.getValueAt(table_appoint.getSelectedRow(), 2);
					String selDoctorName = (String) table_appoint.getValueAt(table_appoint.getSelectedRow(), 1);
					appoint.deleteAppoint(selDate, selDoctorName);
					updateAppointModel(hasta.getId());

				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});

		table_appoint = new JTable(appointModel);
		table_appoint.setComponentPopupMenu(appointMenu);
		table_appoint.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Point point = e.getPoint();
				try {
					int selectedRow = table_appoint.rowAtPoint(point);
					table_appoint.setRowSelectionInterval(selectedRow, selectedRow);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});
		w_scrollAppoint.setViewportView(table_appoint);
	}

	public void updateWhourModel(int doctor_id) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_whour.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < whour.getWhourList(doctor_id).size(); i++) {
			whourData[0] = whour.getWhourList(doctor_id).get(i).getId();
			whourData[1] = whour.getWhourList(doctor_id).get(i).getWdate();
			whourModel.addRow(whourData);
		}
	}

	public void updateAppointModel(int hasta_id) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_appoint.getModel();
		clearModel.setRowCount(0);
		try {
			for (int i = 0; i < appoint.getHastaList(hasta_id).size(); i++) {
				appointData[0] = appoint.getHastaList(hasta_id).get(i).getId();
				appointData[1] = appoint.getHastaList(hasta_id).get(i).getDoctorName();
				appointData[2] = appoint.getHastaList(hasta_id).get(i).getAppDate();
				appointModel.addRow(appointData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
