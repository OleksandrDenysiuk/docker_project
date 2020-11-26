import java.util.Scanner;
import java.sql.*;

public class Lab4 {

   static final String jdbcDriver = "com.mysql.jdbc.Driver";
   static final String dbAddress = "jdbc:mysql://10.0.10.3:3306/";
   static final String userPass = "?user=root&password=admin";
   static final String dbName = "database";
   static final String userName = "root";
   static final String password = "admin";

   static Connection con;

   public static void main(String[] args) {
      Scanner scan = new Scanner(System.in);
      boolean run = true;
      int operation;
      scan.nextLine();
      try {
         Class.forName(jdbcDriver);
         con = DriverManager.getConnection(dbAddress + dbName, userName, password);
         Statement  statement = con.createStatement();
         int myResult = statement.executeUpdate(
            "CREATE TABLE IF NOT EXISTS `" +dbName+ "`.`posts` ( `id` INT NOT NULL AUTO_INCREMENT , `title` VARCHAR(64) NOT NULL , `description` VARCHAR(64) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;"
         );
         statement.close();
      } catch (ClassNotFoundException | SQLException e) {
         e.printStackTrace();
      }
      while(run) {
         System.out.println("\nWhat do you want to do?");
         System.out.println("1. Add new post");
         System.out.println("2. Delete some post");
         System.out.println("3. Update some post");
         System.out.println("4. See all posts");
         System.out.println("5. Exit");
         System.out.print("Enter number of step: ");
         operation = Integer.parseInt(scan.nextLine());
         switch(operation) {
            case 1: {
               System.out.println("Create new post");
               System.out.print("Title: ");
               String title = scan.nextLine();
               System.out.print("Description: ");
               String description = scan.nextLine();

               try {
                  PreparedStatement prpStmt = con.prepareStatement("INSERT INTO posts ( title, description ) VALUES (?, ?)");
                  prpStmt.setString(1, title);
                  prpStmt.setString(2, description);
                  prpStmt.execute();
                  System.out.println("Post has been added");
               } catch (SQLException e) {
                  e.printStackTrace();
               }
               break;
            }
            case 2: {
               System.out.println("Enter ID of post: ");
               int id = Integer.parseInt(scan.nextLine());
               try {
                  PreparedStatement prpStmt = con.prepareStatement("DELETE FROM posts WHERE ID = ?");
                  prpStmt.setInt(1, id);
                  prpStmt.execute();
                  System.out.println("Post has been deleted");
               } catch (SQLException e) {
                  e.printStackTrace();
               }
               break;
            }
            case 3: {
               System.out.println("Enter ID of post: ");
               int id = Integer.parseInt(scan.nextLine());
               System.out.println("Title: ");
               String title = scan.nextLine();
               if(!title.isEmpty()){
                  try {
                     PreparedStatement prpStmt = con.prepareStatement("UPDATE posts SET title = ? WHERE ID = ?");
                     prpStmt.setInt(1, id);
                     prpStmt.setString(2, title);
                     prpStmt.execute();
                     System.out.println("Post title has been updated");
                  } catch (SQLException e) {
                     e.printStackTrace();
                  }
               }
               System.out.println("Description: ");
               String description = scan.nextLine();
               if(!title.isEmpty()){
                  try {
                     PreparedStatement prpStmt = con.prepareStatement("UPDATE posts SET description = ? WHERE ID = ?");
                     prpStmt.setInt(1, id);
                     prpStmt.setString(2, description);
                     prpStmt.execute();
                     System.out.println("Post description has been changed");
                  } catch (SQLException e) {
                     e.printStackTrace();
                  }
               }

               break;
            }
            case 4: {
               try {
                  System.out.println("Post list");
                  Statement  statement = con.createStatement();
                  ResultSet rs =  statement.executeQuery("SELECT * FROM posts");
                  while(rs.next()) {
                     int id = rs.getInt("id");
                     String title = rs.getString("title");
                     String description = rs.getString("description");
                     System.out.println(id + " | " + title + " | " + description);
                  }
                  statement.close();
               } catch (SQLException e) {
                  e.printStackTrace();
               }
               break;
            }
            case 5: {
               System.out.println("Exit... Good buy!");
               run = false;
               break;
            }
         }
      }
   }
}
