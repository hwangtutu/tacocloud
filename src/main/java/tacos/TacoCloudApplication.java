package tacos;

import tacos.data.IngredientRepository;
import tacos.data.OrderRepository;
import tacos.data.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 메인 애플리케이션 클래스.
 * - JPA / Hibernate 자동설정을 전부 제외(DataSourceAutoConfiguration, HibernateJpaAutoConfiguration)
 * - Spring Data MongoDB 레포지토리를 활성화(@EnableMongoRepositories)
 * - CommandLineRunner(dataLoader)를 인스턴스 메서드로 선언
 * - 기본 화면 매핑(view controllers) 설정
 */
@SpringBootApplication(
        exclude = {
                DataSourceAutoConfiguration.class,
                HibernateJpaAutoConfiguration.class
        }
)
@EnableMongoRepositories("tacos.data")
public class TacoCloudApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(TacoCloudApplication.class, args);
    }

    /**
     * 초기 데이터 로딩용 CommandLineRunner 빈.
     * 인스턴스 메서드로 선언되어야 "Illegal factory instance" 오류가 나지 않는다.
     *
     * @param ingredientRepo Ingredient 컬렉션에 초기 데이터를 삽입하는 레포지토리
     */
    @Bean
    public CommandLineRunner dataLoader(IngredientRepository ingredientRepo) {
        return args -> {
            // 기존에 있던 데이터를 삭제하고,
            // 예시 Ingredient 문서를 MongoDB에 저장
            ingredientRepo.deleteAll();

            ingredientRepo.save(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP));
            ingredientRepo.save(new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP));
            ingredientRepo.save(new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN));
            ingredientRepo.save(new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN));
            ingredientRepo.save(new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES));
            ingredientRepo.save(new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES));
            ingredientRepo.save(new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE));
            ingredientRepo.save(new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE));
            ingredientRepo.save(new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE));
            ingredientRepo.save(new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE));
        };
    }

    /**
     * 간단한 뷰 컨트롤러 설정.
     * - "/" → "home" 뷰
     * - "/login" → "login" 뷰
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/login").setViewName("login");
    }
}
