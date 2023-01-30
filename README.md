# TechBox
Spring MVC project with JWT based Spring Security. 
<h2> Description 

</h2>
Project TechBox is a cutting-edge e-commerce platform that harnesses the power of spring security and the efficiency of JWT (JSON Web Token) technology. The platform features two key entities - User and Product, that are connected through a many-to-many relationship. Users can easily create an account, log in and browse a vast product catalog, where they can add their desired items to their wishlist with ease. The platform also allows for searching products by vendor name, adding an extra level of convenience and accessibility. The security of the platform is maintained through role segregation, with only administrators having the ability to create, update or patch products. To ensure a secure experience, the platform implements a JWT system that blacklists tokens upon logout, generates new tokens with each subsequent login, and includes the feature of refresh tokens. The relationship between users and tokens is monitored and recorded through the JWT entity, which has a one-to-one relationship with the User entity, guaranteeing outsatnding security and usability experience.
