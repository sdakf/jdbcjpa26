package pl.sda.jdbcjpa26;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcMain {
    public static void main(String[] args) {
        Connection connection = createConnection();
    }

    static Connection createConnection() {

        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306?serverTimezone=UTC",
                    "root",
                    "pass"
            );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException(throwables.getMessage());
        }

    }

}
