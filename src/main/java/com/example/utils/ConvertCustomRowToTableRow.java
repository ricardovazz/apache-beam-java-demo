/*package com.example.utils;

import org.apache.beam.sdk.transforms.SimpleFunction;
import com.google.api.services.bigquery.model.TableRow;

public class ConvertCustomRowToTableRow extends SimpleFunction<CustomRow, TableRow> {
    @Override
    public TableRow apply(CustomRow input) {
        TableRow tableRow = new TableRow();

        tableRow.set("row", input.getRow());
        tableRow.set("metadata", input.getMetadata());

        return tableRow;
    }

    public static SimpleFunction<CustomRow, TableRow> convertCustomRowToTableRow() {
        return new ConvertCustomRowToTableRow();
    }
}*/
