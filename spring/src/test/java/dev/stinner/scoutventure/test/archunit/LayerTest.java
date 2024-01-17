package dev.stinner.scoutventure.test.archunit;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@SuppressWarnings("unused")
@AnalyzeClasses(packages = "dev.stinner.scoutventure", importOptions = {ImportOption.DoNotIncludeTests.class})
public class LayerTest {
    @ArchTest
    public static final ArchRule LAYER_SEPARATION = layeredArchitecture().consideringAllDependencies()
            .layer("Application").definedBy("..application..")
            .layer("Domain").definedBy("..domain..")
            .layer("Infrastructure").definedBy("..infrastructure..")
            .whereLayer("Application").mayNotBeAccessedByAnyLayer()
            .whereLayer("Domain").mayOnlyBeAccessedByLayers("Application", "Infrastructure")
            .whereLayer("Infrastructure").mayNotBeAccessedByAnyLayer();


    @ArchTest
    public static final ArchRule CONTROLLERS_MAY_NOT_DIRECTLY_ACCESS_API_SERVICES = layeredArchitecture().consideringAllDependencies()
            .layer("Controllers").definedBy("..controllers")
            .layer("Services").definedBy("..services..")
            .whereLayer("Services").mayNotBeAccessedByAnyLayer();

    @ArchTest
    public static final ArchRule SERVICES_MAY_NOT_DIRECTLY_ACCESS_SPI = layeredArchitecture().consideringAllDependencies()
            .layer("Services").definedBy("..services..")
            .layer("Infrastructure").definedBy("..infrastructure..")
            .whereLayer("Infrastructure").mayNotBeAccessedByAnyLayer();
}
