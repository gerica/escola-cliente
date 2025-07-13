package com.escola.client.model.response;

import com.escola.client.model.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;
    private String username;
    private String firstName;
    private String lastName;
    private Set<Role> roles;

}