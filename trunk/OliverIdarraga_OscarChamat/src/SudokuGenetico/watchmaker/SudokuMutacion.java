/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SudokuGenetico.watchmaker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;

/**
 *
 * @author civilian
 */
public class SudokuMutacion implements EvolutionaryOperator<Sudoku> {

    @Override
    public List<Sudoku> apply(List<Sudoku> list, Random random) {
        List<Sudoku> mutatedCandidates = new ArrayList<Sudoku>(list.size());
        for (Sudoku sudoku : list) {
            mutatedCandidates.add(mutate(sudoku, random));
        }
        return mutatedCandidates;
    }

    private Sudoku mutate(Sudoku sudoku, Random rng) {
        int i = rng.nextInt(sudoku.cells.length - 1);
        Sudoku.Gen[] newCell = sudoku.cells.clone();
        int nn = sudoku.cells.length;

//        i=(i==nn*nn)?i-1:i;

        if (sudoku.cells[i].isInicial()) {
            return new Sudoku(newCell, sudoku.c);
        } else {
            int desde = newCell[i].desde;
            int hasta = newCell[i].hasta;
            newCell[i].setValor((int) ((hasta - desde + 1) * Math.random() + desde));
            return new Sudoku(newCell, sudoku.c);
        }
    }
}
