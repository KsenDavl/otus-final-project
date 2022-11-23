package ru.otus.spring.davlks.meetingservice.enums;

public enum MeetingStatus {

    CREATED, //создано, не одобрено

    REJECTED, // не одобрено, отклонено

    APPROVED, //одобрено, можно присоединиться

    TO_CANCEL, //с заявкой организатора на отмену

    CANCELLED,//отмененное

    ARCHIVED;//прошедшее

}
