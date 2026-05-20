package br.rainfall.rainfall;

import br.rainfall.utils.ReadCSV;

import java.util.List;

public class RainfallManipulate {

    private List<RainfallRecord> rainfall;

    void RainfallRecord(){
        this.rainfall = ReadCSV.listCsv();
    }

    public String TotalRainfallPerMonth(){
        return "TotalperMonth";
    }

    public String MaxDayRainfall(){
        return "Max";
    }

    public String MinDayRainfall(){
        return "Min";
    }

    public String MaxMonthRainfall(){
        return "Max Month";
    }

    public String MinMonthRainfall(){
        return "Min Month";
    }

    public String AvgYearRainfall(){
        return "Avg Year";
    }

    public String AvgMonthRainfall(){
        return "Avg Month";
    }

    public String TopTenDays(){
        return "Top Ten Days";
    }
}
