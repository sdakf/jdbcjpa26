package pl.sda.jdbcjpa26;

import java.sql.*;

public class JdbcMain {
    public static void main(String[] args) {
        createStatement(1000);
        createStatement(2000);

    }

    private static void createStatement(int minSalary) {
        Connection connection = createConnection();
        String query = "select ename, job, mgr, sal " +
                "from sdajdbc.employee " +
                "where sal > " + minSalary;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String ename = resultSet.getString("ename");
                String job = resultSet.getString("job");
                int mgr = resultSet.getInt("mgr");
                int sal = resultSet.getInt("sal");
                System.out.println(ename +", "+ job +", " + mgr +", " + sal);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
