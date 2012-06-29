package SudokuGenetico.watchmaker;

import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.uncommons.watchmaker.framework.FitnessEvaluator;

public class SudokuEvaluador implements FitnessEvaluator<Sudoku> {

    private final int n, nn;
    private boolean[] list;//la implementacion de la list de checks tuvo que ser cambiada para ser segura en cocurrencia
    private final Coordenadas c;

    public SudokuEvaluador(int n) {
        this.n = n;
        nn = n * n;
        list = new boolean[nn]; // List of 0s
        c = new Coordenadas(n);
    }

    @Override
    public double getFitness(Sudoku t, List<? extends Sudoku> list) {


        int fitness = 0;
        Sudoku.Gen[] gene = t.cells;
        for (int k = 0; k < nn; k++) {
            fitness += checkFila(k, gene);
            fitness += checkColumna(k, gene);
            fitness += checkCaja(k, gene);
        }
        return nn * (nn - 1) * 3 - fitness;
    }

    @Override
    public boolean isNatural() {
        return false;
    }

    //cuantos valores estan vacios en la fila larga
    private int checkFila(int r, Sudoku.Gen[] gene) {
        //      System.out.print(row + ":");
//        list.clear();
        Arrays.fill(list, false);
        int m = r * nn;
        int v;
        for (int j = 0; j < nn; j++) {
            v = (Integer) gene[c.campo(r, j)].getValue();
            list[v - 1]=true;//tengo v en la fila
//            l[v-1]=true;
        }
        int zeros = 0;//a quien no tengo
        for (int k = 0; k < nn; k++) {
            if (!list[k]) {
                zeros++;
            }
        }
        return zeros;
    }

    //cuantos valores estan vacios en la columna larga
    private int checkColumna(int column, Sudoku.Gen[] gene) {
        //      System.out.print("col " + column + ":");
        //      System.out.print(row + ":");
//        list.clear();
        Arrays.fill(list, false);
        int v;
        for (int i = 0; i < nn; i++) {
            v = (Integer) gene[c.campo(i, column)].getValue();
            list[v - 1]=true;//tengo v en la fila
        }
        int zeros = 0;//a quien no tengo
        for (int i = 0; i < nn; i++) {
            if (!list[i]) {
                zeros++;
            }
        }
        return zeros;
    }

    //cuantos valores estan vacios en la caja de n por n
    private int checkCaja(int box, Sudoku.Gen[] gene) {

        int l = (box % n) * n,
                k = (box / n) * n;
//        for (int i = 0; i < nn; i++) {
//            System.out.printf("%b ", list.get(i));
//        }
//        System.out.println();
//        list.clear();
        Arrays.fill(list, false);
        //TODO: DARLE DBG A ESTO
//        dbg("l,k",l,k);
        int v;
        for (int i = l; i < l + n - 1; i++) {
            for (int j = k; j < k + n - 1; j++) {
                v = (Integer) gene[c.campo(i, j)].getValue();
                list[v - 1]=true;
            }
        }
        int zeros = 0;                            // Numbers not checked off
        for (int i = 0; i < nn; i++) {
            if (!list[i]) {
                zeros++;
            }
        }
        return zeros;
    }

    private void dbg(Object... o) {
        System.out.println(o);
    }
}
