package com.alkemy.ong.dto;

<<<<<<< HEAD
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDto {

    @NotBlank(message = "the name field cannot be empty")
    @NotNull(message = "field name cannot be null")
    private String name;
    @NotBlank(message = "the name field cannot be empty")
    @NotNull(message = "field name cannot be null")
    private String content;
    private String image;

=======
import lombok.Data;

@Data
public class ActivityDto {
    private String id;
    private String name;
    private String content;
    private String image;
>>>>>>> 0cb91f87437c1d78ab4af39dfd701204d3d61d02
}
