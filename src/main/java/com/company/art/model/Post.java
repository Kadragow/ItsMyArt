package com.company.art.model;

import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.ManyToAny;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_user_id")
    private User postUser;
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date;
    @Column(name = "imagine_name")
    private String imagineName;
    @Column(name = "imagine_type")
    private String imagineType;
    @Lob
    private byte[] data;
    @Column(name = "description")
    private String description;
    @Column(name = "tittle")
    private String tittle;
    @Transient
    private MultipartFile file;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPostUser(User user){
        this.postUser = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImagineName() {
        return imagineName;
    }

    public void setImagineName(String imagineName) {
        this.imagineName = imagineName;
    }

    public String getImagineType() {
        return imagineType;
    }

    public void setImagineType(String imagineType) {
        this.imagineType = imagineType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
