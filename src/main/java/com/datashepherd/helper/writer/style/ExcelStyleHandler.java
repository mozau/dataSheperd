package com.datashepherd.helper.writer.style;

import com.datashepherd.annotation.style.ExcelStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.util.Objects;

public interface ExcelStyleHandler extends StyleHandler , FontStyle , BackgroundColorStyle {
    default void createStyle(ExcelStyle excelStyle,CellStyle style,Font font) {
        if (Objects.nonNull(excelStyle.font())) applyFontStyle(style,excelStyle.font(), font);
        if (Objects.nonNull(excelStyle.backgroundColor())) applyBackgroundColorStyle(style,excelStyle.backgroundColor());
        style.setAlignment(excelStyle.horizontalAlignment());
        style.setVerticalAlignment(excelStyle.verticalAlignment());
        if (Objects.nonNull(excelStyle.patternType())) style.setFillPattern(excelStyle.patternType());
    }
    default void defaultStyle(Font font,CellStyle style){
        font.setFontHeightInPoints((short) 12);
        font.setFontName("Arial");
        font.setBold(false);
        font.setItalic(false);
        font.setStrikeout(false);
        font.setColor(IndexedColors.BLACK.getIndex());
        style.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
    }
}
