package models.getusers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataUsersResponseModel {

    @JsonProperty("last_name")
    private String lastName;

    private int id;

    private String avatar;

    @JsonProperty("first_name")
    private String firstName;

    private String email;

}
