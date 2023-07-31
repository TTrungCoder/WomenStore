package com.web.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO implements Serializable {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    private String fullname;
    private String email;
    private String photo;

}

