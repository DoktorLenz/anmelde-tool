package de.stinner.anmeldetool.test.archunit;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

import java.util.Set;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;


@SuppressWarnings("unused")
@AnalyzeClasses(packages = "de.stinner.anmeldetool", importOptions = ImportOption.DoNotIncludeTests.class)
public class PortsTest {

    @ArchTest
    public static final ArchRule SPI_INTERFACES_MUST_HAVE_SPECIFIC_ENDINGS = classes()
            .that().resideInAPackage("de.stinner.anmeldetool.domain.ports.spi")
            .should().haveSimpleNameEndingWith("Adapter").orShould().haveSimpleNameEndingWith("Repository");
    @ArchTest
    public static final ArchRule API_INTERFACES_MUST_BE_ENDING_WITH_SERVICE = classes()
            .that().resideInAPackage("de.stinner.anmeldetool.domain.ports.api")
            .should().haveSimpleNameEndingWith("Service");
    @ArchTest
    public static final ArchRule PACKAGE_API_AND_SPI_SHOULD_ONLY_CONTAIN_INTERFACES = classes()
            .that().resideInAnyPackage(
                    "de.stinner.anmeldetool.domain.ports.api",
                    "de.stinner.anmeldetool.domain.ports.spi"
            )
            .should().beInterfaces();
    public static ArchCondition<JavaClass> HAVE_IMPLEMENTATION_IN_INFRASTRUCTURE = new ArchCondition<JavaClass>("has an implementation in package 'de.stinner.anmeldetool.infrastructure'") {
        @Override
        public void check(JavaClass item, ConditionEvents events) {
            var classes = new ClassFileImporter().importPackages("de.stinner.anmeldetool.infrastructure");

            boolean conditionMet = classes.stream().map(JavaClass::getInterfaces).flatMap(Set::stream).anyMatch(i -> i.getName().equals(item.getName()));

            events.add(new SimpleConditionEvent(item, conditionMet, item.getName() + " should have an implementation in package 'de.stinner.anmeldetool.infrastructure'"));
        }
    };
    @ArchTest
    public static final ArchRule SPI_INTERFACES_MUST_HAVE_IMPLEMENTATION = classes()
            .that().resideInAPackage("de.stinner.anmeldetool.domain.ports.spi")
            .and().areInterfaces()
            .should(HAVE_IMPLEMENTATION_IN_INFRASTRUCTURE);
    public static ArchCondition<JavaClass> HAVE_IMPLEMENTATION_IN_SERVICES = new ArchCondition<JavaClass>("has an implementation in package 'de.stinner.anmeldetool.domain.service'") {
        @Override
        public void check(JavaClass item, ConditionEvents events) {
            var classes = new ClassFileImporter().importPackages("de.stinner.anmeldetool.domain.service");

            boolean conditionMet = classes.stream().map(JavaClass::getInterfaces).flatMap(Set::stream).anyMatch(i -> i.getName().equals(item.getName()));

            events.add(new SimpleConditionEvent(item, conditionMet, item.getName() + " should have an implementation in package 'de.stinner.anmeldetool.domain.service'"));
        }
    };
    @ArchTest
    public static final ArchRule API_INTERFACES_MUST_HAVE_IMPLEMENTATION = classes()
            .that().resideInAPackage("de.stinner.anmeldetool.domain.ports.api")
            .and().areInterfaces()
            .should(HAVE_IMPLEMENTATION_IN_SERVICES);
}
