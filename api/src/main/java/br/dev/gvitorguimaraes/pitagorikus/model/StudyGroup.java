package br.dev.gvitorguimaraes.pitagorikus.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "study_group")
public class StudyGroup extends BaseEntity{
	
	@Column(name="group_id", unique=true)
	private String groupId;
	
	@Column
	private String name;
	
	@Column
	private String description;
	
	@Column(name="url_photo")
	private String urlPhoto;
	
	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<StudyGroupUser> users;
	
	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<StudyGroupPost> posts;

	public StudyGroup() {
	}
	
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrlPhoto() {
		return urlPhoto;
	}

	public void setUrlPhoto(String urlPhoto) {
		this.urlPhoto = urlPhoto;
	}

	public List<StudyGroupUser> getUsers() {
		if (users == null) users = new ArrayList<>();
		return users;
	}

	public void setUsers(List<StudyGroupUser> users) {
		this.users = users;
	}

	public List<StudyGroupPost> getPosts() {
		if (posts == null) posts = new ArrayList<>();
		return posts;
	}

	public void setPosts(List<StudyGroupPost> posts) {
		this.posts = posts;
	}
	
}
