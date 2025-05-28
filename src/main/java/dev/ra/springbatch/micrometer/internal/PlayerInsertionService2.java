package dev.ra.springbatch.micrometer.internal;

import dev.ra.springbatch.micrometer.PropertyReader;
import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlayerInsertionService2 {
    private static final Logger _log = LoggerFactory.getLogger(PlayerInsertionService2.class);

    private BasicDataSource datasource;

    private PropertyReader propertyReader;

    public void insertPlayers(String playerId,
                              String firstName,
                              String lastName,
                              String yearOfBirth,
                              String yearOfDraft) throws SQLException {
        PreparedStatement ps = null;
        Connection connection = null;
        CallableStatement cs = null;

        try {
            datasource.setRemoveAbandonedTimeout(300);
            datasource.setRemoveAbandoned(true);

            connection = datasource.getConnection();
            String query = "CALL player_by_birth(?, ?, ?, ?, ?)";
            ps = connection.prepareStatement(query);
            ps.setString(1, playerId);
   if (capitalize()) {
       ps.setString(2, firstName.toUpperCase());
   } else {
       ps.setString(2, firstName);
   }


            ps.setString(3, lastName);
            ps.setLong(4, Long.parseLong(yearOfBirth));
            ps.setLong(5, Long.parseLong(yearOfDraft));

            ps.executeUpdate();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }

                if (connection != null) {
                    if (!connection.getAutoCommit()) {
                        connection.commit();
                        connection.endRequest();
                    }
                    if (!connection.isClosed()) {
                        connection.close();
                    }
                }
            } catch (SQLException e) {
                _log.error("Exception occurred:", e);
            }
        }

    }

    public BasicDataSource getDatasource() {
        return datasource;
    }

    public void setDatasource(BasicDataSource datasource) {
        this.datasource = datasource;
    }

    public void setPropertyReader(PropertyReader propertyReader) {
        this.propertyReader = propertyReader;
    }

    public boolean capitalize(){
        return Boolean.parseBoolean(propertyReader.getProperties().getProperty("capitalize"));
    }
}
