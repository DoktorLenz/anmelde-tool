package de.stinner.anmeldetool.test.archunit;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.stereotype.Repository;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@SuppressWarnings("unused")
@AnalyzeClasses(packages = "de.stinner.anmeldetool", importOptions = ImportOption.DoNotIncludeTests.class)
public class RepositoryTest {

    @ArchTest
    public static final ArchRule REPOSITORIES_MUST_RESIDE_IN_INFRASTRUCTURE = classes()
            .that().areAnnotatedWith(Repository.class)
            .should().resideInAPackage("..infrastructure..");
}
