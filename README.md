# HermesStore

This is an ecommerce webapp project developed using Java EE, SQL, JavaScript, and Maven. (2023)

## Features

- User registration and login : Two types of users can register and login to the system: customers and providers.
- Product management : Providers can add, update, and delete products.
- Shopping cart : Customers can add products to their shopping cart and place orders.
- Order management : Providers can view orders placed by customers and update the status of the orders.
- Search : Customers can search for products by name or category.
- Pagination : Products are displayed in pages with a fixed number of products per page.
- Product details : Customers can view the details of a product, including its name, description, price, and category.
- Profile management : Users can update their profile information, including their name, email, and password.
- Order history : Customers can view their order history, including the products they ordered, the total price, and the status of the orders.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Java 11
- Maven
- SQL database

### Installing

1. Clone the repository
```bash
git clone https://github.com/Daryl-03/ecommerce_jee.git
```
2. Navigate to the project directory
```bash
cd ecommerce_jee
```
3. Change the database string in the `src/main/java/com/hermesstore/projetexamen2021/jdbc/DBConnection.java` file
```java
connection = DriverManager.getConnection("jdbc:mysql://<HOST>:<PORT>/<DATABASE>, <USERNAME>, <PASSWORD>);
```
4. Source the `/lib/bdprojetexamen2023.sql` file in your SQL database
5. Paste the contents of `/lib/store` in the `/src/main/webapp/store` directory for the images (initial content)
6. Configure Tomcat in your IDE
7. Add the project to Tomcat
8. Install the dependencies
```bash
mvn install
```
9. Change the application context to "/HermesStore" in the "Run/Debug Configurations" of your IDE before running the project
10. Run the project

## Built With

- [Java](https://www.oracle.com/java/) - The main programming language
- [SQL](https://www.mysql.com/) - Used for database
- [JavaScript](https://developer.mozilla.org/en-US/docs/Web/JavaScript) - Used for frontend
- [JSP](https://www.oracle.com/java/technologies/jspt.html) - Used for frontend
- [JSTL](https://www.oracle.com/java/technologies/jspt.html) - Used for frontend
- [JDBC](https://docs.oracle.com/javase/tutorial/jdbc/) - Used for database connection
- [Servlet](https://docs.oracle.com/javaee/6/tutorial/doc/bnafd.html) - Used for backend
- [JQuery](https://jquery.com/) - Used for frontend
- [Ajax](https://developer.mozilla.org/en-US/docs/Web/Guide/AJAX) - Used for frontend
- [Bootstrap](https://getbootstrap.com/) - Used for frontend
- [Tomcat](http://tomcat.apache.org/) - The web server
- [Maven](https://maven.apache.org/) - Dependency Management

The project follows the MVC design pattern with DAO pattern for database access. The project structure is as follows:
- Model: Java classes in the `src/main/java/com/hermesstore/projetexamen2021/model` package
- View: JSP files in the `src/main/webapp` directory
- Controller: Servlet classes in the `src/main/java/com/hermesstore/projetexamen2021/controller` package
- DAO: Java classes in the `src/main/java/com/hermesstore/projetexamen2021/model/datasource` package
- JDBC: Java classes in the `src/main/java/com/hermesstore/projetexamen2021/jdbc` package

## Authors

- **Daryl-03** - *Initial work* - [Daryl-03](https://github.com/Daryl-03)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details