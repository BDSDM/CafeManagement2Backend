package societedestin.cafemanagement2.dto;

import jakarta.persistence.Column;

public class UserDTO {
    private Long id;
    private String name;

    @Column(name = "contact_number")
    private String contactNumber;

    private String email;
    private String password;
    private String status;
    private String role;

    public UserDTO() {}

    public UserDTO(Long id,String name, String contactNumber,String email, String password,String status,String role) {
        this.id = id;
        this.name=name;
        this.contactNumber = contactNumber;
        this.email = email;
        this.password = password;
        this.status = status;
        this.role = role;
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
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}

