package dev.praise.konnect.services;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AliasGeneratorTest {
    @Test
    public void testThat_aliasGeneratorGeneratesRandomNamees() {
        AliasGeneratorService aliasService = new AliasGeneratorServiceImpl();

        String aliasGeneratorResponse = aliasService.generateAlias();

        assertThat(aliasGeneratorResponse).isNotNull();

        assertThat(aliasGeneratorResponse).matches("^[A-Za-z]+[0-9]+$");

    }


}
