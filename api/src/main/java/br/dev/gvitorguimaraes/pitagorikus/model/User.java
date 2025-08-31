package br.dev.gvitorguimaraes.pitagorikus.model;

import java.time.LocalDateTime;

import br.dev.gvitorguimaraes.pitagorikus.model.enums.UserRoleEnum;
import br.dev.gvitorguimaraes.pitagorikus.model.enums.UserRoleEnumConverter;
import jakarta.persistence.*;

@Entity
@Table(name = "app_user")
public class User extends BaseEntity {

	@Column(length = 300, nullable = false)
    private String name;

    @Column(length = 200, nullable = false, unique = true)
    private String email;
    
    @Column(length = 100, nullable = false, unique = true)
    private String username;

    @Column(length = 128, nullable = false)
    private String password;

    @Column
    private Boolean active;

    @Column(name = "last_edit_data")
    private LocalDateTime lastEditData;

    @Column(name = "last_login_data")
    private LocalDateTime lastLoginData;

    @Convert(converter = UserRoleEnumConverter.class)
    @Column(nullable = false)
    private UserRoleEnum role;
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserProfile profile;

    public User() {
        this.setInclusion(LocalDateTime.now());
    }
    
    public User(String name, String email, String username, String password) {
    	super();
    	this.setName(name);
    	this.setEmail(email);
    	this.setUsername(username);
    	this.setPassword(password);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
    
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Boolean getActive()
    {
        return active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }

    public LocalDateTime getLastEditData()
    {
        return lastEditData;
    }

    public void setLastEditData(LocalDateTime lastEditData)
    {
        this.lastEditData = lastEditData;
    }

    public LocalDateTime getLastLoginData()
    {
        return lastLoginData;
    }

    public void setLastLoginData(LocalDateTime lastLoginData)
    {
        this.lastLoginData = lastLoginData;
    }

    public UserRoleEnum getRole()
    {
        return role;
    }

    public void setRole(UserRoleEnum role)
    {
        this.role = role;
    }

	public UserProfile getProfile() {
		return profile;
	}

	public void setProfile(UserProfile profile) {
		this.profile = profile;
	}
    
}