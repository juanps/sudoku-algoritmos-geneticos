// ============================================================================
//   Copyright 2006, 2007 Daniel W. Dyer
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.
// ============================================================================
package SudokuGenetico.watchmaker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import org.jgap.RandomGenerator;
import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;

/**
 * Factory that generates potential Sudoku solutions from a list of "givens".
 * The rows of the generated solutions will all be valid (i.e. no duplicate VALUES)
 * but there are no constraints on the columns or sub-grids (these will be refined
 * by the evolutionary algorithm).
 * @author Daniel Dyer
 */
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

        int i = (int) Math.round(template.length*rng.nextDouble());
        i=(i==81)?80:i;
        if (rows[i].isInicial()) {
            return new Sudoku(rows, c);
        } else {
            rows[i].setValor((int) ((hasta - desde + 1) * Math.random() + desde));
            return new Sudoku(rows, c);
        }
    }
}
