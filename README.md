#  E-Commerce Backend API - Spring Boot
This is a fully functional backend API for an E-Commerce platform, built using Spring Boot. The application supports role-based authentication (Customer, Seller), product management, cart operations, order placement, and secure login/logout with JWT tokens.


## Entity Relationship Diagram

```mermaid
erDiagram
    Customer {
        Integer customerId PK
        String firstName
        String lastName
        String mobileNo UK
        String emailId UK
        String password
        LocalDateTime createdOn
        String cardNumber
        String cardValidity
        String cardCVV
    }
    
    CustomerAddressMapping {
        Integer customer_id FK
        Integer address_id FK
        String address_type
    }
    
    Cart {
        Integer cartId PK
        double cartTotal
        Integer customerId FK
    }
    
    CartItem {
        Integer cartItemId PK
        Integer cartItemQuantity
        Integer cartId FK
        Integer productId FK
    }
    
    Order {
        Integer orderId PK
        LocalDate date
        OrderStatusValues orderStatus
        Double total
        String cardNumber
        Integer customerId FK
        Integer addressId FK
    }
    
    OrderItems {
        int id PK
        int quantity
        Integer orderId FK
        Integer productId FK
    }
    
    Product {
        Integer productId PK
        String productName
        double price
        String description
        String manufacturer
        Integer quantity
        CategoryEnum category
        ProductStatus status
        Integer sellerId FK
    }
    
    Seller {
        Integer sellerId PK
        String firstName
        String lastName
        String password
        String mobile UK
        String emailId UK
    }
    
    Address {
        int addressId PK
        String streetName
        String buildingName
        String locality
        String city
        String state
        String pinCode
    }
    
    Customer ||--|| Cart : "has"
    Customer ||--o{ Order : "places"
    Customer ||--o{ CustomerAddressMapping : "has_addresses"
    CustomerAddressMapping }o--|| Address : "maps_to"
    Cart ||--o{ CartItem : "contains"
    CartItem }o--|| Product : "references"
    Order ||--o{ OrderItems : "contains"
    OrderItems }o--|| Product : "references"
    Order }o--|| Address : "delivered_to"
    Seller ||--o{ Product : "sells"
```


### Key Relationships

- **Customer** has one **Cart** (One-to-One)
- **Customer** can place multiple **Orders** (One-to-Many)
- **Customer** has multiple **Addresses** with types via mapping table (Many-to-Many)
- **Cart** contains multiple **CartItems** (One-to-Many)
- **Order** contains multiple **OrderItems** (One-to-Many)
- **Product** can be in multiple **CartItems** and **OrderItems** (Many-to-One)
- **Seller** can sell multiple **Products** (One-to-Many)
- **Address** can be used for multiple **Orders** (One-to-Many)
- **Customer** has embedded **CreditCard** information (Composition)


### Notes

- `PK` = Primary Key
- `FK` = Foreign Key
- `UK` = Unique Key
- CreditCard fields are embedded in Customer table (not shown as separate entity)
- Enums: `OrderStatusValues`, `CategoryEnum`, `ProductStatus`

##  Tech Stack

- **Java 21**
- **Spring Boot**
- **Spring Security**
- **JWT (JSON Web Tokens)**
- **Spring Data JPA**
- **MySQL**
- **Hibernate**
- **Maven**


##  Features

- **User Authentication & Authorization**
  - Role-based login (Customer, Seller)
  - JWT-based secure login and logout
- **Customer Features**
  - Add to cart / remove from cart
  - Place and view orders
- **Seller Features**
  - Add, update, and delete products
  - View own listed products
- **General**
  - Password encryption using BCrypt
  - Exception handling with custom error responses
  - Layered architecture (Controller-Service-Repository)
 

## Running the project locally
- Clone the repository
- Update the application.properties file with your database username and password

  ```spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
  spring.datasource.username=your_mysql_username
  spring.datasource.password=your_mysql_password```

- Run the project from command line using: .\mvnw spring-boot:run


## Acknowledgments

- Inspired by common e-commerce platforms and Spring Boot architecture
- Thanks to open-source contributors and Spring Boot community for resources and guidance



