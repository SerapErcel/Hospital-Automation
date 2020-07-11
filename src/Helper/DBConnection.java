package Helper;

import java.sql.*;

public class DBConnection {
	Connection c = null;

	public DBConnection() {
	}

	public Connection connDb() {
		try {
			this.c = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/hospital?user=root&password=Traeh180290.");
			return c;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return c;
	}
}
