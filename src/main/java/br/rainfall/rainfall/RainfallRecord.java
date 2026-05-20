package br.rainfall.rainfall;

import lombok.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RainfallRecord {

    private int id;
    private Double valor;
    private Date data;
    private int posto;
}