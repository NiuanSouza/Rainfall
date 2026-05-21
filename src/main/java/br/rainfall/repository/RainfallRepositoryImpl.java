package br.rainfall.repository;

import br.rainfall.model.RainfallRecord;
import br.rainfall.utils.ReadCSV;
import java.util.List;

public class RainfallRepositoryImpl implements RainfallRepository {

    @Override
    public List<RainfallRecord> getAllRecords() {
        return ReadCSV.listCsv();
    }
}