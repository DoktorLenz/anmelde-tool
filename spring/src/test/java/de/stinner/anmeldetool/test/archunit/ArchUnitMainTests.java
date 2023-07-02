package de.stinner.anmeldetool.test.archunit;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import jakarta.persistence.Entity;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

@SuppressWarnings("unused")
@AnalyzeClasses(packages = "de.stinner.anmeldetool", importOptions = ImportOption.DoNotIncludeTests.class)
class ArchUnitMainTests {

    @ArchTest
    static final ArchRule REPOSITORY_NAMING_CONVENTION =
            classes()
                    .that().areAssignableTo(Repository.class)
                    .or()
                    .implement(Repository.class)
                    .should()
                    .haveSimpleNameEndingWith("Repository");
    @ArchTest
    static final ArchRule ALL_ENTITIES_MATCHING_NAMING_PATTERN =
            classes()
                    .that()
                    .areAnnotatedWith(Entity.class)
                    .should()
                    .haveSimpleNameEndingWith("Entity");
    @ArchTest
    static final ArchRule CONTROLLER_NAMING_CONVENTION =
            classes()
                    .that()
                    .areAnnotatedWith(Controller.class)
                    .or()
                    .areAnnotatedWith(RestController.class)
                    .should()
                    .haveSimpleNameEndingWith("Controller");
    @ArchTest
    static final ArchRule CONTROLLERS_NOT_INSTANTIATED =
            noClasses()
                    .should()
                    .dependOnClassesThat()
                    .areAnnotatedWith(Controller.class)
                    .andShould()
                    .dependOnClassesThat()
                    .areAnnotatedWith(RestController.class);
    private static final String PERSISTENCE_PACKAGE_MATCHER = "..persistence..";
    @ArchTest
    static final ArchRule REPOSITORIES_ONLY_IN_PERSISTENCE =
            classes()
                    .that()
                    .areAssignableTo(Repository.class)
                    .or()
                    .implement(Repository.class)
                    .should()
                    .resideInAPackage(PERSISTENCE_PACKAGE_MATCHER);
    @ArchTest
    static final ArchRule ALL_ENTITIES_IN_PERSISTENCE =
            classes()
                    .that()
                    .areAnnotatedWith(Entity.class)
                    .should()
                    .resideInAPackage(PERSISTENCE_PACKAGE_MATCHER);
    @ArchTest
    static final ArchRule NO_DTOS_IN_PERSISTENCE =
            noClasses()
                    .that().haveSimpleNameContaining("Dto")
                    .should()
                    .resideInAPackage(PERSISTENCE_PACKAGE_MATCHER);

    @ArchTest
    static final ArchRule NO_REPOSITORIES_IN_CONTROLLERS =
            noClasses()
                    .that().areAnnotatedWith(Controller.class)
                    .or()
                    .areAnnotatedWith(RestController.class)
                    .should()
                    .dependOnClassesThat()
                    .resideInAPackage(PERSISTENCE_PACKAGE_MATCHER)
                    .andShould()
                    .dependOnClassesThat()
                    .haveSimpleNameContaining("Repository");

    //    @ArchTest
//    static final ArchRule NO_ENTITIES_IN_CONTROLLERS =
//            noClasses()
//                    .that()
//                    .areAnnotatedWith(Controller.class)
//                    .or()
//                    .areAnnotatedWith(RestController.class)
//                    .should()
//                    .dependOnClassesThat()
//                    .resideInAPackage(PERSISTENCE_PACKAGE_MATCHER)
//                    .andShould()
//                    .dependOnClassesThat()
//                    .haveSimpleNameContaining("Entity");
    private static final String API_PACKAGE_MATCHER = "..api..";
    @ArchTest
    static final ArchRule ALL_CONTROLLERS_IN_API_PACKAGE =
            classes()
                    .that()
                    .areAnnotatedWith(Controller.class)
                    .or()
                    .areAnnotatedWith(RestController.class)
                    .should()
                    .resideInAPackage(API_PACKAGE_MATCHER);

    @ArchTest
    static final ArchRule ALL_DTOS_IN_API_PACKAGE =
            classes()
                    .that()
                    .haveSimpleNameContaining("Dto")
                    .should()
                    .resideInAPackage(API_PACKAGE_MATCHER);
    private static final String SERVICE_PACKAGE_MATCHER = "..service..";
    @ArchTest
    static final ArchRule ALL_PUBLIC_REPOSITORY_CALLS_MUST_BE_TRANSACTIONAL =
            methods()
                    .that().arePublic()
                    .and().areNotStatic()
                    .and().haveNameMatching("^.*(get|find|delete|save|update?i)(?i).*")
                    .and().areDeclaredInClassesThat().resideInAPackage(SERVICE_PACKAGE_MATCHER)
                    .and().areDeclaredInClassesThat().haveSimpleNameEndingWith("Service")
                    .should()
                    .beAnnotatedWith(Transactional.class);

}
