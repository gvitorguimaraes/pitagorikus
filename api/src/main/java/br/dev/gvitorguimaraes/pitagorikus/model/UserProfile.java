package br.dev.gvitorguimaraes.pitagorikus.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.dev.gvitorguimaraes.pitagorikus.model.enums.InterestsProfileEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "app_user_profile")
public class UserProfile extends BaseEntity {
	
	@OneToOne
	@JoinColumn(name = "id_app_user", nullable = false)
	private User user;
	
	@Column(length=255)
	private String profileImageUrl;
	
	@Column
	private LocalDate dateOfBirth;
	
	@Column(length=2000)
	private String about;
	
	/**
	 * List of interests (ENUM code comma separated)
	 */
	@Column
	private String interests;

	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getInterests() {
		return interests;
	}

	public void setInterests(String interests) {
		this.interests = interests;
	}
	
	public List<InterestsProfileEnum> getInterestsList() {
		List<InterestsProfileEnum> interestsList = new ArrayList<>();
        for (String code : interests.split(",")) {
        	interestsList.add(InterestsProfileEnum.fromCode(code));
        }
        return interestsList;
	}
	
	public void setInterestsList(List<InterestsProfileEnum> interestsList) {
		String interestStr = "";
		for (InterestsProfileEnum interest : interestsList) {
			if (!interestStr.isBlank()) {
				interestStr += ",";
			}
			interestStr += interest.getCode();
		}
		this.interests = interestStr;
	}
}
