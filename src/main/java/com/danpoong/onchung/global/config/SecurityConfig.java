package com.danpoong.onchung.global.config;

//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
//    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
//    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {
//        return http
//                .csrf(AbstractHttpConfigurer::disable)
//                .httpBasic(AbstractHttpConfigurer::disable)
//                .formLogin(AbstractHttpConfigurer::disable)
//                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //세션 사용 안 함
//
//                .exceptionHandling(exceptionHandling -> {
//                    exceptionHandling
//                            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
//                            .accessDeniedHandler(jwtAccessDeniedHandler);
//                })
//
//                .authorizeHttpRequests((authorizeRequests) ->
//                        authorizeRequests
////                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
////                                .requestMatchers("/**", "/api/**").permitAll()
////                                .anyRequest().authenticated()
//                                .anyRequest().permitAll()
//                )
//
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//
//                .build();
//    }
//}
