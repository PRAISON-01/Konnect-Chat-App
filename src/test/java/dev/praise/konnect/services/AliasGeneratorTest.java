package dev.praise.konnect.services;

import dev.praise.konnect.dtos.response.AliasGeneratorResponse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AliasGeneratorTest {
    @Test
    public void testThat_aliasGeneratorGeneratesRandomNamees() {
        AliasGeneratorService aliasService = new AliasGeneratorServiceImpl();

        AliasGeneratorResponse aliasGeneratorResponse = aliasService.generateAlias();

        assertThat(aliasGeneratorResponse).isNotNull();

        assertThat(aliasGeneratorResponse.getAlias()).matches("^[A-Za-z]+[0-9]+$");

    }


}
