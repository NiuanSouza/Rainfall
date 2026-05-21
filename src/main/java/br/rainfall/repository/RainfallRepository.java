package br.rainfall.repository;

import br.rainfall.model.RainfallRecord;
import java.util.List;

public interface RainfallRepository {
    List<RainfallRecord> getAllRecords();
}