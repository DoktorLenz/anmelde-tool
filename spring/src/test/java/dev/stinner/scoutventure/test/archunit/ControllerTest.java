package dev.stinner.scoutventure.test.archunit;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@SuppressWarnings("unused")
@AnalyzeClasses(packages = "dev.stinner.scoutventure", importOptions = ImportOption.DoNotIncludeTests.class)
public class ControllerTest {

    @ArchTest
    public static final ArchRule CONTROLLERS_ARE_NOT_INSTANTIATED = noClasses()
            .should().dependOnClassesThat()
            .areAnnotatedWith(Controller.class)
            .andShould()
            .dependOnClassesThat()
            .areAnnotatedWith(RestController.class);

    @ArchTest
    public static final ArchRule CONTROLLERS_MUST_END_WITH_CONTROLLER = classes()
            .that().areAnnotatedWith(Controller.class)
            .or().areAnnotatedWith(RestController.class)
            .should()
            .haveSimpleNameEndingWith("Controller");

    @ArchTest
    public static final ArchRule ALL_CONTROLLERS_MUST_RESIDE_INSIDE_APPLICATION = classes()
            .that().areAnnotatedWith(Controller.class)
            .or().areAnnotatedWith(RestController.class)
            .should().resideInAPackage("..application..");

    @ArchTest
    public static final ArchRule REST_CONTROLLERS_MUST_RESIDE_INSIDE_REST_CONTROLLER_PACKAGE = classes()
            .that().areAnnotatedWith(RestController.class)
            .should().resideInAPackage("..rest.controllers..");

}
