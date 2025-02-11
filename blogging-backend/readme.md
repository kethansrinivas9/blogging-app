# Important information
## Spring Security Configuration steps:
### Email/Password Authentication
    1. create Websecurityconfig class and add @Configuration and @EnableWebSecurity annotations
    2. Add SecurityFilterChain for adding authentication filter
    3. Add BCryptPasswordEncoder as a bean for encoding the password when registering user
    4. Add DaoAuthenticationProvider as the AuthenticationProvider Bean to be used for authentication later
    5. Add AuthenticationManager to be used in login method to authenticate user
### JWT Authentication
    1. create JWTAuthenticationFilter that extends OncePerRequestFilter
    2. Need to override the doFilterInternal method that comes from OncePerRequestFilter 
       with JWT logic
    3. Create JWTService to extract Username from JWT
    4. Create a secret token 128/256/other bits from online services for creating JWT token


 