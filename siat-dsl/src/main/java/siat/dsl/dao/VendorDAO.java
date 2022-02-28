package siat.dsl.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import siat.dsl.model.User;
import siat.dsl.model.Vendor;
import siat.dsl.phoenix.Crud;

/**
 * <h1>SIAT Project</h1> 
 * <h2>Layer: Data Storage Layer</h2> 
 * <h3>Package Name: siat.dsl.dao</h3>
 * <h3>Class Name: VendorDAO</h3>
 * <p>
 * @Project This file is part of SIAT project.
 * </p>
 * <p>
 * @Description: This class is used to handel Vendor CRUD Operations.
 * </p>
 * 
 * @author Aftab Alam <aftab@khu.ac.kr>
 * 
 * @version 1.0
 * @since 2018-05-25
 **/
public class VendorDAO {
	private static Crud operation = new Crud();

	/**
	 * Create new vendor.
	 * 
	 * @param vendor object
	 * @return vendor object
	 * @throws SQLException
	 */
	public static Vendor create(Vendor vendor) throws SQLException {

		String[] data = new String[2];

		data[0] = "NEXT VALUE FOR siat.vendor_id";
		data[1] = "\'" + vendor.getVendorName() + "\'";

		// System.out.println("Data = " + Arrays.toString(data));
		operation.insertOrUdate("VENDOR", data);
		
		return vendor;
	}

	////////////////////////////////////////////////////
	
	/**
	 * This function fetch a list of all vendors from DB.
	 * 
	 * @param null
	 * @return list of vendor objects
	 * @throws SQLException
	 */
	public static List<Vendor> getAll() throws SQLException {

		ResultSet rs = operation.getAllRows("VENDOR");
		List<Vendor> list = new ArrayList<Vendor>();

		while (rs.next()) {
			Vendor vendor = new Vendor()
					.setVendorId(rs.getString("VENDOR_ID"))
					.setVendorName(rs.getString("VENDOR_NAME"));
			
			list.add(vendor);
		}

		// System.out.println(list.size());
		return list;
	}

	////////////////////////////////////////////////////

	/**
	 * Update Vendor.
	 * 
	 * @param Vendor Object
	 * @return Vendor Object
	 */
	public static Vendor update(Vendor vendor) {

		String[] data = new String[2];

		data[0] = "" + vendor.getVendorId();
		data[1] = "\'" + vendor.getVendorName() + "\'";
		
		operation.insertOrUdate("VENDOR", data);

		return vendor;
	}
}

