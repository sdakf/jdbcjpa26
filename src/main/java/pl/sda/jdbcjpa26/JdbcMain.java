package pl.sda.jdbcjpa26;

import java.math.BigDecimal;
import java.sql.*;

public class JdbcMain {
    public static void main(String[] args) {
        bigDecimalExample();

        createStatement(1000);
        createStatement(2000);
        createPreparedStatement(1000);
    }

    private static void bigDecimalExample() {
        double a = 0.01;
        double b = 0.03;
        System.out.println(a - b);

        BigDecimal aa = new BigDecimal(0.01);
        BigDecimal bb = new BigDecimal(0.03);
        System.out.println(aa.subtract(bb));

        BigDecimal aaa = BigDecimal.valueOf(0.01);
        BigDecimal bbb = BigDecimal.valueOf(0.03);
        System.out.println(aaa.subtract(bbb));
    }

    private static void createPreparedStatement(int minSalary) {
        Connection connection = createConnection();
        String query = "select ename, job, mgr, sal " +
                "from sdajdbc.employee " +
                "where sal > ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBigDecimal(1, BigDecimal.valueOf(minSalary));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                printResult(resultSet);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
                printResult(resultSet);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void printResult(ResultSet resultSet) {
        try {
            String ename = resultSet.getString("ename");
            String job = resultSet.getString("job");
            Integer mgr = resultSet.getInt("mgr");
            if (resultSet.wasNull()) {
                mgr = null;
            }
            int sal = resultSet.getInt("sal");
            System.out.println(ename + ", " + job + ", " + mgr + ", " + sal);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("BŁĄD");
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
