package com.company.art.model;

import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_user_id")
    private User postUser;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    private Calendar date;
    @Column(name = "imagine_name")
    private String imagineName;
    @Column(name = "imagine_type")
    private String imagineType;
    @Lob
    private byte[] data;
    @Column(name = "description", length = 500)
    @Length(max = 500)
    private String description;
    @Column(name = "tittle", length = 30)
    @Length(max = 30)
    private String tittle;
    @Nullable
    @OneToMany(mappedBy = "likedPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PostLikes> postLikes;
    @Transient
//    @NotNull
    private MultipartFile file;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getAuthor(){
        return this.postUser;
    }

    public void setPostUser(User user){
        this.postUser = user;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
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

    public Set<PostLikes> getPostLikes() {
        return postLikes;
    }

    public void setPostLikes(Set<PostLikes> postLikes) {
        this.postLikes = postLikes;
    }
}
