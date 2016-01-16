package models;

public class User {
	private int id;
	private String firstName;
	private String lastName;
	private String gender;
	private int age;
	private String country;
	private String zip;
	private String street;
	private String employment;
	
	
	
	public String getEmployment() {
		return employment;
	}


	public void setEmployment(String employment) {
		this.employment = employment;
	}


	public User(int id, String first, String last, 
			String gender, int age, String country, 
			String employment,
			String zip, String street){
		this.id = id;
		this.firstName = first;
		this.lastName = last;
		this.gender = gender;
		this.age = age;
		this.country = country;
		this.zip = zip;
		this.street = street;
		this.employment = employment;
	}

	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getZip() {
		return zip;
	}


	public void setZip(String zip) {
		this.zip = zip;
	}


	public String getStreet() {
		return street;
	}


	public void setStreet(String street) {
		this.street = street;
	}


	@Override
    public boolean equals(Object object){
        boolean sameSame = false;

        if (object != null && object instanceof User){
        	return this.firstName.equals(((User) object).getFirstName()) && 
        			this.lastName == ((User) object).getLastName() &&
        			this.id == ((User) object).getId();
        }
        return sameSame;
    }

}
