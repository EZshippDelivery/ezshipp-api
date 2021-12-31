package com.ezshipp.api.model;

import lombok.Data;

@Data
public class BikerDetailsResponse {
	private String name;
	private long phone;
	private String numberPlate;
	private String licenseUrl;
	private String zone;
	private String shift;
}
