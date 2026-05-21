package br.rainfall.service;

import br.rainfall.model.RainfallRecord;
import br.rainfall.repository.RainfallRepository;
import br.rainfall.repository.RainfallRepositoryImpl;

import java.util.List;

public class StaticService {

    private final RainfallRepository repository;
    private List<RainfallRecord> rainfall;

    public StaticService() {
        this.repository = new RainfallRepositoryImpl();
        this.rainfall = this.repository.getAllRecords();
    }

    public String totalRainfallPerMonth() {
        return "TotalperMonth";
    }

    public String maxDayRainfall() {
        return "Max";
    }

    public String minDayRainfall() {
        return "Min";
    }

    public String maxMonthRainfall() {
        return "Max Month";
    }

    public String minMonthRainfall() {
        return "Min Month";
    }

    public String avgYearRainfall() {
        return "Avg Year";
    }

    public String avgMonthRainfall() {
        return "Avg Month";
    }

    public String topTenDays() {
        return "Top Ten Days";
    }
}