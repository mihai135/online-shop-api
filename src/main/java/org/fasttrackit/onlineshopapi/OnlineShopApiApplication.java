package org.fasttrackit.onlineshopapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(value = "org.fasttrackit.onlineshopapi")
public class OnlineShopApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineShopApiApplication.class, args);
	}

}
