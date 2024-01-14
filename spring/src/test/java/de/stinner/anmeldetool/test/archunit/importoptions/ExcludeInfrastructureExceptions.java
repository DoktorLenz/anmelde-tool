package de.stinner.anmeldetool.test.archunit.importoptions;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.core.importer.Location;

import java.util.regex.Pattern;

public class ExcludeInfrastructureExceptions implements ImportOption {
    @Override
    public boolean includes(Location location) {
        return !location.matches(Pattern.compile(".*/infrastructure/(.+/)*exceptions/.*"));
//        return !(location.contains("/infrastructure/") && location.contains("/exceptions"));
    }
}
