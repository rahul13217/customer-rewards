import com.example.customerrewards.RewardController;
import com.example.customerrewards.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@WebMvcTest(RewardController.class)
public class RewardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCalculateRewards() throws Exception {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("customer1", LocalDate.of(2024, 1, 15), BigDecimal.valueOf(120)),
                new Transaction("customer1", LocalDate.of(2024, 1, 20), BigDecimal.valueOf(75)),
                new Transaction("customer2", LocalDate.of(2024, 1, 10), BigDecimal.valueOf(40)),
                new Transaction("customer1", LocalDate.of(2024, 2, 5), BigDecimal.valueOf(150)),
                new Transaction("customer2", LocalDate.of(2024, 2, 28), BigDecimal.valueOf(90))
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/calculate-rewards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactions)))
                .andExpect(MockMvcResultMatchers.status().isOk()); // Verify that the endpoint returns 200 OK
    }
}