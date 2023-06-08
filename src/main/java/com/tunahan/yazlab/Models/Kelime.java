package com.tunahan.yazlab.Models;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;


@Document (collection = "kelimeler")
public class Kelime {

	@Transient
    public static final String SEQUENCE_NAME = "users_sequence";
	
    @Id
	private String id;

	private String birlesen;

	private Boolean bool;

	private List<String> list;

	private String time;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

	public Kelime() {
	}
	
    public Kelime(String birlesen, Boolean bool, List<String> list, String time, LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.birlesen = birlesen;
        this.bool = bool;
        this.list = list;
        this.time = time;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static String getSequenceName() {
		return SEQUENCE_NAME;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBirlesen() {
		return birlesen;
	}

	public void setBirlesen(String birlesen) {
		this.birlesen = birlesen;
	}

	public Boolean getBool() {
		return bool;
	}

	public void setBool(Boolean bool) {
		this.bool = bool;
	}

	public List<String> getList() {
        return list;
    }


    public void setList(List<String> list) {
        this.list = list;
    }


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getTime() {
        return time;
    }


    public void setTime(String time) {
        this.time = time;
    }

	@Override
	public String toString() {
		return "Kelime [id=" + id + ", birlesen=" + birlesen + ", bool=" + bool + ", list=" + list + ", time=" + time
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}


	
}
