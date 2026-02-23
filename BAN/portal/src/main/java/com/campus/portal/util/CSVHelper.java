package com.campus.portal.util;

import com.campus.portal.model.Learner;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CSVHelper {

    public static void exportData(List<Learner> list, File file) throws IOException {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

            writer.write("id_number,name,course,level,gpa,email,contact,status");
            writer.newLine();

            for (Learner l : list) {
                writer.write(String.join(",",
                        l.getRegNumber(),
                        l.getName(),
                        l.getCourse(),
                        String.valueOf(l.getYearLevel()),
                        String.valueOf(l.getAverageScore()),
                        l.getEmailAddress(),
                        l.getContactNumber(),
                        l.getEnrollmentStatus()
                ));
                writer.newLine();
            }
        }
    }

    public static List<Learner> importData(File file) throws IOException {

        List<Learner> list = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            reader.readLine(); // skip header

            String line;
            while ((line = reader.readLine()) != null) {

                String[] p = line.split(",");

                if (p.length < 8) {
                    throw new IOException("Invalid CSV row: expected at least 8 fields");
                }

                Learner l = new Learner(
                        p[0], // id_number (regNumber)
                        p[1], // name
                        p[2], // course
                        Integer.parseInt(p[3]), // level (yearLevel)
                        Double.parseDouble(p[4]), // gpa (averageScore)
                        p[5], // email
                        p[6], // contact
                        LocalDate.now(), // default created_on to now (since not in CSV)
                        p[7] // status
                );

                list.add(l);
            }
        }

        return list;
    }
}