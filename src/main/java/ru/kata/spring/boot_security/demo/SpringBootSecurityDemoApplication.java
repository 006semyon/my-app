package ru.kata.spring.boot_security.demo;

import com.github.javafaker.Faker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.io.FileWriter;
import java.io.IOException;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class SpringBootSecurityDemoApplication {

    public static void main(String[] args) throws IOException {
//		Faker faker = new Faker(new Locale("ru"));
//		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		FileWriter writer = new FileWriter("src/main/resources/db/changelog/sql/initial_data.sql");
//
//		// Set to track unique tax types for users
//		Set<String> uniqueTaxTypes = new HashSet<>();
//
//		for (int i = 0; i < 60; i++) {
//			// Generate user data
//			String firstName = faker.name().firstName();
//			String lastName = faker.name().lastName();
//			String username = faker.name().username();
//			String email = faker.internet().emailAddress();
//			int age = faker.number().numberBetween(18, 65);
//			String password = passwordEncoder.encode("password" + i);
//
//			// SQL to insert user
//			writer.write(String.format(
//					"INSERT INTO users (username, first_name, last_name, email, age, password) VALUES ('%s', '%s', '%s', '%s', %d, '%s');\n",
//					username, firstName, lastName, email, age, password
//			));
//
//			// Assign role to the user (user_id starts at i+1)
//			writer.write(String.format(
//					"INSERT INTO user_roles (user_id, role_id) VALUES (%d, 1);\n", i + 1
//			));
//
//			// Generate tax payment data
//			uniqueTaxTypes.clear(); // Clear the set to ensure unique tax types per user
//			for (int j = 0; j < 2; j++) {
//				String taxType;
//				do {
//					taxType = faker.options().option("Налог на доход", "Налог на имущество");
//				} while (!uniqueTaxTypes.add(taxType)); // Ensure uniqueness
//
//				double amount = faker.number().randomDouble(2, 1000, 10000);
//				String dueDate = faker.date().future(60, TimeUnit.DAYS).toInstant()
//						.atZone(ZoneId.systemDefault()).toLocalDate().toString();
//				String status = (j % 2 == 0) ? "PAID" : "PENDING"; // Alternate status for variety
//
//				// SQL to insert tax payment
//				writer.write(String.format(
//						"INSERT INTO tax_payments (user_id, tax_type, amount, due_date, status) VALUES (%d, '%s', %.2f, '%s', '%s');\n",
//						i + 1, taxType, amount, dueDate, status
//				));
//			}
//
//			// Generate property data
//			String propertyType = faker.options().option("Квартира", "Автомобиль");
//			String propertyDescription = propertyType.equals("Квартира")
//					? faker.address().fullAddress()
//					: faker.rockBand().name(); // Use car model for "Автомобиль"
//			double propertyValue = faker.number().randomDouble(2, 500000, 5000000);
//
//			// SQL to insert property
//			writer.write(String.format(
//					"INSERT INTO properties (user_id, type, description, value) VALUES (%d, '%s', '%s', %.2f);\n",
//					i + 1, propertyType, propertyDescription, propertyValue
//			));
//		}
//
//		writer.close();
//		System.out.println("SQL file generated successfully!");

        SpringApplication.run(SpringBootSecurityDemoApplication.class, args);
/*		Scanner scanner = new Scanner(System.in);
		String text = scanner.nextLine();

		RestTemplate restTemplate = new RestTemplate();
		String url = "https://resource-manager.api.cloud.yandex.net/resource-manager/v1/clouds";

		Map<String, String> data = new HashMap<>();
		data.put("folderId", "b1gsp5chmg1qsq3hjia5");
		data.put("texts", "[ " + text + " ]");
		data.put("targetLanguageCode", "en");
		data.put("name", "ramazan-mx");
		data.put("organization_id", "bpf7ngl77tgq4ogvcqhf");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Bearer " + "t1.9euelZqbzsjGz56Yl4-MncedjY-Kl-3rnpWajZLOz8mVm5uOzYzIzM-OjM3l8_dLFj1G-e82OjZa_N3z9wtFOkb57zY6Nlr8zef1656Vms2SjJaQnpSZm52Xypidj5WV7_zF656Vms2SjJaQnpSZm52Xypidj5WV.BzVxZjp-XuCss7CK-0CmgfCIxDt0anID7AhkU7gLo80u16_FR0k-q9BrZE6KJgkSQSwwwKBtb3dtOR0ON1x2Aw");

		HttpEntity<Map<String,String>> request = new HttpEntity<>(data, headers);

		String response = restTemplate.postForObject(url, request, String.class);
		System.out.println(response);*/
    }
}
