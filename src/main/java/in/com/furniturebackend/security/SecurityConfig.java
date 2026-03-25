package in.com.furniturebackend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;




@Configuration
@EnableMethodSecurity
public class SecurityConfig {
	

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    	  http
          .csrf(csrf -> csrf.disable())
          
          // ✅ CORS enable
          .cors(cors -> {})

          // ✅ JWT ke liye Stateless session
          .sessionManagement(session ->
                  session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
          
          .authorizeHttpRequests(auth -> auth

              // 🔹 Swagger Public Access
              .requestMatchers(
                      "/v3/api-docs/**",
                      "/swagger-ui/**",
                      "/swagger-ui.html",
                      "/uploads/**"
              ).permitAll()
              
              .requestMatchers("/api/auth/**").permitAll()
              
           // Category Public GET
              .requestMatchers(HttpMethod.GET, "/api/categories/**").permitAll()

              // Category ADMIN Only
              .requestMatchers(HttpMethod.POST, "/api/categories/**").hasRole("ADMIN")
              .requestMatchers(HttpMethod.PUT, "/api/categories/**").hasRole("ADMIN")
              .requestMatchers(HttpMethod.DELETE, "/api/categories/**").hasRole("ADMIN")
              

              // 🔹 Authentication Public
              .requestMatchers("/api/auth/**").permitAll()

              // 🔹 Product GET Public
              .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()

              // 🔹 Product Modify ADMIN Only
              .requestMatchers(HttpMethod.POST, "/api/products/**").hasRole("ADMIN")
              .requestMatchers(HttpMethod.PUT, "/api/products/**").hasRole("ADMIN")
              .requestMatchers(HttpMethod.DELETE, "/api/products/**").hasRole("ADMIN")

              // 🔹 Cart & Orders Only Logged Users
              .requestMatchers("/api/cart/**", "/api/orders/**")
              .hasAnyRole("USER", "ADMIN")

              // 🔹 Everything Else Authenticated
              .anyRequest().authenticated()
          )

          // 🔥 JWT Filter Connect
          .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

      return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(
          AuthenticationConfiguration config) throws Exception {
      return config.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
  }
}