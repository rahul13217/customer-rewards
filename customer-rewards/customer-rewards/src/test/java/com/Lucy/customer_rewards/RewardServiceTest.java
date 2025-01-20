import com.example.customerrewards.RewardService;
import com.example.customerrewards.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RewardServiceTest {

    @Autowired
    private RewardService rewardService;

    @Test
    public void testCalculateRewards() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("customer1", LocalDate.of(2024, 1, 15), BigDecimal.valueOf(120)),
                new Transaction("customer1", LocalDate.of(2024, 1, 20), BigDecimal.valueOf(75)),
                new Transaction("customer2", LocalDate.of(2024, 1, 10), BigDecimal.valueOf(40)),
                new Transaction("customer1", LocalDate.of(2024, 2, 5), BigDecimal.valueOf(150)),
                new Transaction("customer2", LocalDate.of(2024, 2, 28), BigDecimal.valueOf(90))
        );

        List<CustomerRewards> expectedRewards = Arrays.asList(
                new CustomerRewards("customer1", "JANUARY", 145),
                new CustomerRewards("customer1", "FEBRUARY", 150),
                new CustomerRewards("customer2", "JANUARY", 0),
                new CustomerRewards("customer2", "FEBRUARY", 40)
        );

        List<CustomerRewards> actualRewards = rewardService.calculateRewards(transactions);

        assertEquals(expectedRewards, actualRewards);
    }
}