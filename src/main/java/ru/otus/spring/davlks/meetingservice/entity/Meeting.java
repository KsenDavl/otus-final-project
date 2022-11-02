package ru.otus.spring.davlks.meetingservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Column(name = "time")
    private LocalTime time;

    @Column(name = "approved")
    private boolean approved;

    @Column(name = "capacity")
    private int capacity;


//    @OneToMany(targetEntity = Comment.class, cascade = CascadeType.ALL, mappedBy = "book", fetch = FetchType.LAZY)
//    private List<String> requirements;

    //private List<User> participants;

//    private User organizer;

    //private Admin approver;




}
