alter table meeting.meetings drop column approved;

alter table meeting.meetings add column status VARCHAR(50);