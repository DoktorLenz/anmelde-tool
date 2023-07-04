package de.stinner.anmeldetool.domain.shared.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GenderTest {

    @Test
    void test_string_to_Gender() {
        assertThat(Gender.MALE.getGender()).isEqualTo("männlich");
        assertThat(Gender.FEMALE.getGender()).isEqualTo("weiblich");
        assertThat(Gender.DIVERSE.getGender()).isEqualTo("divers");
        assertThat(Gender.UNDEFINED.getGender()).isEqualTo("keine Angabe");
    }
}
