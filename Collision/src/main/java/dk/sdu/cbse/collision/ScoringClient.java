package dk.sdu.cbse.collision;

import org.springframework.web.client.RestTemplate;

public class ScoringClient {

    private static final String BASE_URL = "http://localhost:8080";

    private final RestTemplate restTemplate = new RestTemplate();

    public int addScore(int points) {
        try {
            return restTemplate.postForObject(
                    BASE_URL + "/score/add?points=" + points,
                    null,
                    Integer.class
            );
        } catch (Exception exception) {
            System.out.println("Could not update score service: " + exception.getMessage());
            return -1;
        }
    }

    public int getScore() {
        try {
            Integer score = restTemplate.getForObject(
                    BASE_URL + "/score",
                    Integer.class
            );

            return score == null ? 0 : score;
        } catch (Exception exception) {
            return 0;
        }
    }

    public void resetScore() {
        try {
            restTemplate.postForObject(
                    BASE_URL + "/score/reset",
                    null,
                    Integer.class
            );
        } catch (Exception exception) {
            System.out.println("Could not reset score service: " + exception.getMessage());
        }
    }
}