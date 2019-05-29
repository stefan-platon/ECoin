package ecoin.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "authentication")
public class Authentication {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "user_id", nullable = false)
	private User userObj;

	@Column(name = "token", nullable = false, unique = true)
	private String token;

	@Column(name = "created_time", nullable = false)
	private LocalDateTime createdTime;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the userObj
	 */
	public User getUserObj() {
		return userObj;
	}

	/**
	 * @param userObj the userObj to set
	 */
	public void setUserObj(User userObj) {
		this.userObj = userObj;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the createdTime
	 */
	public LocalDateTime getCreatedTime() {
		return createdTime;
	}

	/**
	 * @param createdTime the createdTime to set
	 */
	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}

	@PrePersist
	private void initializeCreatedTime() {
		this.createdTime = LocalDateTime.now();
	}

}
