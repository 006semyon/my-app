package ru.kata.spring.boot_security.demo.service;

import com.github.javafaker.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.*;
import ru.kata.spring.boot_security.demo.repository.RoleRepo;
import ru.kata.spring.boot_security.demo.repository.UserRepo;
import ru.kata.spring.boot_security.demo.security.CustomUserDetails;

import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final RoleRepo roleRepo;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(RoleRepo roleRepo, UserRepo userRepo, @Lazy PasswordEncoder passwordEncoder) {
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> person = userRepo.findByUsername(username);
        if (person.isPresent()) {
            return new CustomUserDetails(person.get());
        } else {
            throw new UsernameNotFoundException("Username not found");
        }
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username).orElse(null);
    }

    @Transactional
    @Override
    public List<Role> getRoles() {
        return roleRepo.findAll();
    }

    @Transactional
    @Override
    public void add(User user, List<Integer> roles) {
        if (userRepo.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Пользователь с именем '" + user.getUsername() + "' уже существует");
        }
        HashSet<Role> rolesOfUser = new HashSet<>();
        for (Integer roleId : roles) {
            rolesOfUser.add(roleRepo.findById(roleId).orElseThrow(() -> new RuntimeException("Role 'ROLE_USER' not found")));
        }
        user.setRoles(rolesOfUser);
        encodePassword(user);

        List<String> carModels = Arrays.asList(
                // Иностранные модели
                "Toyota Camry", "Ford Mustang", "BMW X5", "Audi A4", "Mercedes-Benz C-Class",
                "Tesla Model 3", "Honda Accord", "Chevrolet Tahoe", "Hyundai Elantra", "Kia Sportage",
                "Volkswagen Passat", "Nissan Altima", "Jeep Grand Cherokee", "Mazda CX-5", "Subaru Outback",
                "Lexus RX", "Porsche Cayenne", "Jaguar XF", "Land Rover Discovery", "Volvo XC90",
                "Peugeot 3008", "Citroën C4", "Skoda Octavia", "Fiat Panda", "Renault Duster",

                // Российские модели
                "Lada Granta", "Lada Vesta", "Lada XRay", "Lada Niva Legend", "Lada Niva Travel",
                "GAZ Sobol", "UAZ Patriot", "UAZ Hunter", "Lada Kalina", "Lada Priora"
        );


        Faker fakerRu = new Faker(new Locale("ru"));
        // Локализация "en" для английских названий
        Faker fakerEn = new Faker(new Locale("en"));

        // Генерация налоговых платежей
        HashSet<TaxPayment> taxPayments = new HashSet<>();
        for (int i = 0; i < 2; i++) {
            String taxType = i == 0 ? "Налог на доход" : "Налог на имущество";
            double amount = fakerRu.number().randomDouble(2, 1000, 10000);
            String status = fakerRu.options().option("PAID", "PENDING"); // Случайный статус
            TaxPayment taxPayment = new TaxPayment(
                    user,
                    taxType,
                    amount,
                    fakerRu.date().future(60, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                    TaxStatus.valueOf(status)
            );
            taxPayments.add(taxPayment);
        }
        user.setTaxPayments(taxPayments);

        // Генерация данных об имуществе
        HashSet<Property> properties = new HashSet<>();
        Property apartment = new Property(
                user,
                "Квартира",
                fakerRu.address().fullAddress(), // Генерация русскоязычного адреса
                fakerRu.number().randomDouble(2, 1000000, 5000000)
        );
        Property car = new Property(
                user,
                "Автомобиль",
                carModels.get(fakerRu.number().numberBetween(0, carModels.size())), // Случайная модель из списка // Генерация английского названия автомобиля
                fakerRu.number().randomDouble(2, 500000, 3000000)
        );
        properties.add(apartment);
        properties.add(car);
        user.setProperties(properties);


        userRepo.save(user);
    }

    @Transactional
    @Override
    public void update(int id, User user, List<Integer> roles) {
        User u = getUserById(id);
        u.setFirstName(user.getFirstName());
        u.setLastName(user.getLastName());
        u.setAge(user.getAge());
        u.setEmail(user.getEmail());
        if (user.getPassword() != null) {
            u.setPassword(user.getPassword());
        }
        encodePassword(u);

        if (roles != null) {
            HashSet<Role> rolesOfUser = new HashSet<>();
            for (Integer roleId : roles) {
                rolesOfUser.add(roleRepo.findById(roleId).orElseThrow(() -> new RuntimeException("Role not found")));
            }
            u.setRoles(rolesOfUser);
        }
    }

    @Transactional
    @Override
    public void delete(int id) {
        userRepo.deleteById(id);
    }

    @Transactional
    @Override
    public List<User> getAll() {
        return userRepo.findAll();
    }

    @Transactional
    @Override
    public User getUserById(int id) {
        return userRepo.findById(id).orElse(null);
    }

    private void encodePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}
