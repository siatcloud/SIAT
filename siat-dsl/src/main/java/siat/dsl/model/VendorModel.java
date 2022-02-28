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
 * <h3>Class Name: VendorModel</h3>
 * <p>
 * 
 * @Project This file is part of SIAT project.
 * </p><p>
 * @Description: This class is used as a VendorModel model.
 *</p>
 * 
 * @author Aftab Alam <aftab@khu.ac.kr>
 * 
 * @version 1.0
 * @since 2018-05-27
 **/

public class VendorModel {
	private int vendorModelId = 0;
	private int vendorId = 0;
	private String cameraModel = "";
	private String description = "";

	public int getVendorModelId() {
		return vendorModelId;
	}

	public VendorModel setVendorModelId(String vendorModelId) {
		this.vendorModelId = Integer.parseInt(vendorModelId);
		return this;
	}

	public int getVendorId() {
		return vendorId;
	}

	public VendorModel setVendorId(String vendorId) {
		this.vendorId = Integer.parseInt(vendorId);
		return this;
	}

	public String getCameraModel() {
		return cameraModel;
	}

	public VendorModel setCameraModel(String cameraModel) {
		this.cameraModel = cameraModel;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public VendorModel setDescription(String description) {
		this.description = description;
		return this;
	}
}
