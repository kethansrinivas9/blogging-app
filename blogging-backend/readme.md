# Important information
## Spring Security Configuration steps:
    1. create Websecurityconfig class and add @Configuration and @EnableWebSecurity annotations
    2. Add SecurityFilterChain for adding authentication filter
    3. Add BCryptPasswordEncoder as a bean for encoding the password when registering user
    4. Add DaoAuthenticationProvider as the AuthenticationProvider Bean to be used for authentication later
    5. Add AuthenticationManager to be used in login method to authenticate user


 