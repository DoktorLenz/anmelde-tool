package dev.stinner.scoutventure.test.archunit;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaType;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@SuppressWarnings("unused")
@AnalyzeClasses(packages = "dev.stinner.scoutventure", importOptions = ImportOption.DoNotIncludeTests.class)
public class RepositoryTest {

    @ArchTest
    public static final ArchRule REPOSITORIES_MUST_RESIDE_IN_INFRASTRUCTURE = classes()
            .that().areAnnotatedWith(Repository.class)
            .should().resideInAPackage("..infrastructure..");

    public static ArchCondition<JavaClass> IMPLEMENT_SPI_INTERFACE = new ArchCondition<JavaClass>("implements an SPI interface") {
        @Override
        public void check(JavaClass javaClass, ConditionEvents conditionEvents) {
            var classes = new ClassFileImporter().importPackages("dev.stinner.scoutventure.domain.ports.spi");
            Set<JavaType> spiInterfaces = new HashSet<>(classes);

            boolean conditionMet = javaClass.getInterfaces().stream()
                    .anyMatch(classInterface -> spiInterfaces.stream()
                            .anyMatch(spiInterface -> classInterface.getName().equals(spiInterface.getName()))
                    );

            conditionEvents.add(new SimpleConditionEvent(javaClass, conditionMet, javaClass.getName() + " should implement an SPI interface"));

        }
    };
    @ArchTest
    public static final ArchRule REPOSITORIES_MUST_IMPLEMENT_SPI = classes()
            .that().areAnnotatedWith(Repository.class)
            .should(IMPLEMENT_SPI_INTERFACE);

}
