package com.company;

import com.company.Service.Service;
import com.company.Service.ServiceImpl;
import com.company.model.Lesson;
import com.company.model.Notes;
import com.company.model.Student;
import com.company.model.Teacher;
import com.sun.tools.internal.ws.wsdl.document.soap.SOAPUse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    static Service service = new ServiceImpl();
    static Scanner tara = new Scanner(System.in);

    public static void main(String[] args) {

        menuGoster();

        Service service = new ServiceImpl();
        Student student = new Student();
        Teacher teacher = new Teacher();

        Notes notes1 = new Notes();

        notes1.setNote1(10);
        notes1.setNote2(100);
        notes1.setLessonID(2);
        notes1.setStudentID(12);

        //List<Notes> liste=service.notlariGor(12);
        //System.out.println(liste.get(0).getStudentID()+"-"+liste.get(0).getLessonName()+"-"+liste.get(0).getNote1());


        //service.ogretmenNotGirisi(10,notes1);


        /*List<Lesson> ders_liste=service.getAllLesons();

        for(Lesson l : ders_liste){
            System.out.println(l.getLessonName());
        }
        */

        /*
        teacher.setTeacherID(5);
        teacher.setName("recep");
        teacher.setSurname("tayyip");
        teacher.setPassword("123456789");

        service.ogretmenKendiniGuncelleme(teacher);

        */


        //List<Notes> liste=service.ogretmenNotSorgulama(12,1);
        //System.out.println(liste.get(0).getStudentID()+"- "+liste.get(0).getNote1()+"- "+liste.get(0).getNote2());

        /*

        student.setStudentID(12);
        student.setName("Ahmet");
        student.setSurname("Yavuz");
        student.setPassword("123");
        service.ogrenciKaydet(student);

        service.ogreniSil(15);
        */
    }

    private static void menuGoster() {

        System.out.println("Yapmak istediğiniz işlemi seçiniz:\n " +
                "1- Öğretim Üyesi İşlemleri\n " +
                "2- Öğrenci İşlemleri\n " +
                "3- Çıkış");

        boolean cikis = false;
        while (!cikis) {
            int secim = tara.nextInt();
            switch (secim) {

                case 1:
                    cikis = true;
                    ogretmenGiris();
                    break;

                case 2:
                    cikis = true;
                    ogrenciGiris();
                    break;

                case 3:
                    System.out.println("Çıkış yapılıyor...");
                    cikis = true;
                    break;

                default:
                    System.out.println("Lütfen 1-3 arası değer giriniz.");
                    break;
            }
        }
    }

    private static void ogrenciMenusu(int ogrenciID) {
        boolean cikis = false;
        while (!cikis) {
            System.out.println("*****Öğrenci Menüsü*****");
            System.out.println("Yapmak istediğiniz işlemi seçin.");
            System.out.println("1-Ders seçimi");
            System.out.println("2-Notları Görmek");
            System.out.println("3-Çıkış");
            int secim = tara.nextInt();
            switch (secim) {

                case 1:
                    dersSecimi(ogrenciID);
                    break;

                case 2:
                    System.out.println("Notlarınız:");
                    List<Notes> notes = service.notlariGor(ogrenciID);
                    for (Notes note : notes) {
                        System.out.println("Ders Adi: " + note.getLessonName());
                        System.out.println("Vize " + note.getNote1());
                        System.out.println("Final " + note.getNote2());
                        System.out.println("***************************");
                    }
                    break;

                case 3:
                    System.out.println("Çıkış yapılıyor...");
                    cikis = true;
                    break;

                default:
                    System.out.println("Lütfen 1-3 arası değer giriniz.");
                    break;
            }
        }
    }
    private static void dersSecimi(int ogrenciID) {

        System.out.println("Ders Seçimi Menüsü");
        List<Lesson> tumDersler = service.getAllLesons(ogrenciID);
        for (Lesson l : tumDersler) {
            System.out.println("ID:" + l.getLessonID() + " - " + l.getLessonName());
        }
        if (tumDersler.size() > 0) {
            System.out.println("Seçmek istediğiniz dersin ıd'sini giriniz.");
            int secilenDers = tara.nextInt();
            boolean aa=false;
            for(Lesson l : tumDersler){
                if(l.getLessonID() ==secilenDers){
                    aa=true;
                }
            }
            if(aa) {
                service.dersSec(ogrenciID, secilenDers);
                System.out.println("Eklendi.");
            }
            else{
                System.out.println("Yanlış id girdiniz.");
                dersSecimi(ogrenciID);
            }
        }
        else {
            System.out.println("Tüm dersler zaten seçili");
        }
    }
    private static void ogrenciGiris() {
        boolean giris = false;
        while (!giris) {
            System.out.println("ID'nizi giriniz.");
            int ogrenciID = tara.nextInt();
            System.out.println("şifrenizi giriniz.");
            String sifre = tara.next();

            boolean a = service.sorgulaOgrenci(ogrenciID, sifre);
            Map<String, String> gelenOgrenciBilgileri = service.girenOgrenciKim(ogrenciID);
            if (a == true) {
                giris = true;
                System.out.println("Hogeldiniz " + gelenOgrenciBilgileri.get("ad"));
                ogrenciMenusu(ogrenciID);
            } else {
                System.out.println("hatalı giriş. Tekrar deneyiniz...");
            }
        }
    }
    private static void ogretmenGiris() {

        boolean giris = false;

        while (!giris) {

            System.out.println("ID'nizi giriniz.");
            int ogretmenID = tara.nextInt();
            System.out.println("şifrenizi giriniz.");
            String sifre = tara.next();

            boolean a = service.sorgulaOgretmen(ogretmenID, sifre);
            Map<String, String> gelenHocaBilgileri = service.girenHocaKim(ogretmenID);
            if (a == true) {
                giris = true;
                System.out.println("Hogeldiniz " + gelenHocaBilgileri.get("ad"));
                System.out.println();
                ogretmenMenusu(ogretmenID);
            } else {
                System.out.println("Hatalı giriş.Tekrar deneyiniz...");
            }
        }
    }

    private static void ogretmenMenusu(int ogretmenID) {

        System.out.println("*****Öğretmen Menüsü*****");
        System.out.println("Yapmak istediğiniz işlemi seçin.");
        System.out.println("1-Not Girişi");
        System.out.println("2-Not Sorgulama");
        System.out.println("3-Bilgi Güncelleme");
        System.out.println("4-Çıkış");

        boolean cikis = false;
        while (!cikis) {

            int secim = tara.nextInt();
            switch (secim) {

                case 1:


                    break;

                case 2:

                    List<Lesson> gelen=service.hocaninVerdigiDersler(ogretmenID);
                    System.out.println("Verilen Dersler");
                    for (Lesson l : gelen){
                        System.out.println(l.getLessonID()+" - "+ l.getLessonName());
                    }

                    break;

                case 3:

                    break;

                case 4:
                    cikis=true;
                    System.out.println("Çıkış Yapılıyor.");
                    break;
                default:
                    System.out.println("Lütfen 1-4 arası değer giriniz.");
                    break;
            }
        }


    }
}
