package kg.BIZ.dto.request;

public record AboutMeForVolunteerRequest(
        String location,
        String motivation,
        String experience,
        String skills,
        String strengths,
        String busyness,
        String socialMediaPages

) {
}
