package dev.stinner.scoutventure.test.archunit;

import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@SuppressWarnings("unused")
@AnalyzeClasses(packages = "dev.stinner.scoutventure", importOptions = ImportOption.DoNotIncludeTests.class)
public class DtoTest {

    @ArchTest
    public static final ArchRule DTO_SHOULD_END_WITH_DTO = classes()
            .that().resideInAPackage("..application..models..")
            .and().doNotHaveModifier(JavaModifier.SYNTHETIC)
            .should().haveSimpleNameEndingWith("Dto");

    @ArchTest
    public static final ArchRule DTO_MUST_RESIDE_INSIDE_APPLICATION = classes()
            .that().haveSimpleNameContaining("Dto")
            .should().resideInAPackage("..application..");
}
