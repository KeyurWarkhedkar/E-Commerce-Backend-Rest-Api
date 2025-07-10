package com.keyur.E_Commerce.Entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
public class UserSession {
	//fields
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer sessionId;
	
	@Column(unique = true)
	private String token;

	private Integer userId;
	
	private String userType;
	
	private LocalDateTime sessionStartTime;
	
	private LocalDateTime sessionEndTime;


	//no args constructor
	public UserSession() {
	}


	//getters and setters
	public Integer getSessionId() {
		return sessionId;
	}

	public void setSessionId(Integer sessionId) {
		this.sessionId = sessionId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public LocalDateTime getSessionStartTime() {
		return sessionStartTime;
	}

	public void setSessionStartTime(LocalDateTime sessionStartTime) {
		this.sessionStartTime = sessionStartTime;
	}

	public LocalDateTime getSessionEndTime() {
		return sessionEndTime;
	}

	public void setSessionEndTime(LocalDateTime sessionEndTime) {
		this.sessionEndTime = sessionEndTime;
	}


	//toString()
	@Override
	public String toString() {
		return "UserSession{" +
				"sessionId=" + sessionId +
				", token='" + token + '\'' +
				", userId=" + userId +
				", userType='" + userType + '\'' +
				", sessionStartTime=" + sessionStartTime +
				", sessionEndTime=" + sessionEndTime +
				'}';
	}
}
