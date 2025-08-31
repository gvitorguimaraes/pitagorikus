package br.dev.gvitorguimaraes.pitagorikus.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "study_group_user_invitation")
public class StudyGroupUserInvitation extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_group", nullable = false)
    private StudyGroup group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user_invited", nullable = false)
    private User userInvited;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user_inviter", nullable = false)
    private User inviter;

    @Column
    private Boolean accepted;

    @Column(name = "acceptation_date")
    private LocalDateTime acceptanceDate;

    public StudyGroupUserInvitation(){}

    public StudyGroupUserInvitation(StudyGroup group, User userInvited, User inviter){
        setGroup(group);
        setUserInvited(userInvited);
        setInviter(inviter);
    }

    public StudyGroup getGroup() {
        return group;
    }

    public void setGroup(StudyGroup group) {
        this.group = group;
    }

    public User getUserInvited() {
        return userInvited;
    }

    public void setUserInvited(User userInvited) {
        this.userInvited = userInvited;
    }

    public User getInviter() {
        return inviter;
    }

    public void setInviter(User inviter) {
        this.inviter = inviter;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public LocalDateTime getAcceptanceDate() {
        return acceptanceDate;
    }

    public void setAcceptanceDate(LocalDateTime acceptanceDate) {
        this.acceptanceDate = acceptanceDate;
    }
}
