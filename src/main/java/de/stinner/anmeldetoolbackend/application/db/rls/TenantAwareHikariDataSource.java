package de.stinner.anmeldetoolbackend.application.db.rls;

import com.zaxxer.hikari.HikariDataSource;
import de.stinner.anmeldetoolbackend.application.authentication.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

@Slf4j
public class TenantAwareHikariDataSource extends HikariDataSource {

    private static final UUID APPLICATION_TENANT = UUID.fromString("00000000-0000-0000-0000-000000000000");

    @Override
    public Connection getConnection() throws SQLException {
        Connection connection = super.getConnection();

        UUID currentTenantId = APPLICATION_TENANT;

        //Checks, whether an active HTTP-Session exists
        if (RequestContextHolder.getRequestAttributes() != null) {

            currentTenantId = UserInfo.getSubjectUserId();

            if (currentTenantId.equals(APPLICATION_TENANT)) {
                log.info("UserId equal to Application_tenant_id.");
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            log.info("Trying to build new DB-Connection for User {}", currentTenantId);
        } else {
            log.info("Trying to build new DB-Connection for Application");
        }


        try (Statement sql = connection.createStatement()) {
            sql.execute("SET app.current_tenant = '" + currentTenantId + "'");
        }

        return connection;
    }

}
