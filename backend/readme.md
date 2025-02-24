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
    4. Create a secret token 128/256/other bits from online services for creating JWT token and validating the user details for subsequent requests
    5. The secret is base64 encoded, to create a new JWT token we need to decode the secret and generate the token using hmacShaKeyFor method
    6. Once the JWT is created with login request, the subsequent requests should use this JWT as Authorization header with the prefix "Bearer " 
    7. For the subsequent request when the JWT is supplied, the email/username is extracted from the JWT and user is loaded from the DB and the 
       request is authenticated usign the UsernamePasswordAuthenticationToken and set to the SecurityContextHolder without having to go through the normal username/password login process
    8. use addFilterBefore with jwtAuthenticationFilter before the UsernamePasswordAuthenticationFilter, this will allow us
       to check the JWT everytime before doing username/password login

### Spring Security authentication flow
    Spring Security -> Filters (UsernamePasswordAuthenticationFilter) -> Authentication Manager(Provider Manager) ->
    -> Providers (DAO Authentication Provider) -> UserDetailsService (JDBC UserDetails Manager)


 