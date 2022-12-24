# StoreWala

![image](https://user-images.githubusercontent.com/111365348/208684870-007e52bb-9c3f-421b-818d-8bc95a3cc6f4.png)
![image](https://user-images.githubusercontent.com/111365348/208684972-9c707143-924a-4c13-a3e4-4bee528b2040.png)


StoreWala is an e-commerce website that allows users to browse and purchase products online. It is built using the following technologies:

- Spring Boot
- Thymeleaf
- Spring Security
- Hibernate Validator
- Spring JPA
- MySQL 8
- Bootstrap 5
- Sweet Alert
- FontAwesome
- FSLightbox
- TinyMCE
- JavaScript

The project includes the following modules:

- `login`: This module allows users to log in to the website using their email and password.
- `register`: This module allows users to create a new account on the website.
- `profile`: This module allows users to update their personal information and change their password.
- `admin-panel`: This module provides an interface for the website administrator to manage the product catalog, orders, and users. The administrator can view the products of all sellers, suspend or delete users, add categories, and manage unban requests.
- `seller-panel`: This module provides an interface for sellers to manage their products and orders. Sellers can view their products, add new products, and manage their orders.
- `user-panel`: This module provides an interface for normal users to view the status of their orders.
- `cart`: This module allows users to add items to their shopping cart and manage its contents.
- `checkout`: This module handles the checkout process, including calculating the total cost of the items in the cart and processing the payment. Currently, only COD (Cash on Delivery) is available.
- `unban-request`: This module allows users to request to unban their account if it has been suspended by the administrator.
- `review-section`: This module allows users to leave reviews for products on the product detail page.
- `product-detailed-page`: This module provides a detailed view of a product, including its description, price, and reviews.
- `order-module`: Dropped.

## Installation

To install and run this project, you'll need to have [Java](https://www.java.com) and [Maven](https://maven.apache.org) installed on your computer, as well as a MySQL 8 server. Then, follow these steps:

1. Clone or download this repository.
2. Create a new database on your MySQL server and update the `spring.datasource.url`, `spring.datasource.username`, and `spring.datasource.password` properties in the `src/main/resources/application.properties` file to match your MySQL server configuration.
3. Navigate to the project directory and run `mvn clean install` to build the project.
4. Run `java -jar target/storewala-0.0.1-SNAPSHOT.jar` to start the application.

Once the application is running, you can access the website at http://localhost:8080. Use the following credentials to log in:

- Email: `admin@storewala.com`
- Password: `admin`

## Live View:

go to: `https://ecommerce-sb.hariss.ga/`
You can register/login there.
