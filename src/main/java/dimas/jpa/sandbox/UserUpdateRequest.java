package dimas.jpa.sandbox;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.ToString;

import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@ToString
public class UserUpdateRequest {

    public Optional<String> username;

    public Optional<String> password;
}
