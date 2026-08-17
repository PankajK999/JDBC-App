// import java.sql.*;

// public class App {

//     public static void main(String[] args) throws Exception {

//         Connection con = DriverManager.getConnection(
//                 "jdbc:mysql://localhost:3306/college",
//                 "root",
//                 "Beast"
//         );

//         System.out.println(con);
//         System.out.println("Connection created");

//         con.close();
//     }
// }


// // cd "C:\Users\nites\OneDrive\Desktop\Java\3\New Project\src"

// // javac -cp ".;..\lib\*" App.java

// // java -cp ".;..\lib\*" App



import java.sql.*;

public class App {

    public static void main(String[] args) {

        // MySQL connection details
        String url = "jdbc:mysql://localhost:3306/college";
        String username = "root";
        String password = "Beast"; // Change if your MySQL password is different

        try {
            // Step 1: Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Step 2: Connect to MySQL
            Connection con = DriverManager.getConnection(
                    url,
                    username,
                    password
            );

            System.out.println("Connected to MySQL!");

            // Step 3: Create table
            Statement st = con.createStatement();

            String createTable = "CREATE TABLE IF NOT EXISTS student (" +
                    "id INT PRIMARY KEY, " +
                    "name VARCHAR(30), " +
                    "branch VARCHAR(20)" +
                    ")";

            st.executeUpdate(createTable);

            // Step 4: Insert student
            String insertSQL = "INSERT INTO student (id, name, branch) VALUES (?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(insertSQL);

            ps.setInt(1, 101);
            ps.setString(2, "Rahul");
            ps.setString(3, "CSE");

            try {
                ps.executeUpdate();
                System.out.println("Record Inserted!");
            } catch (SQLIntegrityConstraintViolationException e) {
                System.out.println("ID 101 already exists. Record not inserted.");
            }

            // Step 5: Select all students
            String selectSQL = "SELECT * FROM student";

            ResultSet rs = st.executeQuery(selectSQL);

            System.out.println("\nStudent Records:");
            System.out.println("-------------------------");

            // Step 6: Read ResultSet
            while (rs.next()) {

                int id = rs.getInt("id");
                String name = rs.getString("name");
                String branch = rs.getString("branch");

                System.out.println(id + " " + name + " " + branch);
            }

            // Step 7: Close resources
            rs.close();
            ps.close();
            st.close();
            con.close();

            System.out.println("\nConnection Closed.");

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found!");
            e.printStackTrace();

        } catch (SQLException e) {
            System.out.println("Database Error!");
            e.printStackTrace();
        }
    }
}