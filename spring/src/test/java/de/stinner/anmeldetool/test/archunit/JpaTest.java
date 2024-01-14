package de.stinner.anmeldetool.test.archunit;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.core.domain.JavaType;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashSet;
import java.util.Set;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@SuppressWarnings("unused")
@AnalyzeClasses(packages = "de.stinner.anmeldetool", importOptions = ImportOption.DoNotIncludeTests.class)
class JpaTest {

    @ArchTest
    public static final ArchRule INTERFACES_EXTENDING_JPA_REPOSITORY_MUST_BE_ENDING_WITH_JPA_REPOSITORY = classes()
            .that().areInterfaces().and().areAssignableTo(JpaRepository.class)
            .should().haveSimpleNameEndingWith("JpaRepository");


    @ArchTest
    public static final ArchRule CLASSES_MUST_NOT_BE_ENDING_WITH_JPA_REPOSITORY = classes()
            .that().areNotInterfaces()
            .should().haveSimpleNameNotEndingWith("JpaRepository");


    @ArchTest
    public static final ArchRule ONLY_ENTITIES_IN_MODELS_PACKAGE = classes()
            .that().resideInAPackage("de.stinner.anmeldetool.infrastructure.jpa.models")
            .and().doNotHaveModifier(JavaModifier.SYNTHETIC)
            .should().haveSimpleNameEndingWith("Entity");
    @ArchTest
    public static final ArchRule JPA_SPI_CLASSES_MUST_RESIDE_IN_JPA_PACKAGE = classes()
            .that().haveSimpleNameEndingWith("JpaSpi")
            .should().resideInAPackage("..jpa..");
    @ArchTest
    public static final ArchRule JPA_REPOSITORIES_MUST_RESIDE_IN_JPA_PACKAGE = classes()
            .that().areAssignableTo(JpaRepository.class)
            .should().resideInAPackage("..jpa..");
    public static ArchCondition<JavaClass> IMPLEMENT_SPI_INTERFACE = new ArchCondition<JavaClass>("implements an SPI interface") {
        @Override
        public void check(JavaClass javaClass, ConditionEvents conditionEvents) {
            var classes = new ClassFileImporter().importPackages("de.stinner.anmeldetool.domain.ports.spi");
            Set<JavaType> spiInterfaces = new HashSet<>(classes);

            boolean conditionMet = javaClass.getInterfaces().stream()
                    .anyMatch(classInterface -> spiInterfaces.stream()
                            .anyMatch(spiInterface -> classInterface.getName().equals(spiInterface.getName()))
                    );

            conditionEvents.add(new SimpleConditionEvent(javaClass, conditionMet, javaClass.getName() + " should implement an SPI interface"));

        }
    };
    @ArchTest
    public static final ArchRule CLASSES_MUST_BE_ENDING_WITH_JPA_SPI_AND_MUST_IMPLEMENT_SPI_INTERFACE = classes()
            .that().resideInAPackage("..jpa..")
            .and().areNotInterfaces()
            .and().haveSimpleNameNotContaining("Entity")
            .and().doNotHaveModifier(JavaModifier.SYNTHETIC)
            .should().haveSimpleNameEndingWith("JpaSpi")
            .andShould(IMPLEMENT_SPI_INTERFACE);
}
