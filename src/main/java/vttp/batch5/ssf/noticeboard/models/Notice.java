package vttp.batch5.ssf.noticeboard.models;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Notice {
    
    @NotBlank(message = "Title is mandatory")
    @Size(min = 3, max = 128, message = "Title must be between 3 to 128 characters")
    private String title;

    @NotBlank(message = "Poster email is mandatory")
    @Email(message = "Poster email input does not conform to email format")
    private String poster;

    @NotBlank(message = "Post date is mandatory")
    @Future(message = "Post date must be in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date postDate;

    @NotBlank(message = "Category is mandatory")
    private List<String> categories;

    @NotBlank(message = "Contents of notice is mandatory")
    private String text;

    public Notice() {
    }

    public Notice(
            @NotBlank(message = "Title is mandatory") @Size(min = 3, max = 128, message = "Title must be between 3 to 128 characters") String title,
            @NotBlank(message = "Poster email is mandatory") @Email(message = "Poster email input does not conform to email format") String poster,
            @NotBlank(message = "Post date is mandatory") @Future(message = "Post date must be in the future") Date postDate,
            @NotBlank(message = "Category is mandatory") List<String> categories,
            @NotBlank(message = "Contents of notice is mandatory") String text) {
        this.title = title;
        this.poster = poster;
        this.postDate = postDate;
        this.categories = categories;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return title + "," + poster + "," + postDate + "," + categories + "," + text;
    }

}
