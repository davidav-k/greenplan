package com.greenplan.render;

import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.util.*;

public class SimpleRender implements DocRender {
    @Override
    public byte[] pdfFromMarkdown(String md) {
        return ("PDF PLACEHOLDER\n\n" + md).getBytes();
    }

    @Override
    public byte[] xlsxFromPlantList(List<Map<String, Object>> plants) {
        try (var wb = new XSSFWorkbook(); var out = new ByteArrayOutputStream()) {
            var sh = wb.createSheet("Plants");
            int r = 0;
            sh.createRow(r++).createCell(0).setCellValue("Latin");
            for (var p : plants) {
                sh.createRow(r++).createCell(0).setCellValue(String.valueOf(p.getOrDefault("latin", "?")));
            }
            wb.write(out);
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}