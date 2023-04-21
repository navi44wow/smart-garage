# **Smart Garage**

### Project Description

_Web application that enables the owners of an auto repair shop to manage their day-to-day job._

---
### Functionality

#### Public Part

_The public part must be accessible without authentication i.e., for anonymous users.
The public part provides the Login form (must) used to authenticate a user using username and password.
There must be an option to invoke the “forgotten password” option._

#### Private part
***<ins>Accessible only if the user is authenticated.</ins>***

#### Registered users will be able to:
Customers must be able to see the list of all their services.

- There must be a way to filter them by vehicle or date.
 
- There must be a way to filter them by vehicle and date.

- There must be a way to see a detailed report of a visit to the shop.

- The report must include the customer info, the vehicle info, every service that was done to the vehicle and the total price. The customer must be able to choose the currency of the report. (Use a third-party service to convert the price. See appendix).

- There could be a way to generate a PDF report for a given visit to the shop.

- There could be a way to generate one report that includes multiple visits.

- There could be an indicator of the status of the service (not started, in progress, ready for pickup…).

- There should be an option to change the password.

#### Administrative part
- There must be a section in the application, dedicated to the vehicles:

- Employees must be able to browse or update all vehicles linked to customers.

- Employees must be able to filter the vehicles by owner.

- Employees must be able to create a new vehicle for customers.

- If the model/make does not exist in the application’s database, it must be created.

- There must be a section in the application, dedicated to the services that the shop offers:

- Employees must be able to browse, create, delete, or update a service.

- Employees must be able to filter the services by name or price

- Employees must be able to filter the services by name and price.

- There must be a section in the application, dedicated to customers.

- Employees must be able to browse, delete, or update a customer’s profile.

- Employees must be able to filter the customers by name, email, phone number, vehicle (model or make) or visits in range (a visit between two dates).

- Employees must be able to filter the customers by name, email, phone number, vehicle (model or make) and visits in range (a visit between two dates).

- Employees must be able to sort the customers by name or visit date (latest or oldest on top).

- Employees must be able to register for a new visit to the shop by a customer who does not have a profile yet.

- The visit is registered and then the customer receives an email with automatically generated login information.

- Employees must be able to see a detailed report of a visit to the shop.

- The report must include the customer info, the vehicle info, every service that was done to the vehicle and the total price. The employee must be able to choose the currency of the generated report (Use a third-party service to convert the price. See Appendix).

- Employees should be able to send a “reset password” link to a customer via email.

- Employees could be able to generate a PDF report for a given service. The report is sent to the customer via email.
### Roadmap:
- [x] Create a README file.
- [x] Create classes: User, Vehicle, CarService, Visit.
- [x] CRUD operations for User. **<ins>Important**
- [ ] CRUD operations for Vehicle. **<ins>Important**
- [ ] CRUD operations for CarService. **<ins>Important**
- [ ] CRUD operations for Visit. **<ins>Important**
- [ ] Search options, by: vehicle or/and date for Customers **<ins>Important**
- [ ] Create a report view for Visit **<ins>Important**
- [ ] Filter options for CarServices section, filter by: name or/and price. for Employee dashboard **<ins>Important**
- [ ] Filter options for Customers section, filter by: name, email, phone, vehicle(model or make) or/and visits in range between two dates for Employee dashboard **<ins>Important**
- [ ] Implement `password reset` feature **<ins>Important**
- [ ] Implement `report view` feature to see the details of a visit. **<ins>Important**
- [ ] Implement `send report to the customer` feature. **<ins>Important**
- [ ] Implement `generate report in  pdf` feature. **<ins>Could be, not must**
- [ ] Create 'Home page' [HTML page]
- [ ] Create 'Customer dashboard' [HTML page]
- [ ] Create 'Employee dashboard' [HTML page]
- [ ] Create 'Register/Update new/existent customer' [HTML page]
- [ ] Create 'Register/Update new/existent vehicle' [HTML page]
- [ ] Create 'Register/Update new service'  [HTML page]
- [ ] Create 'Register/Update new visit' [HTML page]
- [ ] Implement Update Visit status feature [CRUD + Thymeleaf] **<ins> Very Important**
- [ ] Images of the database relations.
- [ ] Create and provide a link to the Swagger documentation. **<ins> Very Important**

---

### Swagger documentation

***<ins>Documentation is pending to be completed...***


---


### Database image

***<ins>Documentation is pending to be completed...***

---

### Use Cases

**Scenario 1 - A walk-in customer:**

*New customers walk in the shop. He has never visited before. He explains what the issue with his vehicle is and an employee creates a new personal vehicle (it includes the brand, model, year, vin, and license plate). When the vehicle is created the employee creates a new service order that includes the newly created vehicle, as well as all the service procedures that need to be performed (oil change, brake fluid change…). The customer receives, in the email that he has provided, an automatically generated invoice, containing the total sum that he must pay along with every service procedure that will be performed on his vehicle. He receives another email, that contains information about his automatically generated login information.*

**Scenario 2 - Forgotten password:**

*A customer has forgotten their password. They select the “Forgot password?” option and enter their email. The system checks if there is a registered user with that email. If there is one, he receives an email with a link to a page where he can enter a new password. The link should be accessible for a limited time only (say, an hour) and if accessed once, it should not be possible to access it again.*
