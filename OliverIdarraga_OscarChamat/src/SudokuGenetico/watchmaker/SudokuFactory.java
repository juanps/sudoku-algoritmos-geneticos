package SudokuGenetico.watchmaker;

import java.util.Random;
import java.util.Scanner;
import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;

public class SudokuFactory extends AbstractCandidateFactory<Sudoku> {

    private final Sudoku.Gen[] template;
    private int n, nn;
    private int desde, hasta;
    private Coordenadas c;

    public SudokuFactory(int n, Scanner sc, Coordenadas c) {
        this.n = n;
        nn = n * n;
        this.template = new Sudoku.Gen[nn * nn];
        int val;
        desde = 1;
        hasta = nn;
        this.c = c;

        for (int k = 0; k < template.length; k++) {
            val = (int) ((hasta - desde + 1) * Math.random() + desde);
            template[k] = new Sudoku.Gen(desde, hasta, val, false);
        }

        for (int i = 0; i < nn; i++) {
            for (int j = 0; j < nn; j++) {
                val = sc.nextInt();
                if (val != 0) {
//                    sampleGenes[c.campo(i, j)]=new GenSudoku(conf, 1, nn,true,val);
                    template[c.campo(i, j)].setValorInicial(val);
                }
            }
        }
    }

    @Override
    public Sudoku generateRandomCandidate(Random rng) {
        // Clone the template as the basis for this grid.
        Sudoku.Gen[] rows = template.clone();

//        int i = (int) Math.round(template.length*rng.nextDouble());
//        i=(i==nn*nn)?i-1:i;

        int i = rng.nextInt((nn * nn) - 1);
        if (rows[i].isInicial()) {
            return new Sudoku(rows, c);
        } else {
            rows[i].setValor((int) ((hasta - desde + 1) * Math.random() + desde));
            return new Sudoku(rows, c);
        }
    }
}
