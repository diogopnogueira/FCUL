package business.occupation;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import business.lesson.Lesson;
import business.converter.LocalDateAttributeConverter;

@Entity
public class Occupation {

    @Id
    @GeneratedValue
    private int id;

    @Column
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate startDate;

    @Column
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate endDate;

    @OneToOne
    private Lesson lesson;

    Occupation() {
    }

    public Occupation(LocalDate startDate, LocalDate endDate, Lesson lesson) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.lesson = lesson;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public String toString() {
        return "Lesson: " + lesson.getName() + "\n" +
                "Start hour: " + lesson.getStartHour() + "\n" +
                "End hour: " + lesson.getEndHour() + "\n";
    }

}
