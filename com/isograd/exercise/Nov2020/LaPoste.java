package com.isograd.exercise.Nov2020;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LaPoste {
    static Scanner in;

    public static void main(String[] argv) throws Exception {
        in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int N = Integer.parseInt(in.nextLine());



        Colis[] colis = new Colis[N];

        Set<Colis> bestCar1 = null;
        Set<Colis> bestCar2 = null;

        int minDiff = Integer.MAX_VALUE;

        for (int i = 0; i < N; i++) {
            String[] line = in.nextLine().split(" ");
            colis[i] = new Colis(line[0], Integer.parseInt(line[1]));
        }

        for (int i = 1; i < 1<<N; i++) {
//            System.out.println(i);
            Set<Colis> car1 = new HashSet<>();
            Set<Colis> car2 = new HashSet<>();

            for (int j = 0; j < N; j++) {
                if((i >> j) % 2 == 1) car1.add(colis[j]);
                else  car2.add(colis[j]);
            }

            int sumCar1 = car1.stream().mapToInt(c -> c.volume).sum();
            int sumCar2 = car2.stream().mapToInt(c -> c.volume).sum();
            int difference = Math.abs(sumCar1- sumCar2);
            if(difference < minDiff){
                minDiff= difference;
                bestCar1 = car1;
                bestCar2 = car2;
            }

        }

        System.out.println(bestCar1.stream().map(c -> c.id).collect(Collectors.joining(" ")));
        System.out.println(bestCar2.stream().map(c -> c.id).collect(Collectors.joining(" ")));
    }

    public static int[] lineToInt(String line, String regex) {
        return Stream.of(line.split(regex)).mapToInt(Integer::parseInt).toArray();
    }

    private static class Colis{
        public String id;
        public int volume;

        public Colis(String id, int volume) {
            this.id = id;
            this.volume = volume;
        }
    }
}
