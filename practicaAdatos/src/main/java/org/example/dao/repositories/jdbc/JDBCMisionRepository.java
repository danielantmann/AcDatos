package org.example.dao.repositories.jdbc;

import jakarta.inject.Inject;
import org.example.dao.common.SQLQueries;
import org.example.dao.model.Mision;
import org.example.dao.model.Personaje;
import org.example.dao.repositories.MisionRepository;
import org.example.dao.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBCMisionRepository  implements MisionRepository {
    private static final Logger logger = Logger.getLogger(JDBCPersonajeRepository.class.getName());
    private final DBConnection dbConnection;

    @Inject
    public JDBCMisionRepository(DBConnection dbConnection ){
        this.dbConnection = dbConnection;
    }

    @Override
    public Optional<Mision> findByName(String name) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.SELECT_MISION_BY_NOMBRE)){
            logger.log(Level.INFO, SQLQueries.SELECT_MISION_BY_NOMBRE);
            preparedStatement.setString(1,name);

            try (ResultSet rs = preparedStatement.executeQuery()){
                if (rs.next()){

                    Mision mision = Mision.builder()

                            .misionId(rs.getLong("Mision_ID"))
                            .nombre(rs.getString("Nombre"))
                            .descripcion(rs.getString("Descripcion"))
                            .tokens(rs.getInt("Tokens"))
                            .build();
                    return Optional.of(mision);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al buscar mision por nombre: " + name, e);
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}
