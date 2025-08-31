package br.dev.gvitorguimaraes.pitagorikus.model;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Long id;

    @CreationTimestamp
    @Column(name = "inclusion", nullable = false, updatable = false)
    private LocalDateTime inclusion;

    @Column(name = "exclusion")
    private LocalDateTime exclusion;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 11;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getInclusion()
    {
        return inclusion;
    }

    public void setInclusion(LocalDateTime inclusion)
    {
        this.inclusion = inclusion;
    }

    public LocalDateTime getExclusion()
    {
        return exclusion;
    }

    public void setExclusion(LocalDateTime exclusion)
    {
        this.exclusion = exclusion;
    }

    public boolean isDeleted()
    {
        return exclusion != null;
    }

}
