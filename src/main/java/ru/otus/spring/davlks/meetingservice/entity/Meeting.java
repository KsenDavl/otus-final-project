package ru.otus.spring.davlks.meetingservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.otus.spring.davlks.meetingservice.enums.MeetingStatus;
import ru.otus.spring.davlks.meetingservice.security.entity.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "meetings")
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @Column(name = "start_time", columnDefinition = "TIME")
    private LocalTime startTime;

    @Column(name = "finish_time", columnDefinition = "TIME")
    private LocalTime finishTime;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "seats_left")
    private int seatsLeft;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "meetings_users",
            joinColumns = {@JoinColumn(name = "meeting_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private List<User> users;

    @Column(name = "organizer_login")
    private String organizerLogin;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private MeetingStatus status;

}
