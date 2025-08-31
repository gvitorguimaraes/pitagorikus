package br.dev.gvitorguimaraes.pitagorikus.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "study_group_user")
public class StudyGroupUser extends BaseEntity{
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_user", nullable = false)
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_group", nullable = false)
	private StudyGroup group;
	
	@Column(name="group_admin")
	private Boolean groupAdmin;
	
	//
	// pontuação será calculada com base em: 
	// - consistencia (dias consecutivos)
	// - tempo de atividade total 
	// 
	
	@Column
	private Integer score;
	
	
	public StudyGroupUser(){
		this.groupAdmin = false;
		this.score = 0;
	}
	
	public StudyGroupUser(User user, StudyGroup group){
		super();
		this.user = user;
		this.group = group;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public StudyGroup getGroup() {
		return group;
	}

	public void setGroup(StudyGroup group) {
		this.group = group;
	}

	public boolean isGroupAdmin() {
		return groupAdmin;
	}

	public void setGroupAdmin(boolean isGroupAdmin) {
		this.groupAdmin = isGroupAdmin;
	}

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Boolean getGroupAdmin() {
        return groupAdmin;
    }

    public void setGroupAdmin(Boolean groupAdmin) {
        this.groupAdmin = groupAdmin;
    }
}
