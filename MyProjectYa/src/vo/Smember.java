package vo; //vo=valueObject

import java.sql.Timestamp;

/*
Member 테이블 생성 SQL문 
CREATE TABLE BOARD(
id VARCHAR(15) PRIMARY KEY,
password VARCHAR(15) NOT NULL,
name VARCHAR(20),
birthday TIMESTAMP,
gender VARCHAR(5),
email VARCHAR(30),
address VARCHAR(20),
tel VARCHAR(20),
mtel VARCHAR(20),
reg_date TIMESTAMP
);  
 */
public class Smember {
	private String id;
	private String password;
	private String name;
	private Timestamp birthday;
	private Timestamp reg_date;
	private String gender;
	private String email;
	private String address;
	private String tel;
	private String mtel;
	
	// 액션태그 연동가능한 자바빈 객체가 되려면
	// 필드, 게터세터, 기본생성자 3요소가 필요함.
	public Smember() {
	}
	
	public Smember(String id, String password, String name, Timestamp birthday, String gender, String email,
			String address, String tel, String mtel) {
		super();
		this.id = id;
		this.password = password;
		this.name = name;
		this.birthday = birthday;
		this.gender = gender;
		this.email = email;
		this.address = address;
		this.tel = tel;
		this.mtel = mtel;
	}

	public Timestamp getBirthday() {
		return birthday;
	}

	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Timestamp getReg_date() {
		return reg_date;
	}

	public void setReg_date(Timestamp reg_date) {
		this.reg_date = reg_date;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMtel() {
		return mtel;
	}

	public void setMtel(String mtel) {
		this.mtel = mtel;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Smember [id=").append(id).append(", password=").append(password).append(", name=").append(name)
				.append(", birthday=").append(birthday).append(", gender=").append(gender).append(", email=")
				.append(email).append(", reg_date=").append(reg_date).append(", address=").append(address)
				.append(", tel=").append(tel).append(", mtel=").append(mtel).append("]");
		return builder.toString();
	}

	
}//Member
