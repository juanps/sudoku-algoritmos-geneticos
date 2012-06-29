package SudokuGenetico.watchmaker;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.uncommons.watchmaker.framework.operators.AbstractCrossover;

public class SudokuReproduccion extends AbstractCrossover<Sudoku> {

    public SudokuReproduccion() {
        super(1);
    }

    public SudokuReproduccion(int crossOverPoints) {
        super(crossOverPoints);
    }

    @Override
    protected List<Sudoku> mate(Sudoku p1, Sudoku p2, int i, Random random) {

        BigInteger mascaraCrossOverUniforme = new BigInteger(p1.cells.length, random);
        Sudoku.Gen[] hijo1 = p1.cells.clone();
        Sudoku.Gen[] hijo2 = p2.cells.clone();
        Sudoku.Gen tmp;
        for (int j = 0; j < p1.cells.length; j++) {
            if (mascaraCrossOverUniforme.testBit(j)) {
                tmp = hijo1[j];
                hijo1[j] = hijo2[j];
                hijo2[j] = tmp;
            }
        }

        List<Sudoku> result = new ArrayList<Sudoku>(2);
        result.add(new Sudoku(hijo1, p1.c));
        result.add(new Sudoku(hijo2, p2.c));
        return result;
    }
}
