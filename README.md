

## TechBox ![2641167](https://user-images.githubusercontent.com/55769297/215401056-a60b6a99-f396-4d0c-817c-4f96f703419f.png)


Spring MVC project with JWT based Spring Security. 
## Description
Project TechBox is a cutting-edge e-commerce platform that harnesses the power of spring security and the efficiency of JWT (JSON Web Token) technology. The platform features two key entities - User and Product, that are connected through a many-to-many relationship. Users can easily create an account, log in and browse a vast product catalog, where they can add their desired items to their wishlist with ease. The platform also allows for searching products by vendor name, adding an extra level of convenience and accessibility. The security of the platform is maintained through role segregation, with only administrators having the ability to create, update or patch products. To ensure a secure experience, the platform implements a JWT system that blacklists tokens upon logout, generates new tokens with each subsequent login, and includes the feature of refresh tokens. The relationship between users and tokens is monitored and recorded through the JWT entity, which has a one-to-one relationship with the User entity, guaranteeing outsatnding security and usability experience.
## API
- Show all products
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
