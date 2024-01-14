package de.stinner.anmeldetool.test.archunit;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.stereotype.Service;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@SuppressWarnings("unused")
@AnalyzeClasses(packages = "de.stinner.anmeldetool", importOptions = ImportOption.DoNotIncludeTests.class)
public class ServiceTest {

    @ArchTest
    public static final ArchRule SERVICES_MUST_BE_ANNOTATED_WITH_SERVICE = classes()
            .that().resideInAPackage("..services..")
            .should().beAnnotatedWith(Service.class);

    @ArchTest
    public static final ArchRule SERVICES_MUST_RESIDE_IN_PACKAGE_SERVICES = classes()
            .that().areAnnotatedWith(Service.class)
            .should().resideInAnyPackage("..services..");

    @ArchTest
    public static final ArchRule SERVICES_MUST_NOT_RESIDE_OUTSIDE_DOMAIN = classes()
            .that().areAnnotatedWith(Service.class)
            .should().resideInAPackage("..domain..");
}
