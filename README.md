

## TechBox ![2867321](https://user-images.githubusercontent.com/55769297/215401960-0c0d3fdb-fd25-48d1-8d27-0d1dd7cfe894.png)



Spring MVC project with JWT based Spring Security. 
## Description
Project TechBox is a cutting-edge e-commerce platform that harnesses the power of spring security and the efficiency of JWT (JSON Web Token) technology. The platform features two key entities - User and Product, that are connected through a many-to-many relationship. Users can easily create an account, log in and browse a vast product catalog, where they can add their desired items to their wishlist with ease. The platform also allows for searching products by vendor name, adding an extra level of convenience and accessibility. The security of the platform is maintained through role segregation, with only administrators having the ability to create, update or patch products. To ensure a secure experience, the platform implements a JWT system that blacklists tokens upon logout, generates new tokens with each subsequent login, and includes the feature of refresh tokens. The relationship between users and tokens is monitored and recorded through the JWT entity, which has a one-to-one relationship with the User entity, guaranteeing outsatnding security and usability experience.
## API
- Show all products![product list](https://user-images.githubusercontent.com/55769297/215486554-c8ed5fda-dc60-4399-bb4e-88f6f2137a43.png)
- Show Single product ![product get by id](https://user-images.githubusercontent.com/55769297/215486858-f9a16a14-9781-4ed8-99ea-3e5e89c4943f.png)
- Product search by vendor name![product by vendor](https://user-images.githubusercontent.com/55769297/215487159-52ecc342-469a-478e-8c8a-88937e7311f9.png)
- Create a new product![product add](https://user-images.githubusercontent.com/55769297/215487207-b7d6494b-bd1d-4b94-8fb3-5d1242948972.png)
- Update a product ![Product update done](https://user-images.githubusercontent.com/55769297/215487282-58389f8a-0a4e-4d38-aea8-8ef64596989f.png)
- Product Validation
![product update validation](https://user-images.githubusercontent.com/55769297/215488664-3debcbed-b6ee-473e-960c-1d88f6e43c8d.png)

- Patch a product![product patch update](https://user-images.githubusercontent.com/55769297/215487312-6a51618e-0d41-4da0-acb9-5a4e32ff36fd.png)
- Delete a product![delete product by id](https://user-images.githubusercontent.com/55769297/215487372-ae98b09f-dccc-4506-b5a3-e78e66f94d80.png)
- Register as a new user![user registration](https://user-images.githubusercontent.com/55769297/215487456-221df31c-4b29-4345-97c7-4f77c563b0e5.png)
- Login as user![login user](https://user-images.githubusercontent.com/55769297/215487532-d7a12417-7c7d-4228-8408-759b7094b2ea.png)
- Add is user wishlist![users initial wishlist](https://user-images.githubusercontent.com/55769297/215487615-7581980f-73a7-4525-9880-0cc5559a0d66.png)
- ![adding product to uers wishlist](https://user-images.githubusercontent.com/55769297/215487724-c1f8f9b3-2022-49cd-8fc5-f5d7627a7141.png)
![users added wishlist](https://user-images.githubusercontent.com/55769297/215487764-19aaa449-2390-4591-9a4b-48bba22151cf.png)

- Remove from user wishlist
- ![removing product from users wishlist](https://user-images.githubusercontent.com/55769297/215487789-c5fa257d-08bf-4b25-ad2f-0f9fb4d89cd6.png)

- Show user wishlist
- ![users wishlist after removing few ![username logout 200](https://user-images.githubusercontent.com/55769297/215488181-c79afe49-a5cf-4f0a-96a8-91442432bf37.png)
products](https://user-images.githubusercontent.com/55769297/215487840-e210a19a-1b83-449e-928c-df7fc49efe76.png)
- Logout![Uploading username logout 200.png…]()![user token blacklisted](https://user-images.githubusercontent.com/55769297/215488227-e8ffc39a-2447-4a44-99b6-1d1890cd3e19.png)
- All users list
![all users list](https://user-images.githubusercontent.com/55769297/215488409-2037aa36-a025-4ff6-9b3b-d958ddc05388.png)
- Refresh token
![refresh token](https://user-images.githubusercontent.com/55769297/215488460-76a9d70d-0e62-4475-b392-f7138c4ffb99.png)

