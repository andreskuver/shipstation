package ss.ss_service.models;

public class Address {
	private String name;
	private String company;
	private String street1;
	private String street2;
	private String street3;
	private String city;
	private String state;
	private String postalCode;
	private String country;
	private String phone;
	private Boolean residential;
	private String addressVerified;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getStreet1() {
		return street1;
	}
	public void setStreet1(String street1) {
		this.street1 = street1;
	}
	public String getStreet2() {
		return street2;
	}
	public void setStreet2(String street2) {
		this.street2 = street2;
	}
	public String getStreet3() {
		return street3;
	}
	public void setStreet3(String street3) {
		this.street3 = street3;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Boolean getResidential() {
		return residential;
	}
	public void setResidential(Boolean residential) {
		this.residential = residential;
	}
	public String getAddressVerified() {
		return addressVerified;
	}
	public void setAddressVerified(String addressVerified) {
		this.addressVerified = addressVerified;
	}
}
