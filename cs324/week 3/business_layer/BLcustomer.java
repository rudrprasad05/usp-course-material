/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author chaudhary_k
 */
package business_layer;
import data_access_layer.DAcustomer;
import data_access_layer.*;

public class BLcustomer{
	private String cusId;
	private String fName;
	private String lName;

	private DAcustomer cusData;

	public BLcustomer(){
		cusData = new DAcustomer();
	}

	public String getCusId() {
		return cusId;
	}
	public void setCusId(String cusId) {
		this.cusId = cusId;
	}
	public String getFName() {
		return fName;
	}
	public void setFName(String name) {
		fName = name;
	}
	public String getLName() {
		return lName;
	}
	public void setLName(String name) {
		lName = name;
	}

	public void add() throws Exception{
		cusData.add(this);
	}
	public void delete() throws Exception{
		cusData.delete(this);
	}
	public void update() throws Exception{
		cusData.update(this);
	}



}