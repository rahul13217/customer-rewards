import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RewardService {

    public List<CustomerRewards> calculateRewards(List<Transaction> transactions) {
        List<CustomerRewards> rewards = new ArrayList<>();

        Map<String, Map<String, Integer>> customerRewardsMap = transactions.stream()
                .collect(Collectors.groupingBy(
                        t -> t.getCustomerId(),
                        Collectors.groupingBy(
                                t -> t.getTransactionDate().getMonth().toString(),
                                Collectors.summingInt(t -> calculatePoints(t.getAmount()))
                        )
                ));

        customerRewardsMap.forEach((customerId, monthPoints) -> {
            monthPoints.forEach((month, points) -> {
                rewards.add(new CustomerRewards(customerId, month, points));
            });
        });

        return rewards;
    }

    private int calculatePoints(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.valueOf(100)) > 0) {
            return (amount.subtract(BigDecimal.valueOf(100))).intValue() * 2 + 50;
        } else if (amount.compareTo(BigDecimal.valueOf(50)) >= 0) {
            return amount.subtract(BigDecimal.valueOf(50)).intValue();
        } else {
            return 0;
        }
    }
}