package de.stinner.anmeldetool.test.archunit;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import jakarta.persistence.Entity;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@SuppressWarnings("unused")
@AnalyzeClasses(packages = "de.stinner.anmeldetool", importOptions = ImportOption.DoNotIncludeTests.class)
public class EntityTest {

    @ArchTest
    public static final ArchRule ALL_ENTITIES_MATCHING_NAMING_PATTERN =
            classes()
                    .that()
                    .areAnnotatedWith(Entity.class)
                    .should()
                    .haveSimpleNameEndingWith("Entity");

    @ArchTest
    public static final ArchRule ALL_ENTITIES_MUST_RESIDE_IN_INFRASTRUCTURE = classes()
            .that().areAnnotatedWith(Entity.class)
            .or().haveSimpleNameEndingWith("Entity")
            .should().resideInAPackage("..infrastructure..");
}
