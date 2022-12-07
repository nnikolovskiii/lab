package mk.ukim.finki.wp.lab.model;

import org.springframework.stereotype.Controller;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter(autoApply = true)
public class TeacherFullnameConverter implements
        AttributeConverter<TeacherFullname, String> {

    private static final String SEPARATOR = ", ";
    @Override
    public String convertToDatabaseColumn(TeacherFullname teacherFullname) {
        if (teacherFullname == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        if (teacherFullname.getSurname() != null && !teacherFullname.getSurname()
                .isEmpty()) {
            sb.append(teacherFullname.getSurname());
            sb.append(SEPARATOR);
        }

        if (teacherFullname.getName() != null
                && !teacherFullname.getName().isEmpty()) {
            sb.append(teacherFullname.getName());
        }

        return sb.toString();
    }

    @Override
    public TeacherFullname convertToEntityAttribute(String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }

        String[] pieces = s.split(SEPARATOR);

        if (pieces.length == 0) {
            return null;
        }

        TeacherFullname personName = new TeacherFullname();
        String firstPiece = !pieces[0].isEmpty() ? pieces[0] : null;
        if (s.contains(SEPARATOR)) {
            personName.setSurname(firstPiece);

            if (pieces.length >= 2 && pieces[1] != null
                    && !pieces[1].isEmpty()) {
                personName.setName(pieces[1]);
            }
        } else {
            personName.setName(firstPiece);
        }

        return personName;
    }

}
