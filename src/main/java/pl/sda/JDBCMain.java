package pl.sda;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Optional;

public class JDBCMain {
    public static void main(String[] args) {
        //createStatement("2000");
        System.out.println("-------");
        createStatement("5000 or 1=1");//trzeba zabezpieczyc aby nie mozna bylo pisac dodakowych zapytan
    }

    private static void createStatement(String salaryFilter) {
        try {
            Connection connection = createConnection()
                    .orElseThrow(() -> new RuntimeException("Nie udało się utworzyć połączenia"));
            String query = "select ename, job, sal from sdajdbc.employee where sal>" + salaryFilter;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String ename = resultSet.getString("ename");
                String job = resultSet.getString("job");
                BigDecimal sal = resultSet.getBigDecimal("sal");
                System.out.println(ename + " " + job + " " + sal);
            }
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
    }

    private static Optional<Connection> createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306?serverTimezone=UTC",
                    "root",
                    "#$%ertDFG");
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }

        return Optional.ofNullable(connection);
    }
}
