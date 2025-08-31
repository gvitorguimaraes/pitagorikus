package br.dev.gvitorguimaraes.pitagorikus.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "study_group_post")
public class StudyGroupPost extends BaseEntity {
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_group", nullable = false)
	private StudyGroup group;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_group_user", nullable = false)
	private StudyGroupUser user;
	
	@Column
	private String title;
	
	@Column
	private String description;
	
	@Column
	private LocalDate date;
	
	@Column
	private Long activityTimeInSeconds;
	
	@Column
	private String urlPhoto;

	public StudyGroup getGroup() {
		return group;
	}

	public void setGroup(StudyGroup group) {
		this.group = group;
	}

	public StudyGroupUser getUser() {
		return user;
	}

	public void setUser(StudyGroupUser user) {
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Long getActivityTimeInSeconds() {
		return activityTimeInSeconds;
	}

	public void setActivityTimeInSeconds(Long activityTimeInSeconds) {
		this.activityTimeInSeconds = activityTimeInSeconds;
	}

	public String getUrlPhoto() {
		return urlPhoto;
	}

	public void setUrlPhoto(String urlPhoto) {
		this.urlPhoto = urlPhoto;
	}
	
}
