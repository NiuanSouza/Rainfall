package br.rainfall;

import br.rainfall.service.StaticService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        StaticService service = new StaticService();

        Scanner scanner = new Scanner(System.in);
        int option = -1;

        System.out.println("=== SISTEMA DE ANÁLISE DE PLUVIOMETRIA (ACARAÚ - 2025) ===");

        while (option != 0) {
            System.out.println("\nEscolha uma estatística para visualizar:");
            System.out.println("1 - Total de precipitação para cada mês do ano");
            System.out.println("2 - Dia de maior e menor precipitação no ano");
            System.out.println("3 - Mês de maior e menor precipitação no ano");
            System.out.println("4 - Média de precipitação do ano");
            System.out.println("5 - Média da precipitação de cada mês do ano");
            System.out.println("6 - Os 10 Dias de maior precipitação no ano");
            System.out.println("0 - Sair");
            System.out.print("Digite a opção: ");

            try {
                option = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido.");
                continue;
            }

            System.out.println("\n--------------------------------------------------");
            switch (option) {
                case 1:
                    System.out.print("Por favor digite o ano desejado: ");
                    try {
                        option = Integer.parseInt(scanner.nextLine());
                        System.out.println("Total de chuva por Mês: " + service.totalRainfallPerMonth());

                    } catch (NumberFormatException e) {
                        System.out.println("Por favor, digite um número válido.");
                        continue;
                    }
                    break;
                case 2:
                    System.out.print("Por favor digite o ano desejado: ");
                    try {
                        int year = Integer.parseInt(scanner.nextLine());
                        System.out.println("Maior Dia: " + service.maxDayRainfall(year));
                        System.out.println("Menor Dia: " + service.minDayRainfall(year));
                    } catch (NumberFormatException e) {
                        System.out.println("Por favor, digite um número válido.");
                        continue;
                    }

                    break;
                case 3:

                    System.out.print("Por favor digite o ano desejado: ");
                    try {
                        int year = Integer.parseInt(scanner.nextLine());
                        System.out.println("Maior Mês: " + service.maxMonthRainfall(year));
                        System.out.println("Menor Mês: " + service.minMonthRainfall(year));
                    } catch (NumberFormatException e) {
                        System.out.println("Por favor, digite um número válido.");
                        continue;
                    }


                    break;
                case 4:
                    System.out.println("Resultado: " + service.avgYearRainfall());
                    break;
                case 5:
                    System.out.println("Resultado: " + service.avgMonthRainfall());
                    break;
                case 6:
                    System.out.println("Resultado: " + service.topTenDays());
                    break;
                case 0:
                    System.out.println("Encerrando o programa. Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
                    break;
            }
            System.out.println("--------------------------------------------------");
        }

        scanner.close();
    }
}