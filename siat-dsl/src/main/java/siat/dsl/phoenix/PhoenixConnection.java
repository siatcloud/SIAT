package siat.dsl.phoenix;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * <h1>SIAT Project</h1> 
 * <h2>Layer: Data Storage Layer</h2> 
 * <h3>Package Name: siat.dsl.phoenix</h3>
 * <h3>Class Name: PhoenixConnection</h3>
 * <p>
 * @Project This file is part of SIAT project.
 * </p>
 * <p>
 * @Description: This class is used to handel Hbase/Phoenix Connection.
 * </p>
 * 
 * @author Aftab Alam <aftab@khu.ac.kr>, 
 * http://163.180.116.39		
 * @version 1.0
 * @since 2018-05-20
 **/

public class PhoenixConnection {
	public String URl1 = "jdbc:phoenix:localhost:2181:/hbase-unsecure"; // In use
	//public String URl2 = "jdbc:phoenix:thin:url=http://localhost:8765;serialization=PROTOBUF";
	public String URl2 = "jdbc:phoenix:thin:url=http://163.180.118.133:8765;serialization=PROTOBUF";
	//public String driver = "org.apache.phoenix.jdbc.PhoenixDriver";
	public String driver = "org.apache.phoenix.queryserver.client.Driver";
	public String userName = "";
	public String password = "";
	public String dataBase= "siat";
	public Connection connection = null;
	public Statement statement = null;
	public ResultSet rS = null;
	public PreparedStatement ps = null;

	public PhoenixConnection() {

		try {

			// Connect to the database
			Class.forName(this.driver);
			connection = DriverManager.getConnection(this.URl2);
			//System.out.println("Connection established successfully");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}