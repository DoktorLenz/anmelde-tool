package dev.stinner.scoutventure.infrastructure.nami.client;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClientGenderTest {

    @Test
    void test_string_to_Gender() {
        Assertions.assertThat(ClientGender.MALE.getGender()).isEqualTo("männlich");
        assertThat(ClientGender.FEMALE.getGender()).isEqualTo("weiblich");
        assertThat(ClientGender.DIVERSE.getGender()).isEqualTo("divers");
        assertThat(ClientGender.UNDEFINED.getGender()).isEqualTo("keine Angabe");
    }
}
