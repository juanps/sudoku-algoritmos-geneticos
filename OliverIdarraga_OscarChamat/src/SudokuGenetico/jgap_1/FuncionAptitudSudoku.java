/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SudokuGenetico.jgap_1;

import java.util.BitSet;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.IChromosome;

/**
 *
 * @author civilian
 */
public class FuncionAptitudSudoku extends FitnessFunction {

    private final int n, nn;
    private BitSet list;
    private Coordenadas c;
    private int[] sqr;

    public FuncionAptitudSudoku(int n) {
        this.n = n;
        nn = n * n;
        list = new BitSet(nn); // List of 0s
        c = new Coordenadas(n);

        sqr = new int[nn * nn];
    }

    @Override
    protected double evaluate(IChromosome ic) {
        // The fitness value measures how many rows, columns, and boxes of
        // the Sudoku square contain the values 1..squareSize. For each row,
        // column, and box, the number of missing values is returned.  If
        // all values, 1, 2, ..., m_squareSize occur, the 0 is returned.
        // These values are summed for each row, column and box and the sum
        // is subtracted from a high value. The higher the result the better 
        // the fitness.





        int fitness = 0;
        Gene gene[] = ic.getGenes();

        for (int i = 0; i < nn * nn; i++) {
            sqr[i] = nn + 1;
        }
        
        for (int i = 0, j = 0; i < nn * nn; i++, j = (j + 1) % nn) {
            sqr[(Integer) gene[i].getAllele()-1] = j+1;
        }

        for (int k = 0; k < nn; k++) {
            fitness += checkFila(k, gene);
            fitness += checkColumna(k, gene);
            fitness += checkCaja(k, gene);
        }
        return nn * (nn - 1) * 3 - fitness;
    }

    //cuantos valores estan vacios en la fila larga
    private int checkFila(int r, Gene[] gene) {
        //      System.out.print(row + ":");
        list.clear();

        int m = r * nn;
        int v;
//        for (int k = m; k < m + nn; k++) {
//            v = ((Integer) gene[k].getAllele()).intValue();
//            list.set(v - 1);//tengo v en la fila
//        }
        for (int j = 0; j < nn; j++) {
            v = sqr[c.campo(r, j)];
            list.set(v - 1);//tengo v en la fila
//            l[v-1]=true;
        }
        int zeros = 0;//a quien no tengo
        for (int k = 0; k < nn; k++) {
            if (!list.get(k)) {
                zeros++;
            }
        }
        return zeros;
    }

    //cuantos valores estan vacios en la columna larga
    private int checkColumna(int column, Gene[] gene) {
        //      System.out.print("col " + column + ":");
        //      System.out.print(row + ":");
        list.clear();
        int v;
        for (int i = 0; i < nn; i++) {
            v = sqr[c.campo(i, column)];
            list.set(v - 1);//tengo v en la fila
        }
        int zeros = 0;//a quien no tengo
        for (int i = 0; i < nn; i++) {
            if (!list.get(i)) {
                zeros++;
            }
        }
        return zeros;
    }

    //cuantos valores estan vacios en la caja de n por n
    private int checkCaja(int box, Gene[] gene) {

        int l = (box % n) * n,
                k = (box / n) * n;
        list.clear();
        //TODO: DARLE DBG A ESTO
//        dbg("l,k",l,k);
        int v;
        for (int i = l; i < l + n - 1; i++) {
            for (int j = k; j < k + n - 1; j++) {
                v = sqr[c.campo(i, j)];
                list.set(v - 1);
            }
        }
        int zeros = 0;                            // Numbers not checked off
        for (int i = 0; i < nn; i++) {
            if (!list.get(i)) {
                zeros++;
            }
        }
        return zeros;
    }

    private void dbg(Object... o) {
        System.out.println(o);
    }
}
