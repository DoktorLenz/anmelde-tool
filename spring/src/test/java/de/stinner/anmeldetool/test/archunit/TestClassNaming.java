package de.stinner.anmeldetool.test.archunit;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import de.stinner.anmeldetool.base.BaseControllerTest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@SuppressWarnings("unused")
@AnalyzeClasses(packages = "de.stinner.anmeldetool")
class TestClassNaming {


    @ArchTest
    static final ArchRule INTEGRATION_TESTS =
            classes()
                    .that().areAssignableTo(BaseControllerTest.class)
                    .and().doNotHaveModifier(JavaModifier.ABSTRACT)
                    .should().haveSimpleNameEndingWith("IT");
    private static final DescribedPredicate<JavaClass> haveAMethodAnnotatedWithTest = new DescribedPredicate<>(
            "have a method annotated with @Test") {
        @Override
        public boolean test(JavaClass input) {

            return isTestClass(input);

        }

        private boolean isTestClass(JavaClass input) {
            // Getting Inner Classes using Java Core

            Class<?>[] innerClasses = input.reflect().getDeclaredClasses();

            if (innerClasses.length == 0) {
                return isThereAtLeastOneMethodAnnotedWithTest(input);

            } else {

                // Converting Inner Classes into JavaClasses to use Arch Unit API

                JavaClasses javaInnerClasses = new ClassFileImporter().importClasses(List.of(innerClasses));

                Set<JavaClass> testClasses = javaInnerClasses.stream()
                        .filter(this::isThereAtLeastOneMethodAnnotedWithTest).collect(Collectors.toSet());

                return !testClasses.isEmpty();

            }

        }

        private boolean isThereAtLeastOneMethodAnnotedWithTest(JavaClass javaClass) {

            // Check if the list of methods annoted with @Test is not empty

            return !javaClass.getMethods().stream().filter(this::isAnnotedWithTest).collect(Collectors.toSet())
                    .isEmpty();

        }

        private boolean isAnnotedWithTest(JavaMethod method) {

            return method.isAnnotatedWith(Test.class);
        }
    };
    @ArchTest
    static final ArchRule UNIT_TESTS =
            classes()
                    .that(haveAMethodAnnotatedWithTest)
                    .and().areNotAssignableTo(BaseControllerTest.class)
                    .and().resideOutsideOfPackage("de.stinner.anmeldetool.test.archunit")
                    .should().haveSimpleNameEndingWith("Test");

}

