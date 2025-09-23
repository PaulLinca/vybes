package com.vybes.security;

import java.io.Serial;
import java.io.Serializable;

public record FirebasePrincipal(String uid, String email) implements Serializable {
    @Serial private static final long serialVersionUID = 1L;
}
