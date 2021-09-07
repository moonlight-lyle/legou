package com.changgou.user.pojo;
import javax.persistence.*;
import java.io.Serializable;
import java.lang.String;

@Table(name="tb_cities")
public class Cities implements Serializable{

	@Id
    @Column(name = "cityid")
	private String cityid;

    @Column(name = "city")
	private String city;

    @Column(name = "provinceid")
	private String provinceid;



	//get方法
	public String getCityid() {
		return cityid;
	}

	//set方法
	public void setCityid(String cityid) {
		this.cityid = cityid;
	}
	//get方法
	public String getCity() {
		return city;
	}

	//set方法
	public void setCity(String city) {
		this.city = city;
	}
	//get方法
	public String getProvinceid() {
		return provinceid;
	}

	//set方法
	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}


}
