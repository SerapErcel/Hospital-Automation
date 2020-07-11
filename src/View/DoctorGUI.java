package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Model.Appointment;
import Model.Doctor;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JMenuItem;

import java.awt.Font;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import com.toedter.calendar.JDateChooser;

import Helper.Helper;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class DoctorGUI extends JFrame {

	private static Doctor doctor = new Doctor();
	private JPanel w_pane;
	private JTable table_whour;
	private DefaultTableModel whourModel;
	private Object[] whourData = null;
	private Color c0 = new Color(209, 242, 235);
	private Color c1 = new Color(163, 228, 215);
	private JTable table_doctorAppoint;
	private DefaultTableModel d_appointModel;
	private Object[] d_appointData = null;
	private Appointment appoint = new Appointment();
	private JPopupMenu d_appointMenu;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DoctorGUI frame = new DoctorGUI(doctor);
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
	 * @param
	 */
	public DoctorGUI(Doctor doctor) {

		d_appointModel = new DefaultTableModel();
		Object[] colAppoint = new Object[3];
		colAppoint[0] = "ID";
		colAppoint[1] = "Hasta";
		colAppoint[2] = "Tarih";
		d_appointModel.setColumnIdentifiers(colAppoint);
		d_appointData = new Object[3];
		try {
			for (int i = 0; i < appoint.getRandevuList(doctor.getId()).size(); i++) {
				d_appointData[0] = appoint.getRandevuList(doctor.getId()).get(i).getId();
				d_appointData[1] = appoint.getRandevuList(doctor.getId()).get(i).getHastaName();
				d_appointData[2] = appoint.getRandevuList(doctor.getId()).get(i).getAppDate();
				d_appointModel.addRow(d_appointData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		whourModel = new DefaultTableModel();
		Object[] colWhour = new Object[2];
		colWhour[0] = "ID";
		colWhour[1] = "Tarih";
		whourModel.setColumnIdentifiers(colWhour);
		whourData = new Object[2];
		try {
			for (int i = 0; i < doctor.getWhourList(doctor.getId()).size(); i++) {
				whourData[0] = doctor.getWhourList(doctor.getId()).get(i).getId();
				whourData[1] = doctor.getWhourList(doctor.getId()).get(i).getWdate();
				whourModel.addRow(whourData);

			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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

		JLabel label = new JLabel("Hosgeldiniz Sayin " + doctor.getName());
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

		JPanel w_whour = new JPanel();
		w_whour.setBackground(c0);
		w_tab.addTab("Calisma Saatleri", null, w_whour, null);
		w_whour.setLayout(null);

		JDateChooser select_date = new JDateChooser();
		select_date.setBounds(10, 11, 147, 27);
		w_whour.add(select_date);

		JComboBox select_time = new JComboBox();
		select_time.setModel(new DefaultComboBoxModel(new String[] { "10:00", "10:30", "11:00", "11:30", "12:00",
				"12:30", "13:30", "14:00", "14:30", "15:00", "15:30" }));
		select_time.setBounds(167, 11, 62, 27);
		w_whour.add(select_time);

		JButton btn_addWhour = new JButton("Ekle");
		btn_addWhour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String date = "";
				try {
					date = sdf.format(select_date.getDate());
				} catch (Exception e2) {
					e2.printStackTrace();
				}

				if (date.length() == 0) {
					Helper.showMsg("Lutfen gecerli bir tarih girin");

				} else {
					String time = " " + select_time.getSelectedItem().toString() + ":00";
					String selectDate = date + time;
					try {
						boolean control = doctor.addWhour(doctor.getId(), doctor.getName(), selectDate);
						if (control) {
							Helper.showMsg("success");
							updateWhourModel(doctor);

						} else {
							Helper.showMsg("error");
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btn_addWhour.setBounds(239, 11, 62, 27);
		w_whour.add(btn_addWhour);

		JScrollPane w_scrollWhour = new JScrollPane();
		w_scrollWhour.setBounds(0, 49, 669, 269);
		w_whour.add(w_scrollWhour);

		table_whour = new JTable(whourModel);
		w_scrollWhour.setViewportView(table_whour);

		JButton btn_deletewhour = new JButton("Sil");
		btn_deletewhour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = table_whour.getSelectedRow();
				if (selRow >= 0) {
					String selectRow = table_whour.getModel().getValueAt(selRow, 0).toString();
					int selID = Integer.parseInt(selectRow);
					boolean control;
					try {
						control = doctor.deleteWhour(selID);
						if (control) {
							Helper.showMsg("success");
							updateWhourModel(doctor);

						} else {
							Helper.showMsg("error");
						}

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				} else {
					Helper.showMsg("Lutfen bir tarih seciniz.");
				}
			}
		});
		btn_deletewhour.setBounds(597, 11, 62, 27);
		w_whour.add(btn_deletewhour);

		JPanel w_appointments = new JPanel();
		w_appointments.setBackground(c0);
		w_tab.addTab("Randevular", null, w_appointments, null);
		w_appointments.setLayout(null);

		JScrollPane w_scrollAppoint = new JScrollPane();
		w_scrollAppoint.setBounds(10, 11, 649, 296);
		w_appointments.add(w_scrollAppoint);

		d_appointMenu = new JPopupMenu();
		JMenuItem deleteMenu = new JMenuItem("Ýptal Et");
		d_appointMenu.add(deleteMenu);
		deleteMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String selDate = (String) table_doctorAppoint.getValueAt(table_doctorAppoint.getSelectedRow(), 2);
					String selHastaName = (String) table_doctorAppoint.getValueAt(table_doctorAppoint.getSelectedRow(),
							1);
					appoint.deleteAppoint(selDate, selHastaName);
					updateDAppointModel(doctor.getId());

				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});

		table_doctorAppoint = new JTable(d_appointModel);
		table_doctorAppoint.setComponentPopupMenu(d_appointMenu);
		table_doctorAppoint.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Point point = e.getPoint();
				try {
					int selectedRow = table_doctorAppoint.rowAtPoint(point);
					table_doctorAppoint.setRowSelectionInterval(selectedRow, selectedRow);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});
		w_scrollAppoint.setViewportView(table_doctorAppoint);
	}

	public void updateWhourModel(Doctor doctor) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_whour.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < doctor.getWhourList(doctor.getId()).size(); i++) {
			whourData[0] = doctor.getWhourList(doctor.getId()).get(i).getId();
			whourData[1] = doctor.getWhourList(doctor.getId()).get(i).getWdate();
			whourModel.addRow(whourData);

		}
	}

	public void updateDAppointModel(int doctor_id) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_doctorAppoint.getModel();
		clearModel.setRowCount(0);
		try {
			for (int i = 0; i < appoint.getRandevuList(doctor_id).size(); i++) {
				d_appointData[0] = appoint.getRandevuList(doctor_id).get(i).getId();
				d_appointData[1] = appoint.getRandevuList(doctor_id).get(i).getHastaName();
				d_appointData[2] = appoint.getRandevuList(doctor_id).get(i).getAppDate();
				d_appointModel.addRow(d_appointData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
