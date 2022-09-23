package com.newproject.travelbycaravan.excel;

import com.newproject.travelbycaravan.model.User;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class Excel {

    static String[] HEADERS_USER={"id","FirstName","lastName","phoneNumber","Email","address","zipCode","Roles"};
    static String SHEET_USER="Customer";

    public  static ByteArrayInputStream usersExel(List<User> users){
        try(Workbook workbook=new XSSFWorkbook(); ByteArrayOutputStream out =new ByteArrayOutputStream()) {
        Sheet sheet=workbook.createSheet(SHEET_USER);
        Row headerRow=sheet.createRow(0);

        for(int column=0; column<HEADERS_USER.length;column++){

            Cell cell=headerRow.createCell(column);
            cell.setCellValue(HEADERS_USER[column]);
        }

        int rowId=1;
        for(User user:users){
            Row row =sheet.createRow(rowId++);
            row.createCell(0).setCellValue(user.getId());
            row.createCell(1).setCellValue(user.getFirstName());
            row.createCell(2).setCellValue(user.getLastName());
            row.createCell(3).setCellValue(user.getPhoneNumber());
            row.createCell(4).setCellValue(user.getEmail());
            row.createCell(5).setCellValue(user.getAddress());
            row.createCell(6).setCellValue(user.getZipCode());
            row.createCell(7).setCellValue(user.getRoles().toString());


        }

        workbook.write(out);

        return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException e) {
         throw new RuntimeException("fail to import data to Excel file");
        }


    }

}
