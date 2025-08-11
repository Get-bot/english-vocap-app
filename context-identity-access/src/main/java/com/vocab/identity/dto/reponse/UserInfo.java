package com.vocab.identity.dto.reponse;

import java.util.Set;
import lombok.Builder;

@Builder
public record UserInfo(
        Long id,
        String email,
        Set<String> roles
) {

}
