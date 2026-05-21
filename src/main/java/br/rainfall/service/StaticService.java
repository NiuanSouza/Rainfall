package br.rainfall.service;

import br.rainfall.model.RainfallRecord;
import br.rainfall.repository.RainfallRepository;
import br.rainfall.repository.RainfallRepositoryImpl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaticService {

    private final RainfallRepository repository;
    private List<RainfallRecord> rainfall;

    public StaticService() {
        this.repository = new RainfallRepositoryImpl();
        this.rainfall = this.repository.getAllRecords();
    }

    public String totalRainfallPerMonth(int month, int year) {
        if (this.rainfall == null || this.rainfall.isEmpty()) {
            return "Nenhum registro encontrado.";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
        Map<String, Double> total = new HashMap<>();

        for (RainfallRecord record : this.rainfall) {
            String monthKey = sdf.format(record.getData());
            double volRain = record.getValor();

            if (!total.containsKey(monthKey)) {
                total.put(monthKey, volRain);
            } else {
                double valorAnterior = total.get(monthKey);
                total.put(monthKey, valorAnterior + volRain);
            }
        }

        Map<String, Double> sortedTotal = new java.util.TreeMap<>(new java.util.Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                try {
                    java.util.Date d1 = sdf.parse(o1);
                    java.util.Date d2 = sdf.parse(o2);
                    return d1.compareTo(d2);
                } catch (java.text.ParseException e) {
                    return o1.compareTo(o2);
                }
            }
        });

        sortedTotal.putAll(total);

        StringBuilder result = new StringBuilder("\n");
        for (Map.Entry<String, Double> entry : sortedTotal.entrySet()) {
            result.append("Mês/Ano: ").append(entry.getKey())
                    .append(" -> Total: ").append(entry.getValue()).append(" mm\n");
        }

        return result.toString();
    }

    public String maxDayRainfall(int year) {
        if (rainfall.isEmpty()) return "Nenhum registro";

        RainfallRecord maxRecord = rainfall.getFirst();
        for (RainfallRecord record : rainfall) {
            if (record.getValor() > maxRecord.getValor()) {
                maxRecord = record;
            }
        }
        return maxRecord.getValor() + " em " + maxRecord.getData();
    }

    public String minDayRainfall(int year) {
        if (rainfall.isEmpty()) return "Nenhum registro";

        RainfallRecord minRecord = rainfall.getFirst();
        for (RainfallRecord record : rainfall) {
            if (record.getValor() < minRecord.getValor()) {
                minRecord = record;
            }
        }
        return minRecord.getValor() + " em " + minRecord.getData();
    }

    public String maxMonthRainfall(int year){
        if (rainfall.isEmpty()) return "Nenhum registro";
        return "Max Month";
    }

    public String minMonthRainfall(int year) {
        if (rainfall.isEmpty()) return "Nenhum registro";
        return "Min Month";
    }

    public String avgYearRainfall(int year) {
        if (rainfall.isEmpty()) return "Nenhum registro";
        return "Avg Year";
    }

    public String avgMonthRainfall(int month, int year) {
        if (rainfall.isEmpty()) return "Nenhum registro";
        return "Avg Month";
    }

    public String topTenDays(int year) {
        if (rainfall.isEmpty()) return "Nenhum registro";
        return "Top Ten Days";}
}