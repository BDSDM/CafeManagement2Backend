package societedestin.cafemanagement2.dto;

import jakarta.persistence.Column;

public class UserDTO {
    private Long id;
    private String name;

    @Column(name = "contact_number")
    private String contactNumber;

    private String email;
    private String password;

    public UserDTO() {}

    public UserDTO(Long id,String name, String contactNumber,String email, String password) {
        this.id = id;
        this.name=name;
        this.contactNumber = contactNumber;
        this.email = email;

        this.password = password;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getContactNumber() { return contactNumber; }

    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
}
