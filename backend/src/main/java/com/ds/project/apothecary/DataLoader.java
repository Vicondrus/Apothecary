package com.ds.project.apothecary;

import com.ds.project.apothecary.entities.Address;
import com.ds.project.apothecary.entities.Doctor;
import com.ds.project.apothecary.enums.Gender;
import com.ds.project.apothecary.enums.UserType;
import com.ds.project.apothecary.repositories.DoctorRepository;
import com.ds.project.apothecary.repositories.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * The type Data loader.
 */
@Component
public class DataLoader
        implements ApplicationRunner {

    /**
     * The Doctor repository.
     */
    private final DoctorRepository
            doctorRepository;

    /**
     * The User repository.
     */
    private final UserRepository
            userRepository;

    /**
     * The Password encoder.
     */
    private final PasswordEncoder
            passwordEncoder;

    /**
     * Instantiates a new Data loader.
     *
     * @param pDoctorRepository the doctor repository
     * @param pUserRepository   the user repository
     * @param pPasswordEncoder  the password encoder
     */
    public DataLoader(final DoctorRepository pDoctorRepository,
                      final UserRepository pUserRepository,
                      final PasswordEncoder pPasswordEncoder) {
        this.doctorRepository =
                pDoctorRepository;
        this.userRepository =
                pUserRepository;
        this.passwordEncoder =
                pPasswordEncoder;
    }

    /**
     * Run.
     *
     * @param args the args
     */
    public void run(final ApplicationArguments args) {
        if (!userRepository.existsByUsername("admin")) {
            doctorRepository.save(Doctor.builder().username("admin")
                    .birthDate(new Date())
                    .firstName("Admin")
                    .lastName("Admin")
                    .gender(Gender.OTHER)
                    .address(Address.builder().street("Adminului Street")
                            .number(1)
                            .city("Bucharest")
                            .country("Romania").build())
                    .password(passwordEncoder.encode("admin")).
                            userType(UserType.DOCTOR).build());
        }
    }
}
