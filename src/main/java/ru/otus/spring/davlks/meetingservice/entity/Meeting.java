package ru.otus.spring.davlks.meetingservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
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

    @Column(name = "approved")
    private boolean approved;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "seats_left")
    private int seatsLeft;

//    @OneToMany(targetEntity = Comment.class, cascade = CascadeType.ALL, mappedBy = "book", fetch = FetchType.LAZY)
//    private List<String> requirements;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "meetings_users",
            joinColumns = {@JoinColumn(name = "meeting_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private List<User> users;

    @Column(name = "organizerLogin")
    private String organizerLogin;

    //private Admin approver;

}
