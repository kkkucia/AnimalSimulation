package com.kociokwik.animalSimulation;

import com.kociokwik.animalSimulation.settings.Statistics;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvWriter {
    private static final String COMMA = ",";
    private static final String DEFAULT_SEPARATOR = COMMA;
    private static final String DOUBLE_QUOTES = "\"";
    private static final String EMBEDDED_DOUBLE_QUOTES = "\"\"";
    private static final String NEW_LINE_UNIX = "\n";
    private static final String NEW_LINE_WINDOWS = "\r\n";
    private static List<String[]> list = new ArrayList<>();


    public CsvWriter() {
        String[] header = {"Day", "Animal quantity", "Grass quantity", "Dead animals quantity", "Average age of deaths",
                "Average energy", "Average quantity of kids", "Free fileds", "Dominant gene"};
        list.add(header);
    }

    public void saveFile() {
        SimpleDateFormat dateFormater = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");

        try {
            java.net.URL url = this.getClass().getClassLoader().getResource("filesSaved");
            File parentDirectory = new File(new URI(url.toString()));
            writeToCsvFile(list, new File(parentDirectory, dateFormater.format(new Date()) + ".csv"));
        } catch (IOException | URISyntaxException exception) {
            System.out.println("nie zapisało się");
        }
    }

    private String convertToCsvFormat(final String[] line) {
        return convertToCsvFormat(line, DEFAULT_SEPARATOR);
    }

    private String convertToCsvFormat(final String[] line, final String separator) {
        return convertToCsvFormat(line, separator, true);
    }

    private String convertToCsvFormat(
            final String[] line,
            final String separator,
            final boolean quote) {

        return Stream.of(line)
                .map(l -> formatCsvField(l, quote))
                .collect(Collectors.joining(separator));

    }

    private String formatCsvField(final String field, final boolean quote) {

        String result = field;

        if (result.contains(COMMA)
                || result.contains(DOUBLE_QUOTES)
                || result.contains(NEW_LINE_UNIX)
                || result.contains(NEW_LINE_WINDOWS)) {

            result = result.replace(DOUBLE_QUOTES, EMBEDDED_DOUBLE_QUOTES);

            result = DOUBLE_QUOTES + result + DOUBLE_QUOTES;

        } else {
            if (quote) {
                result = DOUBLE_QUOTES + result + DOUBLE_QUOTES;
            }
        }

        return result;

    }

    private void writeToCsvFile(List<String[]> list, File file) throws IOException {

        List<String> collect = list.stream()
                .map(this::convertToCsvFormat)
                .collect(Collectors.toList());

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (String line : collect) {
                bw.write(line);
                bw.newLine();
            }
        }
    }

    public void addDayToCsv(Statistics stats) {
        String[] record = {String.valueOf(stats.day()), String.valueOf(stats.animalsQuantity()),
                String.valueOf(stats.grassQuantity()), String.valueOf(stats.animalCorpsesQuantity()),
                String.valueOf(stats.averageAge()), String.valueOf(stats.averageEnergy()),
                String.valueOf(stats.averageQuantityOfKids()), String.valueOf(stats.freeFields()), stats.dominantGene()};

        list.add(record);
    }
}
