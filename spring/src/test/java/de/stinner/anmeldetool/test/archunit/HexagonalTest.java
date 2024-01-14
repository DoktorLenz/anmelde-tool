package de.stinner.anmeldetool.test.archunit;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@SuppressWarnings("unused")
@AnalyzeClasses(packages = "de.stinner.anmeldetool", importOptions = {ImportOption.DoNotIncludeTests.class})
public class HexagonalTest {
    @ArchTest
    public static final ArchRule CONTROLLERS_MAY_ONLY_ACCESS_DOMAIN_LAYER = layeredArchitecture().consideringAllDependencies()
            .layer("Application").definedBy("..application..")
            .layer("Domain").definedBy("..domain..")
            .layer("Infrastructure").definedBy("..infrastructure..")
            .whereLayer("Application").mayNotBeAccessedByAnyLayer()
            .whereLayer("Domain").mayOnlyBeAccessedByLayers("Application", "Infrastructure")
            .whereLayer("Infrastructure").mayNotBeAccessedByAnyLayer();
}
