package br.rainfall.utils;

import br.rainfall.model.RainfallRecord;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReadCSV {

    private static final String PATH = "/PluviometriaFuncemeNormalizada_2026-05-19T21_02_25.csv";

    private ReadCSV(){}

    public static List<RainfallRecord> listCsv() {
        ArrayList<RainfallRecord> records = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        InputStream is = ReadCSV.class.getResourceAsStream(PATH);
        if (is == null) {
            System.err.println("Arquivo não encontrado: " + PATH);
            return Collections.emptyList();
        }

        try (InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(isr)) {

            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] dados = line.split(";");

                if (dados.length >= 4) {
                    try {
                        int id = Integer.parseInt(dados[0].trim());
                        Double valor = Double.parseDouble(dados[1].trim());
                        java.util.Date data = sdf.parse(dados[2].trim());
                        int posto = Integer.parseInt(dados[3].trim());

                        RainfallRecord record = new RainfallRecord(id, valor, data, posto);
                        records.add(record);

                    } catch (Exception e) {
                        System.err.println("Erro ao processar a linha: " + line + " -> " + e.getMessage());
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Erro ao ler o arquivo CSV: " + e.getMessage());
            return Collections.emptyList();
        }

        return records;
    }
}