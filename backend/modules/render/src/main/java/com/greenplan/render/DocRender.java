package com.greenplan.render;

public interface DocRender {
    byte[] pdfFromMarkdown(String md);

    byte[] xlsxFromPlantList(java.util.List<java.util.Map<String, Object>> plants);
}