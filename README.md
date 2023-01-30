

## TechBox ![2867321](https://user-images.githubusercontent.com/55769297/215401960-0c0d3fdb-fd25-48d1-8d27-0d1dd7cfe894.png)



Spring MVC project with JWT based Spring Security. 
## Description
Project TechBox is a cutting-edge e-commerce platform that harnesses the power of spring security and the efficiency of JWT (JSON Web Token) technology. The platform features two key entities - User and Product, that are connected through a many-to-many relationship. Users can easily create an account, log in and browse a vast product catalog, where they can add their desired items to their wishlist with ease. The platform also allows for searching products by vendor name, adding an extra level of convenience and accessibility. The security of the platform is maintained through role segregation, with only administrators having the ability to create, update or patch products. To ensure a secure experience, the platform implements a JWT system that blacklists tokens upon logout, generates new tokens with each subsequent login, and includes the feature of refresh tokens. The relationship between users and tokens is monitored and recorded through the JWT entity, which has a one-to-one relationship with the User entity, guaranteeing outsatnding security and usability experience.
## API
- Show all products![product list](https://user-images.githubusercontent.com/55769297/215486554-c8ed5fda-dc60-4399-bb4e-88f6f2137a43.png)


- Show Single product
- Product search by vendor name
- Create a new product
- Update a producta
- Patch a product
- Delete a product
- Register as a new user
- Login as user
- Add is user wishlist
- Remove from user wishlist
- Show user wishlist
- Logout
