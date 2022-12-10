package com.forguta.libs.web.common;

import java.util.List;

public interface AuthSessionInformationSupplier {

    String userId();

    String username();

    String email();

    List<String> permissions();

    List<String> roles();
}
