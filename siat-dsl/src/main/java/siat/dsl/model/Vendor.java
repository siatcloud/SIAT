package siat.dsl.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "vendor")
@XmlAccessorType(XmlAccessType.FIELD)

/**
 * <h1>SIAT Project</h1>
 * <h2>Layer: Data Storage Layer</h2>
 * <h3>Package Name: siat.dsl.model</h3>
 * <h3>Class Name: Vendor</h3>
 * <p>
 * 
 * @Project This file is part of SIAT project.
 *          </p>
 *          <p>
 * @Description: This class is used as a Vendor model.
 *               </p>
 * 
 * @author Aftab Alam <aftab@khu.ac.kr>
 * 
 * @version 1.0
 * @since 2018-05-25
 **/
public class Vendor {
	private int vendorId = 0;
	private String vendorName = "";

	// Default constructor.
	public Vendor() {
	}

	public int getVendorId() {
		return vendorId;
	}

	public Vendor setVendorId(String vendorId) {
		this.vendorId = Integer.parseInt(vendorId);
		return this;
	}

	public String getVendorName() {
		return vendorName;
	}

	public Vendor setVendorName(String vendorName) {
		this.vendorName = vendorName;
		return this;
	}
}
