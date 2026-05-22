package br.rainfall.service;

import br.rainfall.model.RainfallRecord;
import br.rainfall.repository.RainfallRepository;
import br.rainfall.repository.RainfallRepositoryImpl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StaticService {

    private final RainfallRepository repository;
    private final List<RainfallRecord> rainfall;

    public StaticService() {
        this.repository = new RainfallRepositoryImpl();
        this.rainfall = this.repository.getAllRecords();
    }

    private int getYearOfRecord(RainfallRecord record) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(record.getData());
        return cal.get(Calendar.YEAR);
    }

    private int getMonthOfRecord(RainfallRecord record) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(record.getData());
        return cal.get(Calendar.MONTH) + 1;
    }

    public String totalRainfallPerMonth(int year) {
        if (rainfall.isEmpty()) return "Nenhum registro encontrado.";

        SimpleDateFormat monthFormat = new SimpleDateFormat("MM/yyyy");

        Map<String, Double> totalPerMonth = rainfall.stream()
                .filter(r -> getYearOfRecord(r) == year)
                .collect(Collectors.groupingBy(
                        r -> monthFormat.format(r.getData()),
                        java.util.TreeMap::new, // Mantém os meses ordenados cronologicamente
                        Collectors.summingDouble(RainfallRecord::getValor)
                ));

        if (totalPerMonth.isEmpty()) return "Nenhum registro para o ano de " + year;

        StringBuilder sb = new StringBuilder("\nTotal de chuva por Mês em " + year + ":\n");
        totalPerMonth.forEach((mes, total) -> 
            sb.append("Mês/Ano: ").append(mes).append(" -> Total: ").append(String.format("%.2f", total)).append(" mm\n")
        );
        return sb.toString();
    }

    public String maxDayRainfall(int year) {
        return rainfall.stream()
                .filter(r -> getYearOfRecord(r) == year)
                .max(Comparator.comparingDouble(RainfallRecord::getValor))
                .map(r -> String.format("%.2f mm em %s", r.getValor(), new SimpleDateFormat("dd/MM/yyyy").format(r.getData())))
                .orElse("Nenhum registro encontrado.");
    }

    public String minDayRainfall(int year) {
        return rainfall.stream()
                .filter(r -> getYearOfRecord(r) == year)
                .min(Comparator.comparingDouble(RainfallRecord::getValor))
                .map(r -> String.format("%.2f mm em %s", r.getValor(), new SimpleDateFormat("dd/MM/yyyy").format(r.getData())))
                .orElse("Nenhum registro encontrado.");
    }

    private Map<Integer, Double> getMonthlyTotalsMap(int year) {
        return rainfall.stream()
                .filter(r -> getYearOfRecord(r) == year)
                .collect(Collectors.groupingBy(
                        this::getMonthOfRecord,
                        Collectors.summingDouble(RainfallRecord::getValor)
                ));
    }

    public String maxMonthRainfall(int year) {
        Map<Integer, Double> monthlyTotals = getMonthlyTotalsMap(year);
        return monthlyTotals.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(e -> "Mês " + e.getKey() + " com " + String.format("%.2f", e.getValue()) + " mm")
                .orElse("Nenhum registro encontrado.");
    }

    public String minMonthRainfall(int year) {
        Map<Integer, Double> monthlyTotals = getMonthlyTotalsMap(year);
        return monthlyTotals.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(e -> "Mês " + e.getKey() + " com " + String.format("%.2f", e.getValue()) + " mm")
                .orElse("Nenhum registro encontrado.");
    }

    public String avgYearRainfall(int year) {
        return rainfall.stream()
                .filter(r -> getYearOfRecord(r) == year)
                .mapToDouble(RainfallRecord::getValor)
                .average()
                .transform(avg -> avg.isPresent() ? String.format("%.2f mm diários", avg.getAsDouble()) : "Nenhum registro.");
    }

    public String avgMonthRainfall(int year) {
        if (rainfall.isEmpty()) return "Nenhum registro encontrado.";

        Map<Integer, Double> avgPerMonth = rainfall.stream()
                .filter(r -> getYearOfRecord(r) == year)
                .collect(Collectors.groupingBy(
                        this::getMonthOfRecord,
                        java.util.TreeMap::new,
                        Collectors.averagingDouble(RainfallRecord::getValor)
                ));

        if (avgPerMonth.isEmpty()) return "Nenhum registro para o ano de " + year;

        StringBuilder sb = new StringBuilder("\nMédia de chuva por Mês em " + year + ":\n");
        avgPerMonth.forEach((mes, media) -> 
            sb.append("Mês: ").append(String.format("%02d", mes)).append(" -> Média Diária: ").append(String.format("%.2f", media)).append(" mm\n")
        );
        return sb.toString();
    }

    public String topTenDays(int year) {
        List<RainfallRecord> topTen = rainfall.stream()
                .filter(r -> getYearOfRecord(r) == year)
                .sorted(Comparator.comparingDouble(RainfallRecord::getValor).reversed())
                .limit(10)
                .collect(Collectors.toList());

        if (topTen.isEmpty()) return "Nenhum registro encontrado.";

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder sb = new StringBuilder("\n--- OS 10 DIAS MAIS CHUVOSOS DE " + year + " ---\n");
        for (int i = 0; i < topTen.size(); i++) {
            RainfallRecord r = topTen.get(i);
            sb.append(String.format("%dº) %s -> %.2f mm\n", i + 1, sdf.format(r.getData()), r.getValor()));
        }
        return sb.toString();
    }
}
