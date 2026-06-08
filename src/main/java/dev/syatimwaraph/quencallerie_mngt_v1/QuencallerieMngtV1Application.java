package dev.syatimwaraph.quencallerie_mngt_v1;


import dev.syatimwaraph.quencallerie_mngt_v1.enums.Roles;
import dev.syatimwaraph.quencallerie_mngt_v1.model.UserOrg;
import dev.syatimwaraph.quencallerie_mngt_v1.repository.UserOrgRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class QuencallerieMngtV1Application {

    public static void main(String[] args) {
        SpringApplication.run(QuencallerieMngtV1Application.class, args);
    }

    /**
         * Creates a default ADMIN user when the application starts.
         * The user is created only if no user with the username "admin" exists.
     */
        @Bean
        CommandLineRunner createDefaultSaler(UserOrgRepository userRepository,
                                             PasswordEncoder passwordEncoder) {
            return args -> {

                String username = "saler";
                String rawPassword = "saler123";

                if (userRepository.findByUsername(username).isEmpty()) {

                    UserOrg admin = new UserOrg();
                    admin.setUsername(username);
                    admin.setPassword(passwordEncoder.encode(rawPassword));
                    admin.setRole(Roles.SALER);

                    userRepository.save(admin);

                    System.out.println("======================================");
                    System.out.println("Default SALER user created");
                    System.out.println("Username: ");
                    System.out.println("Password: ");
                    System.out.println("======================================");
                } else {
                    System.out.println("SALER user already exists.");
                }
            };
        }

        @Bean
        CommandLineRunner createDefaultAdmin(UserOrgRepository userRepository,
                                             PasswordEncoder passwordEncoder) {
            return args -> {

                String username = "admin";
                String rawPassword = "admin123";

                if (userRepository.findByUsername(username).isEmpty()) {

                    UserOrg admin = new UserOrg();
                    admin.setUsername(username);
                    admin.setPassword(passwordEncoder.encode(rawPassword));
                    admin.setRole(Roles.ADMIN);

                    userRepository.save(admin);

                    System.out.println("======================================");
                    System.out.println("Default ADMIN user created");
                    System.out.println("Username: ");
                    System.out.println("Password: ");
                    System.out.println("======================================");
                } else {
                    System.out.println("ADMIN user already exists.");
                }
            };
        }
    }




//    @SpringBootApplication
//    public class QuencallerieApplication {
//
//        public static void main(String[] args) {
//            SpringApplication.run(QuencallerieApplication.class, args);
//        }
//


