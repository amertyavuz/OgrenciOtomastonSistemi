package com.company.Service;

import com.company.model.Lesson;
import com.company.model.Notes;
import com.company.model.Student;
import com.company.model.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceImpl implements Service {

    private Connection openSqlConnection() {
        Connection connect;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/OKUL_OTOMASYONU?user=root&password=12345678");
            return connect;
        } catch (Exception e) {
            System.out.println("Veritabanına bağlanılamadı.");
        }
        return null;
    }

    @Override
    public boolean sorgulaOgrenci(int id, String sifre) {

        Connection connect = openSqlConnection();
        boolean a = false;
        try {
            PreparedStatement statement = connect.prepareStatement("SELECT * FROM STUDENT WHERE STUDENT_ID = ? AND PASSWORD = ? ");
            statement.setInt(1, id);
            statement.setString(2, sifre);

            ResultSet set = statement.executeQuery();
            a = set.next();
            connect.close();
        } catch (SQLException e) {
            System.out.println("Giriş başarısız.");
        }
        return a;
    }

    @Override
    public boolean sorgulaOgretmen(int id, String sifre) {
        Connection connect = openSqlConnection();
        boolean a = false;
        try {
            PreparedStatement statement = connect.prepareStatement("SELECT * FROM TEACHER WHERE TEACHER_ID = ? AND PASSWORD = ? ");
            statement.setInt(1, id);
            statement.setString(2, sifre);

            ResultSet set = statement.executeQuery();
            a = set.next();
            connect.close();
        } catch (SQLException e) {
            System.out.println("Giriş başarısız.");
        }
        return a;
    }

    @Override
    public Map<String ,String> girenHocaKim(int ogretmenID) {

        Connection connect = openSqlConnection();
        try {
            PreparedStatement statement = connect.prepareStatement("SELECT NAME,SURNAME FROM TEACHER WHERE TEACHER_ID = ?");
            statement.setInt(1, ogretmenID);
            ResultSet set = statement.executeQuery();
            HashMap<String,String> ogretmen_Ad_Soyad = new HashMap<>();

            while(set.next()){
                ogretmen_Ad_Soyad.put("ad", set.getString("NAME"));
                ogretmen_Ad_Soyad.put("soyad", set.getString("SURNAME"));
            }
            connect.close();
            return ogretmen_Ad_Soyad;

        } catch (SQLException e) {
            System.out.println("Öğretmen bilgileri getirilemedi.");
        }

        return new HashMap<String, String>();
    }

    @Override
    public Map<String, String> girenOgrenciKim(int ogrenciID) {
        Connection connect = openSqlConnection();
        try {
            PreparedStatement statement = connect.prepareStatement("SELECT NAME,SURNAME FROM STUDENT WHERE STUDENT_ID = ?");
            statement.setInt(1, ogrenciID);
            ResultSet set = statement.executeQuery();
            HashMap<String,String> ogretmen_Ad_Soyad = new HashMap<>();

            while(set.next()){
                ogretmen_Ad_Soyad.put("ad", set.getString("NAME"));
                ogretmen_Ad_Soyad.put("soyad", set.getString("SURNAME"));
            }
            connect.close();
            return ogretmen_Ad_Soyad;

        } catch (SQLException e) {
            System.out.println("Öğrenci bilgileri getirilemedi.");
        }
        return new HashMap<String, String>();
    }

    @Override
    public List<Lesson> hocaninVerdigiDersler(int ogretmenID) {

        Connection connect = openSqlConnection();
        try {
            PreparedStatement statement = connect.prepareStatement("Select * FROM LESSON WHERE " +
                    "LESSON.TEACHER_ID=?");

            statement.setInt(1,ogretmenID);

            ResultSet set = statement.executeQuery();
            List<Lesson> dersiVeren = new ArrayList<>();
            while(set.next()){
                Lesson lesson = new Notes();
                lesson.setLessonID(set.getInt("LESSON_ID"));
                lesson.setLessonName(set.getString("NAME"));
                lesson.setDersi_veren_ID(set.getInt("TEACHER_ID"));

                dersiVeren.add(lesson);
            }
            connect.close();
            return dersiVeren;

        } catch (SQLException e) {
            System.out.println("Ders sorgulama hatası.");
        }
        return new ArrayList<>();

    }

    @Override
    public void ogrenciKaydet(Student student) {
        Connection connect = openSqlConnection();
        try {
            PreparedStatement statement = connect.prepareStatement("INSERT INTO STUDENT (STUDENT_ID,NAME,SURNAME,PASSWORD)" +
                    " VALUES(?,?,?,?)");
            statement.setInt(1, student.getStudentID());
            statement.setString(2, student.getName());
            statement.setString(3, student.getSurname());
            statement.setString(4, student.getPassword());
            statement.execute();
            connect.close();
        } catch (SQLException e) {
            System.out.println("Öğrenci kayıt edilemedi");
        }
    }

    @Override
    public void ogrenciSil(int studentID) {

        Connection connect = openSqlConnection();
        try {
            PreparedStatement statement = connect.prepareStatement("DELETE FROM STUDENT " +
                    "WHERE  STUDENT_ID  =  (?) ");
            statement.setInt(1, studentID);
            statement.execute();
            statement = connect.prepareStatement("DELETE FROM ALINAN_DERS " +
                    "WHERE  STUDENT_ID  =  (?) ");
            statement.setInt(1, studentID);
            statement.execute();
            connect.close();
        } catch (SQLException e) {
            System.out.println(studentID + " nolu öğrenci silinemedi.");
        }
    }

    @Override
    public void ogretmenKaydet(Teacher teacher) {

        Connection connect = openSqlConnection();
        try {
            PreparedStatement statement = connect.prepareStatement("INSERT INTO TEACHER (TEACHER_ID,NAME,SURNAME,PASSWORD)" +
                    " VALUES(?,?,?,?)");
            statement.setInt(1, teacher.getTeacherID());
            statement.setString(2, teacher.getName());
            statement.setString(3, teacher.getSurname());
            statement.setString(4, teacher.getPassword());
            statement.execute();
            connect.close();
        } catch (SQLException e) {
            System.out.println("Hoca kayıt edilemedi");
        }
    }

    @Override
    public void ogretmenSil(int ogretmenId) {

        Connection connect = openSqlConnection();
        try {
            PreparedStatement statement = connect.prepareStatement("DELETE FROM TEACHER " +
                    "WHERE  TEACHER_ID  =  (?) ");
            statement.setInt(1, ogretmenId);
            statement.execute();
            statement = connect.prepareStatement("DELETE FROM LESSON " +
                    "WHERE  DERSI_VEREN_ID  =  (?) ");
            statement.setInt(1, ogretmenId);
            statement.execute();
            connect.close();
        } catch (SQLException e) {
            System.out.println(ogretmenId + " nolu öğretmen silinemedi.");
        }


    }

    @Override
    public List<Notes> ogretmenNotSorgulama(int studentID, int lessonID) {

        Connection connect = openSqlConnection();
        try {
            PreparedStatement statement = connect.prepareStatement("SELECT *" +
                    " FROM NOTES " +
                    "WHERE STUDENT_ID = ? AND LESSON_ID = ?");
            statement.setInt(1, studentID);
            statement.setInt(2, lessonID);

            ResultSet set = statement.executeQuery();
            List<Notes> not_Listesi = new ArrayList<>();
            while(set.next()){
                Notes not = new Notes();
                not.setLessonID(set.getInt("LESSON_ID"));
                not.setStudentID(set.getInt("STUDENT_ID"));
                not.setNote1(set.getInt("NOTE_1"));
                not.setNote2(set.getInt("NOTE_2"));

                not_Listesi.add(not);
            }
            connect.close();
            return not_Listesi;

        } catch (SQLException e) {
            System.out.println("öğretmen Not Sorgulama hatası.");
        }
        return new ArrayList<>();
    }

    @Override
    public List<Notes> notlariGor(int studentID) {

        Connection connect = openSqlConnection();
        try {
            PreparedStatement statement = connect.prepareStatement("SELECT *" +
                    " FROM NOTES,LESSON " +
                    "WHERE NOTES.LESSON_ID = LESSON.LESSON_ID AND NOTES.STUDENT_ID = ?");
            statement.setInt(1, studentID);
            ResultSet set = statement.executeQuery();

            List<Notes> not_Listesi = new ArrayList<>();
            while(set.next()){
                Notes not = new Notes();
                not.setLessonID(set.getInt("LESSON_ID"));
                not.setStudentID(set.getInt("STUDENT_ID"));
                not.setNote1(set.getInt("NOTE_1"));
                not.setNote2(set.getInt("NOTE_2"));
                not.setLessonName(set.getString("NAME"));

                not_Listesi.add(not);
            }
            connect.close();
            return not_Listesi;

        } catch (SQLException e) {
            System.out.println("öğrenci kendi Not Sorgulama hatası.");
        }
        return new ArrayList<>();

    }

    @Override
    public void ogretmenNotGirisi(int teacherID, Notes notes) {

        Connection connect = openSqlConnection();
        try {
            PreparedStatement statement = connect.prepareStatement("INSERT INTO NOTES (LESSON_ID,STUDENT_ID,NOTE_1,NOTE_2) VALUES(?,?,?,?)");

            statement.setInt(1, notes.getLessonID());
            statement.setInt(2, notes.getStudentID());
            statement.setInt(3, notes.getNote1());
            statement.setInt(4, notes.getNote2());

            statement.execute();
            connect.close();
        } catch (SQLException e) {
            System.out.println("ogretmen not girisi başarısız.");
        }

    }

    @Override
    public List<Lesson> getAllLesons(int studentID) {
        Connection connect = openSqlConnection();
        try {
            PreparedStatement statement = connect.prepareStatement("Select LESSON.NAME , LESSON.LESSON_ID FROM LESSON WHERE " +
                    "LESSON.LESSON_ID NOT IN (SELECT LESSON_ID FROM ALINAN_DERS WHERE STUDENT_ID=?)");

            statement.setInt(1,studentID);

            ResultSet set = statement.executeQuery();
            List<Lesson> ders_Listesi = new ArrayList<>();
            while(set.next()){
                Lesson lesson = new Notes();
                lesson.setLessonID(set.getInt("LESSON_ID"));
                lesson.setLessonName(set.getString("NAME"));

                ders_Listesi.add(lesson);
            }
            connect.close();
            return ders_Listesi;

        } catch (SQLException e) {
            System.out.println("Ders sorgulama hatası.");
        }
        return new ArrayList<>();

    }

    @Override
    public void ogretmenKendiniGuncelleme(Teacher teacher) {
        Connection connect = openSqlConnection();
        try {
            PreparedStatement statement = connect.prepareStatement("Update TEACHER SET TEACHER_ID=?, NAME=?,SURNAME=?,PASSWORD=? WHERE TEACHER_ID=?" );

            statement.setInt(1, teacher.getTeacherID());
            statement.setString(2, teacher.getName());
            statement.setString(3, teacher.getSurname());
            statement.setString(4, teacher.getPassword());
            statement.setInt(5, teacher.getTeacherID());

            statement.execute();
            connect.close();
        } catch (SQLException e) {
            System.out.println("Öğretmen kendini güncelleme hatası.");
        }
    }

    @Override
    public void ogrenciKendiniGuncelleme(Student student) {

        Connection connect = openSqlConnection();
        try {
            PreparedStatement statement = connect.prepareStatement("Update STUDENT SET STUDENT_ID=?, NAME=?,SURNAME=?,PASSWORD=? WHERE STUDENT_ID=?" );

            statement.setInt(1, student.getStudentID());
            statement.setString(2, student.getName());
            statement.setString(3, student.getSurname());
            statement.setString(4, student.getPassword());
            statement.setInt(5, student.getStudentID());

            statement.execute();
            connect.close();
        } catch (SQLException e) {
            System.out.println("Öğrenci kendini güncelleme hatası.");
        }
    }

    @Override
    public void ogretmenGuncelleme(Teacher teacher) {

        Connection connect = openSqlConnection();
        try {
            PreparedStatement statement = connect.prepareStatement("Update TEACHER SET TEACHER_ID=?, NAME=?,SURNAME=? WHERE TEACHER_ID=?" );

            statement.setInt(1, teacher.getTeacherID());
            statement.setString(2, teacher.getName());
            statement.setString(3, teacher.getSurname());
            statement.setInt(4, teacher.getTeacherID());

            statement.execute();
            connect.close();
        } catch (SQLException e) {
            System.out.println("Öğretmen kendini güncelleme hatası.");
        }
    }

    @Override
    public void ogrenciGuncelleme(Student student) {

        Connection connect = openSqlConnection();
        try {
            PreparedStatement statement = connect.prepareStatement("Update STUDENT SET STUDENT_ID=?, NAME=?,SURNAME=? WHERE STUDENT_ID=?" );

            statement.setInt(1, student.getStudentID());
            statement.setString(2, student.getName());
            statement.setString(3, student.getSurname());
            statement.setInt(4, student.getStudentID());
            statement.execute();
            connect.close();
        } catch (SQLException e) {
            System.out.println("Öğrenci güncelleme hatası.");
        }
    }

    @Override
    public void dersSec(int studentID, int ID) {

        Connection connect = openSqlConnection();
        try {
            PreparedStatement statement = connect.prepareStatement("INSERT INTO ALINAN_DERS (STUDENT_ID,LESSON_ID) VALUES(?,?)");

            statement.setInt(1, studentID);
            statement.setInt(2, ID);

            connect.close();
        } catch (SQLException e) {
            System.out.println("Ders seçimi başarısız.");
        }

    }

}

